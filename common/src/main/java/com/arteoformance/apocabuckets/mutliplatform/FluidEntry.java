package com.arteoformance.apocabuckets.mutliplatform;

import net.minecraft.world.level.material.Fluid;import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public final class FluidEntry<K> {
    private final Supplier<Fluid> FLUID_FLOWING;
    private final Supplier<Fluid> FLUID_SOURCE;
    private final CommonFluidType FLUID_TYPE;
    @Nullable
    private final Supplier<K> FLUID_REGISTERED_TYPE;

    public FluidEntry(Supplier<Fluid> fluidFlowing, Supplier<Fluid> fluidSource, CommonFluidType fluidType, @Nullable Supplier<K> fluidRegisteredType) {
        this.FLUID_FLOWING = fluidFlowing;
        this.FLUID_SOURCE = fluidSource;
        this.FLUID_TYPE = fluidType;
        this.FLUID_REGISTERED_TYPE = fluidRegisteredType;
    }

    public Fluid getFlowing() {
        return FLUID_FLOWING.get();
    }

    public Fluid getSource() {
        return FLUID_SOURCE.get();
    }

    public CommonFluidType getType() {
        return this.FLUID_TYPE;
    }

    @Nullable
    public Supplier<K> getRegisteredType() {
        return FLUID_REGISTERED_TYPE;
    }
}
