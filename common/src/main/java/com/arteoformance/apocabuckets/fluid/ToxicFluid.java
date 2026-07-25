package com.arteoformance.apocabuckets.fluid;

import com.arteoformance.apocabuckets.ApocaBlocks;
import com.arteoformance.apocabuckets.ApocaFluids;
import com.arteoformance.apocabuckets.ApocaItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

public abstract class ToxicFluid extends FlowingFluid {

    @Nullable
    public Block getDecayResult() {
        return null;
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return 15;
    }

    @Override
    public Fluid getFlowing() {
        return ApocaFluids.TOXIC_FLUID.getFlowing();
    }

    @Override
    public Fluid getSource() {
        return ApocaFluids.TOXIC_FLUID.getSource();
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return ApocaBlocks.TOXIC.get().defaultBlockState().setValue(LiquidBlock.LEVEL, 0);
    }

    @Override
    public Item getBucket() {
        return ApocaItems.TOXIC_BUCKET.get();
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
    public boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.is(FluidTags.WATER);
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }


    public static class Source extends ToxicFluid {

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }

    public static class Flowing extends ToxicFluid {
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
}