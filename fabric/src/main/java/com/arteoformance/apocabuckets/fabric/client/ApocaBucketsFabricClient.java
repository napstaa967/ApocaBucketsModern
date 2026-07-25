package com.arteoformance.apocabuckets.fabric.client;

import com.arteoformance.apocabuckets.ApocaBlocks;
import com.arteoformance.apocabuckets.ApocaFluids;
import com.arteoformance.apocabuckets.mutliplatform.CommonFluidType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.Fluids;

public final class ApocaBucketsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CommonFluidType toxicFluidType = ApocaFluids.TOXIC_FLUID.getType();
        FluidRenderHandlerRegistry.INSTANCE.register(ApocaFluids.TOXIC_FLUID.getSource(), ApocaFluids.TOXIC_FLUID.getFlowing(),
                new SimpleFluidRenderHandler(
                        toxicFluidType.stillTexture,
                        toxicFluidType.flowingTexture,
                        toxicFluidType.overlayTexture,
                        toxicFluidType.tint
                ));
        FluidRenderHandlerRegistry.INSTANCE.register(ApocaFluids.FLOOD_FLUID.getSource(), ApocaFluids.FLOOD_FLUID.getFlowing(),
                FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER)
        );
        CommonFluidType darknessFluidType = ApocaFluids.DARKNESS_FLUID.getType();
        FluidRenderHandlerRegistry.INSTANCE.register(ApocaFluids.DARKNESS_FLUID.getSource(), ApocaFluids.DARKNESS_FLUID.getFlowing(),
                new SimpleFluidRenderHandler(
                        darknessFluidType.stillTexture,
                        darknessFluidType.flowingTexture,
                        darknessFluidType.overlayTexture,
                        darknessFluidType.tint
                ));
        FluidRenderHandlerRegistry.INSTANCE.register(ApocaFluids.ICE_FLUID.getSource(), ApocaFluids.ICE_FLUID.getFlowing(),
                FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER)
        );
        BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(), ApocaFluids.TOXIC_FLUID.getSource(), ApocaFluids.TOXIC_FLUID.getFlowing());
        BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(), ApocaFluids.FLOOD_FLUID.getSource(), ApocaFluids.FLOOD_FLUID.getFlowing());
        BlockRenderLayerMap.INSTANCE.putFluids(RenderType.solid(), ApocaFluids.DARKNESS_FLUID.getSource(), ApocaFluids.DARKNESS_FLUID.getFlowing());
        BlockRenderLayerMap.INSTANCE.putFluids(RenderType.solid(), ApocaFluids.ICE_FLUID.getSource(), ApocaFluids.ICE_FLUID.getFlowing());
        BlockRenderLayerMap.INSTANCE.putBlock(ApocaBlocks.FIRE.get(), RenderType.translucent());
    }
}
