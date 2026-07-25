package com.arteoformance.apocabuckets.neoforge.registry;

import com.arteoformance.apocabuckets.ApocaBuckets;
import com.arteoformance.apocabuckets.mutliplatform.CommonFluidType;
import com.arteoformance.apocabuckets.mutliplatform.FluidEntry;
import com.arteoformance.apocabuckets.neoforge.bullshit.ApocaFluidType;
import com.arteoformance.apocabuckets.neoforge.multiplatform.NFFluidMap;
import com.arteoformance.apocabuckets.registry.AbstractPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.function.Supplier;

public class NeoForgePlatform extends AbstractPlatform {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ApocaBuckets.MOD_ID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ApocaBuckets.MOD_ID);
    // Neoforge baby shit
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ApocaBuckets.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, ApocaBuckets.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ApocaBuckets.MOD_ID);


    public Supplier<Item> registerItem(String name, Supplier<Item> itemFactory) {

        return ITEMS.register(
                name,
                itemFactory
        );
    }

    public Supplier<Block> registerBlock(String name, Supplier<Block> blockFactory) {

        return BLOCKS.register(
                name,
                blockFactory
        );
    }

    public FluidEntry<FluidType> registerFluid(String name, CommonFluidType commonFluidType) {
        // Create the fluid type because neoforge is a special kid

        DeferredHolder<FluidType, FluidType> fluidType = FLUID_TYPES.register(
                name,
                () -> new ApocaFluidType(
                        commonFluidType,
                        FluidType.Properties.create()
                                .descriptionId(String.format("block.apocabuckets.%s", name))
                )
        );

        Supplier<Fluid> flowingFluidFactory = NFFluidMap.get(name+"_flowing");
        Supplier<Fluid> sourceFluidFactory = NFFluidMap.get(name);

        // Register flowing and source
        DeferredHolder<Fluid, Fluid> flowingFluid = FLUIDS.register(
                name+"_flowing",
                flowingFluidFactory
        );

        DeferredHolder<Fluid, Fluid> sourceFluid = FLUIDS.register(
                name,
                sourceFluidFactory
        );

        return new FluidEntry<FluidType>(flowingFluid, sourceFluid, commonFluidType, fluidType);
    }

    public Supplier<CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> iconFactory, List<Supplier<Item>> contents) {
        return CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(iconFactory)
                .title(Component.translatable(String.format("itemGroup.%s.%s", ApocaBuckets.MOD_ID, name)))
                .displayItems((params, output) -> {
                    for (Supplier<Item> item : contents) {
                        output.accept(item.get());
                    }
                })
                .build()
        );
    }
}
