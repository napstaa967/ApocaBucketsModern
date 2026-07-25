package com.arteoformance.apocabuckets.neoforge.fluid;

import com.arteoformance.apocabuckets.ApocaFluids;
import com.arteoformance.apocabuckets.fluid.DarknessFluid;
import com.arteoformance.apocabuckets.fluid.FloodFluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidType;

public abstract class NFDarknessFluid extends DarknessFluid {

    @Override
    public FluidType getFluidType() {
        return (FluidType) ApocaFluids.DARKNESS_FLUID.getRegisteredType().get();
    }

    public static class Source extends NFDarknessFluid {

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }

    public static class Flowing extends NFDarknessFluid {
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
