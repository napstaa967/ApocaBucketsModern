package com.arteoformance.apocabuckets.neoforge.bullshit;

import com.arteoformance.apocabuckets.ApocaFluids;
import com.arteoformance.apocabuckets.mutliplatform.CommonFluidType;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientFluidTypeExtensions implements IClientFluidTypeExtensions {
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(
                new ClientFluidTypeExtensions(ApocaFluids.TOXIC_FLUID.getType()),
                (FluidType) ApocaFluids.TOXIC_FLUID.getRegisteredType().get()
        );
        event.registerFluidType(
                new ClientFluidTypeExtensions(ApocaFluids.FLOOD_FLUID.getType()) {
                    public int getTintColor(FluidState state, BlockAndTintGetter level, BlockPos pos) {
                        return BiomeColors.getAverageWaterColor(level, pos);
                    }
                },
                (FluidType) ApocaFluids.FLOOD_FLUID.getRegisteredType().get()
        );
        event.registerFluidType(
                new ClientFluidTypeExtensions(ApocaFluids.DARKNESS_FLUID.getType()),
                (FluidType) ApocaFluids.DARKNESS_FLUID.getRegisteredType().get()
        );
        event.registerFluidType(
                new ClientFluidTypeExtensions(ApocaFluids.ICE_FLUID.getType()) {
                    public int getTintColor(FluidState state, BlockAndTintGetter level, BlockPos pos) {
                        return BiomeColors.getAverageWaterColor(level, pos);
                    }
                },
                (FluidType) ApocaFluids.ICE_FLUID.getRegisteredType().get()
        );
    }
    private final CommonFluidType fluidType;

    public ClientFluidTypeExtensions(CommonFluidType fluidType) {
        this.fluidType = fluidType;
    }

    public ResourceLocation getStillTexture() {
        return fluidType.stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return fluidType.flowingTexture;
    }

    @Nullable
    public ResourceLocation getOverlayTexture() {
        return fluidType.overlayTexture;
    }

    public int getTintColor() {
        return fluidType.tint;
    }
}