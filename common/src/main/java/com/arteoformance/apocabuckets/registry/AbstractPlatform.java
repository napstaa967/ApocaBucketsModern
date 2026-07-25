package com.arteoformance.apocabuckets.registry;

import com.arteoformance.apocabuckets.mutliplatform.CommonFluidType;
import com.arteoformance.apocabuckets.mutliplatform.FluidEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractPlatform {
    public abstract Supplier<Item> registerItem(String name, Supplier<Item> itemFactory);
    public abstract Supplier<Block> registerBlock(String name, Supplier<Block> blockFactory);
    // Ignore type since type is nullable (who gaf bro)
    public abstract FluidEntry<?> registerFluid(String name, CommonFluidType commonFluidType);
    public abstract Supplier<CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> iconFactory, List<Supplier<Item>> contents);
}
