package com.arteoformance.apocabuckets.fluid;

import com.arteoformance.apocabuckets.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class DarknessFluid extends FloodFluid {
    @Nullable
    @Override
    public Block getDecayResult() {
        return null;
    }

    @Override
    public void animateTick(Level level, BlockPos blockPos, FluidState fluidState, RandomSource randomSource) {
        BlockPos blockPos2 = blockPos.above();
        if (level.getBlockState(blockPos2).isAir() && !level.getBlockState(blockPos2).isSolidRender(level, blockPos2)) {
            if (randomSource.nextInt(100) == 0) {
                double d = (double)blockPos.getX() + randomSource.nextDouble();
                double e = (double)blockPos.getY() + (double)1.0F;
                double f = (double)blockPos.getZ() + randomSource.nextDouble();
                level.addParticle(ParticleTypes.LAVA, d, e, f, (double)0.0F, (double)0.0F, (double)0.0F);
                level.playLocalSound(d, e, f, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 0.2F + randomSource.nextFloat() * 0.2F, 0.9F + randomSource.nextFloat() * 0.15F, false);
            }

            if (randomSource.nextInt(200) == 0) {
                level.playLocalSound((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.2F + randomSource.nextFloat() * 0.2F, 0.9F + randomSource.nextFloat() * 0.15F, false);
            }
        }
    }

    @Override
    @Nullable
    public ParticleOptions getDripParticle() {
        return ParticleTypes.DRIPPING_LAVA;
    }

    @Override
    protected boolean canConvertToSource(Level level) {
        return true;
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return 30;
    }

    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.is(FluidTags.LAVA);
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
    }

    @Override
    public Fluid getFlowing() {
        return ApocaFluids.DARKNESS_FLUID.getFlowing();
    }

    @Override
    public Fluid getSource() {
        return ApocaFluids.DARKNESS_FLUID.getSource();
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return ApocaBlocks.DARKNESS.get().defaultBlockState().setValue(LiquidBlock.LEVEL, 0);
    }

    @Override
    public Item getBucket() {
        return ApocaItems.DARKNESS_BUCKET.get();
    }

    public static class Source extends DarknessFluid {

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }

    public static class Flowing extends DarknessFluid {
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