package com.arteoformance.apocabuckets.mutliplatform;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
// Intermediary class for registering FluidTypes with neoforge
// In fabric, this is used to register it's client-side renderer
public final class CommonFluidType {
    public final ResourceLocation stillTexture;
    public final ResourceLocation flowingTexture;
    public final ResourceLocation overlayTexture;
    public final int tint;

    public CommonFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture, int tint) {
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        this.tint = tint;
    }
}
