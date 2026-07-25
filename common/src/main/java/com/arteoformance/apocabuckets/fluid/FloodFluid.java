package com.arteoformance.apocabuckets.fluid;

import com.arteoformance.apocabuckets.*;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public abstract class FloodFluid extends FlowingFluid {
    private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>> OCCLUSION_CACHE;

    private boolean canPassThrough(BlockGetter blockGetter, Fluid fluid, BlockPos blockPos, BlockState blockState, Direction direction, BlockPos blockPos2, BlockState blockState2, FluidState fluidState) {
        return !this.isSourceBlockOfThisType(fluidState) && this.canPassThroughWall(direction, blockGetter, blockPos, blockState, blockPos2, blockState2) && this.canHoldFluid(blockGetter, blockPos2, blockState2, fluid);
    }

    private static short getCacheKey(BlockPos blockPos, BlockPos blockPos2) {
        int i = blockPos2.getX() - blockPos.getX();
        int j = blockPos2.getZ() - blockPos.getZ();
        return (short)((i + 128 & 255) << 8 | j + 128 & 255);
    }

    @Override
    protected Map<Direction, FluidState> getSpread(Level level, BlockPos blockPos, BlockState blockState) {
        int i = 1000;
        Map<Direction, FluidState> map = Maps.newEnumMap(Direction.class);
        Short2ObjectMap<Pair<BlockState, FluidState>> short2ObjectMap = new Short2ObjectOpenHashMap();
        Short2BooleanMap short2BooleanMap = new Short2BooleanOpenHashMap();

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos2 = blockPos.relative(direction);
            short s = getCacheKey(blockPos, blockPos2);
            Pair<BlockState, FluidState> pair = short2ObjectMap.computeIfAbsent(s, (sx) -> {
                BlockState blockState_ = level.getBlockState(blockPos2);
                return Pair.of(blockState_, blockState_.getFluidState());
            });
            BlockState blockState2 = (BlockState)pair.getFirst();
            FluidState fluidState = (FluidState)pair.getSecond();
            FluidState fluidState2 = this.getNewLiquid(level, blockPos2, blockState2);
            if (this.canPassThrough(level, fluidState2.getType(), blockPos, blockState, direction, blockPos2, blockState2, fluidState)) {
                map.put(direction, fluidState2);
            }
        }

        return map;
    }

    private boolean isSourceBlockOfThisType(FluidState fluidState) {
        return fluidState.getType().isSame(this) && fluidState.isSource();
    }

    private int sourceNeighborCount(LevelReader levelReader, BlockPos blockPos) {
        int i = 0;

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos2 = blockPos.relative(direction);
            FluidState fluidState = levelReader.getFluidState(blockPos2);
            if (this.isSourceBlockOfThisType(fluidState)) {
                ++i;
            }
        }

        return i;
    }

    protected void spread(Level level, BlockPos blockPos, FluidState fluidState) {
        if (!fluidState.isEmpty()) {
            BlockState blockState = level.getBlockState(blockPos);
            BlockPos blockPos2 = blockPos.below();
            BlockState blockState2 = level.getBlockState(blockPos2);
            FluidState fluidState2 = this.getNewLiquid(level, blockPos2, blockState2);
            if (this.canSpreadTo(level, blockPos, blockState, Direction.DOWN, blockPos2, blockState2, level.getFluidState(blockPos2), fluidState2.getType())) {
                this.spreadTo(level, blockPos2, blockState2, Direction.DOWN, fluidState2);
            }
            // Small tweak to force it to spread
            if (fluidState.isSource() || !this.isWaterHole(level, fluidState2.getType(), blockPos, blockState, blockPos2, blockState2)) {
                this.spreadToSides(level, blockPos, fluidState, blockState);
            }

        }
    }

    private boolean isWaterHole(BlockGetter blockGetter, Fluid fluid, BlockPos blockPos, BlockState blockState, BlockPos blockPos2, BlockState blockState2) {
        if (!this.canPassThroughWall(Direction.DOWN, blockGetter, blockPos, blockState, blockPos2, blockState2)) {
            return false;
        } else {
            return blockState2.getFluidState().getType().isSame(this) ? true : this.canHoldFluid(blockGetter, blockPos2, blockState2, fluid);
        }
    }

    private boolean canPassThroughWall(Direction direction, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, BlockPos blockPos2, BlockState blockState2) {
        Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> object2ByteLinkedOpenHashMap;
        if (!blockState.getBlock().hasDynamicShape() && !blockState2.getBlock().hasDynamicShape()) {
            object2ByteLinkedOpenHashMap = (Object2ByteLinkedOpenHashMap)OCCLUSION_CACHE.get();
        } else {
            object2ByteLinkedOpenHashMap = null;
        }

        Block.BlockStatePairKey blockStatePairKey;
        if (object2ByteLinkedOpenHashMap != null) {
            blockStatePairKey = new Block.BlockStatePairKey(blockState, blockState2, direction);
            byte b = object2ByteLinkedOpenHashMap.getAndMoveToFirst(blockStatePairKey);
            if (b != 127) {
                return b != 0;
            }
        } else {
            blockStatePairKey = null;
        }

        VoxelShape voxelShape = blockState.getCollisionShape(blockGetter, blockPos);
        VoxelShape voxelShape2 = blockState2.getCollisionShape(blockGetter, blockPos2);
        boolean bl = !Shapes.mergedFaceOccludes(voxelShape, voxelShape2, direction);
        if (object2ByteLinkedOpenHashMap != null) {
            if (object2ByteLinkedOpenHashMap.size() == 200) {
                object2ByteLinkedOpenHashMap.removeLastByte();
            }

            object2ByteLinkedOpenHashMap.putAndMoveToFirst(blockStatePairKey, (byte)(bl ? 1 : 0));
        }

        return bl;
    }

    private boolean canHoldFluid(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        Block block = blockState.getBlock();
        if (block instanceof LiquidBlockContainer liquidBlockContainer) {
            return liquidBlockContainer.canPlaceLiquid((Player)null, blockGetter, blockPos, blockState, fluid);
        } else if (!(block instanceof DoorBlock) && !blockState.is(BlockTags.SIGNS) && !blockState.is(Blocks.LADDER) && !blockState.is(Blocks.SUGAR_CANE) && !blockState.is(Blocks.BUBBLE_COLUMN)) {
            if (!blockState.is(Blocks.NETHER_PORTAL) && !blockState.is(Blocks.END_PORTAL) && !blockState.is(Blocks.END_GATEWAY) && !blockState.is(Blocks.STRUCTURE_VOID)) {
                return !blockState.blocksMotion();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void spreadToSides(Level level, BlockPos blockPos, FluidState fluidState, BlockState blockState) {
        Map<Direction, FluidState> map = this.getSpread(level, blockPos, blockState);

        for(Map.Entry<Direction, FluidState> entry : map.entrySet()) {
            Direction direction = (Direction)entry.getKey();
            FluidState fluidState2 = (FluidState)entry.getValue();
            BlockPos blockPos2 = blockPos.relative(direction);
            BlockState blockState2 = level.getBlockState(blockPos2);
            if (this.canSpreadTo(level, blockPos, blockState, direction, blockPos2, blockState2, level.getFluidState(blockPos2), fluidState2.getType())) {
                this.spreadTo(level, blockPos2, blockState2, direction, fluidState2);
            }
        }
    }

    @Override
    public void tick(Level level, BlockPos blockPos, FluidState fluidState) {
        if (!fluidState.isSource()) {
            FluidState fluidState2 = this.getNewLiquid(level, blockPos, level.getBlockState(blockPos));
            int i = this.getSpreadDelay(level, blockPos, fluidState, fluidState2);
            if (fluidState2.isEmpty()) {
                fluidState = fluidState2;
                level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
            } else if (!fluidState2.equals(fluidState)) {
                fluidState = fluidState2;
                BlockState blockState = fluidState2.createLegacyBlock();
                level.setBlock(blockPos, blockState, 2);
                level.scheduleTick(blockPos, fluidState2.getType(), i);
                level.updateNeighborsAt(blockPos, blockState.getBlock());
            }
        }

        this.spread(level, blockPos, fluidState);
        // Remove block after attempting to spread to prevent infinite flood
        @Nullable
        Block decay = getDecayResult();
        if (null != decay) {
            level.setBlock(blockPos, decay.defaultBlockState(), 3);
        }
    }

    // Returns the block the flood decays into after spreading
    @Nullable
    public Block getDecayResult() {
        return Blocks.WATER;
    }

    @Override
    protected void spreadTo(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, Direction direction, FluidState fluidState) {
        if (blockState.getBlock() instanceof LiquidBlockContainer) {
            ((LiquidBlockContainer)blockState.getBlock()).placeLiquid(levelAccessor, blockPos, blockState, fluidState);
        } else {
            if (!blockState.isAir()) {
                this.beforeDestroyingBlock(levelAccessor, blockPos, blockState);
            }

            levelAccessor.setBlock(blockPos, getSource().defaultFluidState().createLegacyBlock(), 3);
        }

    }

    @Override
    public void animateTick(Level level, BlockPos blockPos, FluidState fluidState, RandomSource randomSource) {
        if (!fluidState.isSource() && !(Boolean)fluidState.getValue(FALLING)) {
            if (randomSource.nextInt(64) == 0) {
                level.playLocalSound((double)blockPos.getX() + (double)0.5F, (double)blockPos.getY() + (double)0.5F, (double)blockPos.getZ() + (double)0.5F, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, randomSource.nextFloat() * 0.25F + 0.75F, randomSource.nextFloat() + 0.5F, false);
            }
        } else if (randomSource.nextInt(10) == 0) {
            level.addParticle(ParticleTypes.UNDERWATER, (double)blockPos.getX() + randomSource.nextDouble(), (double)blockPos.getY() + randomSource.nextDouble(), (double)blockPos.getZ() + randomSource.nextDouble(), (double)0.0F, (double)0.0F, (double)0.0F);
        }

    }

    @Override
    @Nullable
    public ParticleOptions getDripParticle() {
        return ParticleTypes.DRIPPING_WATER;
    }

    @Override
    protected boolean canConvertToSource(Level level) {
        return true;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        BlockEntity blockEntity = blockState.hasBlockEntity() ? levelAccessor.getBlockEntity(blockPos) : null;
        Block.dropResources(blockState, levelAccessor, blockPos, blockEntity);
    }

    @Override
    public int getSlopeFindDistance(LevelReader levelReader) {
        return 0;
    }

    @Override
    public int getDropOff(LevelReader levelReader) {
        return 1;
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return 5;
    }

    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.is(FluidTags.WATER);
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }

    @Override
    public Fluid getFlowing() {
        return ApocaFluids.FLOOD_FLUID.getFlowing();
    }

    @Override
    public Fluid getSource() {
        return ApocaFluids.FLOOD_FLUID.getSource();
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == getFlowing() || fluid == getSource();
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return ApocaBlocks.FLOOD.get().defaultBlockState().setValue(LiquidBlock.LEVEL, 0);
    }

    @Override
    public Item getBucket() {
        return ApocaItems.FLOOD_BUCKET.get();
    }

    public static class Source extends FloodFluid {

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }

    public static class Flowing extends FloodFluid {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(new Property[]{LEVEL});
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return (Integer)fluidState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return false;
        }
    }

    static {
        OCCLUSION_CACHE = ThreadLocal.withInitial(() -> {
            Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> object2ByteLinkedOpenHashMap = new Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>(200) {
                protected void rehash(int i) {
                }
            };
            object2ByteLinkedOpenHashMap.defaultReturnValue((byte)127);
            return object2ByteLinkedOpenHashMap;
        });
    }
}