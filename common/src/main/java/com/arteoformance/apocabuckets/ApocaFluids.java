package com.arteoformance.apocabuckets;

import com.arteoformance.apocabuckets.fluid.ToxicFluid;
import com.arteoformance.apocabuckets.mutliplatform.CommonFluidType;
import com.arteoformance.apocabuckets.mutliplatform.FluidEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

import java.util.function.Supplier;

public class ApocaFluids {

    public static final FluidEntry<?> TOXIC_FLUID = ApocaBuckets.REGISTRY.registerFluid(
            "toxic",
            new CommonFluidType(
                    ResourceLocation.withDefaultNamespace("block/water_still"),
                    ResourceLocation.withDefaultNamespace("block/water_flow"),
                    ResourceLocation.withDefaultNamespace("block/water_overlay"),
                    FastColor.ARGB32.color(0x00, 0xFF, 0x00)
            )
    );

    public static final FluidEntry<?> FLOOD_FLUID = ApocaBuckets.REGISTRY.registerFluid(
            "flood",
            new CommonFluidType(
                    ResourceLocation.withDefaultNamespace("block/water_still"),
                    ResourceLocation.withDefaultNamespace("block/water_flow"),
                    ResourceLocation.withDefaultNamespace("block/water_overlay"),
                    FastColor.ARGB32.color(0xFF, 0xFF, 0xFF)
            )
    );

    public static final FluidEntry<?> DARKNESS_FLUID = ApocaBuckets.REGISTRY.registerFluid(
            "darkness",
            new CommonFluidType(
                    ResourceLocation.withDefaultNamespace("block/lava_still"),
                    ResourceLocation.withDefaultNamespace("block/lava_flow"),
                    ResourceLocation.withDefaultNamespace("block/lava_overlay"),
                    FastColor.ARGB32.color(0x00, 0x00, 0x00)
            )
    );

    public static final FluidEntry<?> ICE_FLUID = ApocaBuckets.REGISTRY.registerFluid(
            "ice",
            new CommonFluidType(
                    ResourceLocation.withDefaultNamespace("block/water_still"),
                    ResourceLocation.withDefaultNamespace("block/water_flow"),
                    ResourceLocation.withDefaultNamespace("block/water_overlay"),
                    FastColor.ARGB32.color(0xFF, 0xFF, 0xFF)
            )
    );

    public static void init() {

    }
}
