package com.arteoformance.apocabuckets.fabric.registry;

import com.arteoformance.apocabuckets.ApocaBuckets;
import com.arteoformance.apocabuckets.mutliplatform.CommonFluidMap;
import com.arteoformance.apocabuckets.mutliplatform.CommonFluidType;
import com.arteoformance.apocabuckets.mutliplatform.FluidEntry;
import com.arteoformance.apocabuckets.registry.AbstractPlatform;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.function.Supplier;

// Fabric-side implementation of the item registry

public class FabricPlatform extends AbstractPlatform {

    // Register the item, this returns the result of registering the item
    public Supplier<Item> registerItem(String name, Supplier<Item> itemFactoery) {
        ApocaBuckets.LOGGER.info("Hello from fabric");
        ResourceLocation itemID = ResourceLocation.fromNamespaceAndPath(ApocaBuckets.MOD_ID, name);
        // Register the item.
        Item registeredItem = Registry.register(BuiltInRegistries.ITEM, itemID, itemFactoery.get());

        return () -> registeredItem;
    }

    public Supplier<Block> registerBlock(String name, Supplier<Block> blockFactory) {
        ResourceLocation blockID = ResourceLocation.fromNamespaceAndPath(ApocaBuckets.MOD_ID, name);

        Block registeredBlock = Registry.register(BuiltInRegistries.BLOCK, blockID, blockFactory.get());

        return () -> registeredBlock;
    }

    public FluidEntry<?> registerFluid(String name, CommonFluidType commonFluidType) {
        ResourceLocation flowingFluidId = ResourceLocation.fromNamespaceAndPath(ApocaBuckets.MOD_ID, name+"_flowing");
        ResourceLocation sourceFluidId = ResourceLocation.fromNamespaceAndPath(ApocaBuckets.MOD_ID, name);

        Supplier<Fluid> flowingFluidFactory = CommonFluidMap.get(name+"_flowing");
        Supplier<Fluid> sourceFluidFactory = CommonFluidMap.get(name);

        Fluid rFlowingFluid = Registry.register(BuiltInRegistries.FLUID, flowingFluidId, flowingFluidFactory.get());
        Fluid rSourceFluid = Registry.register(BuiltInRegistries.FLUID, sourceFluidId, sourceFluidFactory.get());

        return new FluidEntry<>(() -> rFlowingFluid, () -> rSourceFluid, commonFluidType, null);
    }

    @Override
    public Supplier<CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> iconFactory, List<Supplier<Item>> contents) {
        ResourceKey<CreativeModeTab> itemGroupKey = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(ApocaBuckets.MOD_ID, name));

        CreativeModeTab itemGroup = FabricItemGroup.builder()
                .icon(iconFactory)
                .title(Component.translatable(String.format("itemGroup.%s.%s", ApocaBuckets.MOD_ID, name)))
                .build();

        CreativeModeTab registeredTab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, itemGroupKey, itemGroup);

        ItemGroupEvents.modifyEntriesEvent(itemGroupKey).register(
                fItemGroup -> {
                    for (Supplier<Item> item : contents) {
                        fItemGroup.accept(item.get());
                    }
                }
        );

        return () -> registeredTab;
    }
}
