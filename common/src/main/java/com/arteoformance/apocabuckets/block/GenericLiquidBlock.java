package com.arteoformance.apocabuckets.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GenericLiquidBlock extends LiquidBlock {
    public GenericLiquidBlock(FlowingFluid flowingFluid, Properties properties) {
        super(flowingFluid, properties);
        this.fluid = flowingFluid;
        this.stateCache = Lists.newArrayList();
        this.stateCache.add(flowingFluid.getSource(false));

        for(int i = 1; i < 8; ++i) {
            this.stateCache.add(flowingFluid.getFlowing(8 - i, false));
        }

        this.stateCache.add(flowingFluid.getFlowing(8, true));
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(LEVEL, 0));
    }

    public static final IntegerProperty LEVEL;
    protected final FlowingFluid fluid;
    private final List<FluidState> stateCache;
    public static final VoxelShape STABLE_SHAPE;
    public static final ImmutableList<Direction> POSSIBLE_FLOW_DIRECTIONS;

    protected VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return collisionContext.isAbove(STABLE_SHAPE, blockPos, true) && (Integer)blockState.getValue(LEVEL) == 0 && collisionContext.canStandOnFluid(blockGetter.getFluidState(blockPos.above()), blockState.getFluidState()) ? STABLE_SHAPE : Shapes.empty();
    }

    protected boolean isRandomlyTicking(BlockState blockState) {
        return blockState.getFluidState().isRandomlyTicking();
    }

    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        blockState.getFluidState().randomTick(serverLevel, blockPos, randomSource);
    }

    protected boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return !this.fluid.is(FluidTags.LAVA);
    }

    protected FluidState getFluidState(BlockState blockState) {
        int i = (Integer)blockState.getValue(LEVEL);
        return (FluidState)this.stateCache.get(Math.min(i, 8));
    }

    protected boolean skipRendering(BlockState blockState, BlockState blockState2, Direction direction) {
        return blockState2.getFluidState().getType().isSame(this.fluid);
    }

    protected RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    protected List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return Collections.emptyList();
    }

    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.empty();
    }

    protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (this.shouldSpreadLiquid(level, blockPos, blockState)) {
            level.scheduleTick(blockPos, blockState.getFluidState().getType(), this.fluid.getTickDelay(level));
        }

    }

    protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getFluidState().isSource() || blockState2.getFluidState().isSource()) {
            levelAccessor.scheduleTick(blockPos, blockState.getFluidState().getType(), this.fluid.getTickDelay(levelAccessor));
        }

        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (this.shouldSpreadLiquid(level, blockPos, blockState)) {
            level.scheduleTick(blockPos, blockState.getFluidState().getType(), this.fluid.getTickDelay(level));
        }

    }

    private boolean shouldSpreadLiquid(Level level, BlockPos blockPos, BlockState blockState) {
        if (this.fluid.is(FluidTags.LAVA)) {
            boolean bl = level.getBlockState(blockPos.below()).is(Blocks.SOUL_SOIL);
            UnmodifiableIterator var5 = POSSIBLE_FLOW_DIRECTIONS.iterator();

            while(var5.hasNext()) {
                Direction direction = (Direction)var5.next();
                BlockPos blockPos2 = blockPos.relative(direction.getOpposite());
                if (level.getFluidState(blockPos2).is(FluidTags.WATER)) {
                    Block block = level.getFluidState(blockPos).isSource() ? Blocks.OBSIDIAN : Blocks.COBBLESTONE;
                    level.setBlockAndUpdate(blockPos, block.defaultBlockState());
                    this.fizz(level, blockPos);
                    return false;
                }

                if (bl && level.getBlockState(blockPos2).is(Blocks.BLUE_ICE)) {
                    level.setBlockAndUpdate(blockPos, Blocks.BASALT.defaultBlockState());
                    this.fizz(level, blockPos);
                    return false;
                }
            }
        }

        return true;
    }

    private void fizz(LevelAccessor levelAccessor, BlockPos blockPos) {
        levelAccessor.levelEvent(1501, blockPos, 0);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LEVEL});
    }

    public ItemStack pickupBlock(@Nullable Player player, LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        if ((Integer)blockState.getValue(LEVEL) == 0) {
            levelAccessor.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);
            return new ItemStack(this.fluid.getBucket());
        } else {
            return ItemStack.EMPTY;
        }
    }

    public Optional<SoundEvent> getPickupSound() {
        return this.fluid.getPickupSound();
    }

    static {
        LEVEL = BlockStateProperties.LEVEL;
        STABLE_SHAPE = Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F);
        POSSIBLE_FLOW_DIRECTIONS = ImmutableList.of(Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST);
    }
}
