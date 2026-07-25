package com.arteoformance.apocabuckets.neoforge.bullshit;

import com.arteoformance.apocabuckets.mutliplatform.CommonFluidType;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidType;

public class ApocaFluidType extends FluidType {

    private final CommonFluidType fluidType;
    public ApocaFluidType(CommonFluidType fluidType, Properties properties) {
        super(properties);
        this.fluidType = fluidType;
    }

    public ResourceLocation getStillTexture() {
        return fluidType.stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return fluidType.flowingTexture;
    }

    public int getTintColor() {
        return fluidType.tint;
    }

    public int getTintColor(Level level, BlockPos pos) {
        return BiomeColors.getAverageWaterColor(level, pos);
    }

    public ResourceLocation getOverlayTexture() {
        return fluidType.overlayTexture;
    }
}
