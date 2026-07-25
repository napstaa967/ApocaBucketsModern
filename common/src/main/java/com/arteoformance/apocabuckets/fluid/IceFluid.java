package com.arteoformance.apocabuckets.fluid;

import com.arteoformance.apocabuckets.ApocaBlocks;
import com.arteoformance.apocabuckets.ApocaFluids;
import com.arteoformance.apocabuckets.ApocaItems;
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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

public abstract class IceFluid extends FloodFluid {
    private final Random r = new Random();
    // Returns the block the flood decays into after spreading
    @Nullable
    @Override
    public Block getDecayResult() {
        Block result = Blocks.ICE;
        result = switch (r.nextInt(0, 4)) {
            case 0 -> Blocks.ICE;
            case 1 -> Blocks.PACKED_ICE;
            case 2 -> Blocks.BLUE_ICE;
            case 3 -> Blocks.WATER;
            default -> result;
        };
        return result;
    }

    @Override
    public Fluid getFlowing() {
        return ApocaFluids.ICE_FLUID.getFlowing();
    }

    @Override
    public Fluid getSource() {
        return ApocaFluids.ICE_FLUID.getSource();
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return ApocaBlocks.ICE.get().defaultBlockState().setValue(LiquidBlock.LEVEL, 0);
    }

    @Override
    public Item getBucket() {
        return ApocaItems.ICE_BUCKET.get();
    }

    public static class Source extends IceFluid {

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }

    public static class Flowing extends IceFluid {
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