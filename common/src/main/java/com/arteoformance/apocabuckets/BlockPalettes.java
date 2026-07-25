package com.arteoformance.apocabuckets;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import java.util.Map;

import static java.util.Map.entry;

public final class BlockPalettes {
    public static final Map<DyeColor, BlockState> CONCRETE_POWDER = Map.ofEntries(
            entry(DyeColor.BLACK,     Blocks.BLACK_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.WHITE,     Blocks.WHITE_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.BLUE,      Blocks.BLUE_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.GREEN,     Blocks.GREEN_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.PURPLE,    Blocks.PURPLE_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.PINK,      Blocks.PINK_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.MAGENTA,   Blocks.MAGENTA_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.GRAY,      Blocks.GRAY_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.LIGHT_GRAY,Blocks.LIGHT_GRAY_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.CYAN,      Blocks.CYAN_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.LIME,      Blocks.LIME_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.BROWN,     Blocks.BROWN_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.LIGHT_BLUE,Blocks.LIGHT_BLUE_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.RED,       Blocks.RED_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.ORANGE,    Blocks.ORANGE_CONCRETE_POWDER.defaultBlockState()),
            entry(DyeColor.YELLOW,    Blocks.YELLOW_CONCRETE_POWDER.defaultBlockState())
    );

    public static final Map<DyeColor, BlockState> CONCRETE = Map.ofEntries(
            entry(DyeColor.BLACK,     Blocks.BLACK_CONCRETE.defaultBlockState()),
            entry(DyeColor.WHITE,     Blocks.WHITE_CONCRETE.defaultBlockState()),
            entry(DyeColor.BLUE,      Blocks.BLUE_CONCRETE.defaultBlockState()),
            entry(DyeColor.GREEN,     Blocks.GREEN_CONCRETE.defaultBlockState()),
            entry(DyeColor.PURPLE,    Blocks.PURPLE_CONCRETE.defaultBlockState()),
            entry(DyeColor.PINK,      Blocks.PINK_CONCRETE.defaultBlockState()),
            entry(DyeColor.MAGENTA,   Blocks.MAGENTA_CONCRETE.defaultBlockState()),
            entry(DyeColor.GRAY,      Blocks.GRAY_CONCRETE.defaultBlockState()),
            entry(DyeColor.LIGHT_GRAY,Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState()),
            entry(DyeColor.CYAN,      Blocks.CYAN_CONCRETE.defaultBlockState()),
            entry(DyeColor.LIME,      Blocks.LIME_CONCRETE.defaultBlockState()),
            entry(DyeColor.BROWN,     Blocks.BROWN_CONCRETE.defaultBlockState()),
            entry(DyeColor.LIGHT_BLUE,Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState()),
            entry(DyeColor.RED,       Blocks.RED_CONCRETE.defaultBlockState()),
            entry(DyeColor.ORANGE,    Blocks.ORANGE_CONCRETE.defaultBlockState()),
            entry(DyeColor.YELLOW,    Blocks.YELLOW_CONCRETE.defaultBlockState())
    );

    public static final Map<DyeColor, BlockState> WOOL = Map.ofEntries(
            entry(DyeColor.BLACK,     Blocks.BLACK_WOOL.defaultBlockState()),
            entry(DyeColor.WHITE,     Blocks.WHITE_WOOL.defaultBlockState()),
            entry(DyeColor.BLUE,      Blocks.BLUE_WOOL.defaultBlockState()),
            entry(DyeColor.GREEN,     Blocks.GREEN_WOOL.defaultBlockState()),
            entry(DyeColor.PURPLE,    Blocks.PURPLE_WOOL.defaultBlockState()),
            entry(DyeColor.PINK,      Blocks.PINK_WOOL.defaultBlockState()),
            entry(DyeColor.MAGENTA,   Blocks.MAGENTA_WOOL.defaultBlockState()),
            entry(DyeColor.GRAY,      Blocks.GRAY_WOOL.defaultBlockState()),
            entry(DyeColor.LIGHT_GRAY,Blocks.LIGHT_GRAY_WOOL.defaultBlockState()),
            entry(DyeColor.CYAN,      Blocks.CYAN_WOOL.defaultBlockState()),
            entry(DyeColor.LIME,      Blocks.LIME_WOOL.defaultBlockState()),
            entry(DyeColor.BROWN,     Blocks.BROWN_WOOL.defaultBlockState()),
            entry(DyeColor.LIGHT_BLUE,Blocks.LIGHT_BLUE_WOOL.defaultBlockState()),
            entry(DyeColor.RED,       Blocks.RED_WOOL.defaultBlockState()),
            entry(DyeColor.ORANGE,    Blocks.ORANGE_WOOL.defaultBlockState()),
            entry(DyeColor.YELLOW,    Blocks.YELLOW_WOOL.defaultBlockState())
    );

    public static final Map<DyeColor, BlockState> ORE_BLOCKS = Map.ofEntries(
            entry(DyeColor.BLACK,     Blocks.NETHERITE_BLOCK.defaultBlockState()),
            entry(DyeColor.WHITE,     Blocks.QUARTZ_BLOCK.defaultBlockState()),
            entry(DyeColor.BLUE,      Blocks.LAPIS_BLOCK.defaultBlockState()),
            entry(DyeColor.GREEN,     Blocks.EMERALD_BLOCK.defaultBlockState()),
            entry(DyeColor.PURPLE,    Blocks.BUDDING_AMETHYST.defaultBlockState()),
            entry(DyeColor.PINK,      Blocks.AMETHYST_BLOCK.defaultBlockState()),
            entry(DyeColor.MAGENTA,   Blocks.AMETHYST_BLOCK.defaultBlockState()),
            entry(DyeColor.GRAY,      Blocks.COAL_BLOCK.defaultBlockState()),
            entry(DyeColor.LIGHT_GRAY,Blocks.IRON_BLOCK.defaultBlockState()),
            entry(DyeColor.CYAN,      Blocks.SCULK.defaultBlockState()),
            entry(DyeColor.LIME,      Blocks.EMERALD_BLOCK.defaultBlockState()),
            entry(DyeColor.BROWN,     Blocks.ANCIENT_DEBRIS.defaultBlockState()),
            entry(DyeColor.LIGHT_BLUE,Blocks.DIAMOND_BLOCK.defaultBlockState()),
            entry(DyeColor.RED,       Blocks.REDSTONE_BLOCK.defaultBlockState()),
            entry(DyeColor.ORANGE,    Blocks.COPPER_BLOCK.defaultBlockState()),
            entry(DyeColor.YELLOW,    Blocks.GOLD_BLOCK.defaultBlockState())
    );
}