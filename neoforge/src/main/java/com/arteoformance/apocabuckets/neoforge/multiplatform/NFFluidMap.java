package com.arteoformance.apocabuckets.neoforge.multiplatform;

import com.arteoformance.apocabuckets.ApocaBuckets;
import com.arteoformance.apocabuckets.fluid.ToxicFluid;
import com.arteoformance.apocabuckets.neoforge.fluid.NFDarknessFluid;
import com.arteoformance.apocabuckets.neoforge.fluid.NFFloodFluid;
import com.arteoformance.apocabuckets.neoforge.fluid.NFIceFluid;
import com.arteoformance.apocabuckets.neoforge.fluid.NFToxicFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class NFFluidMap {
    private final static Map<String, Supplier<Fluid>> FLUID_MAP;
    static {
        Map<String, Supplier<Fluid>> map = new HashMap<>();
        map.put(
                "toxic",
                NFToxicFluid.Source::new
        );
        map.put(
                "toxic_flowing",
                NFToxicFluid.Flowing::new
        );
        map.put(
                "flood",
                NFFloodFluid.Source::new
        );
        map.put(
                "flood_flowing",
                NFFloodFluid.Flowing::new
        );
        map.put(
                "darkness",
                NFDarknessFluid.Source::new
        );
        map.put(
                "darkness_flowing",
                NFDarknessFluid.Flowing::new
        );
        map.put(
                "ice",
                NFIceFluid.Source::new
        );
        map.put(
                "ice_flowing",
                NFIceFluid.Flowing::new
        );

        FLUID_MAP = Collections.unmodifiableMap(map);
    }

    @Nullable
    public static Supplier<Fluid> get(String fluid) {
        return FLUID_MAP.get(fluid);
    }
}
