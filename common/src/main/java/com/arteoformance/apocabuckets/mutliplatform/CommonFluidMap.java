package com.arteoformance.apocabuckets.mutliplatform;

import com.arteoformance.apocabuckets.ApocaBuckets;
import com.arteoformance.apocabuckets.fluid.DarknessFluid;
import com.arteoformance.apocabuckets.fluid.FloodFluid;
import com.arteoformance.apocabuckets.fluid.IceFluid;
import com.arteoformance.apocabuckets.fluid.ToxicFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class CommonFluidMap {
    private final static Map<String, Supplier<Fluid>> FLUID_MAP;
    static {
        Map<String, Supplier<Fluid>> map = new HashMap<>();
        map.put(
                "toxic",
                ToxicFluid.Source::new
        );
        map.put(
                "toxic_flowing",
                ToxicFluid.Flowing::new
        );
        map.put(
                "flood",
                FloodFluid.Source::new
        );
        map.put(
                "flood_flowing",
                FloodFluid.Flowing::new
        );
        map.put(
                "darkness",
                DarknessFluid.Source::new
        );
        map.put(
                "darkness_flowing",
                DarknessFluid.Flowing::new
        );
        map.put(
                "ice",
                IceFluid.Source::new
        );
        map.put(
                "ice_flowing",
                IceFluid.Flowing::new
        );

        FLUID_MAP = Collections.unmodifiableMap(map);
    }

    @Nullable
    public static Supplier<Fluid> get(String fluid) {
        return FLUID_MAP.get(fluid);
    }
}
