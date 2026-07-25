package com.arteoformance.apocabuckets;

import com.arteoformance.apocabuckets.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class ApocaBlocks {

    public static final Supplier<Block> NULL = ApocaBuckets.REGISTRY.registerBlock(
            "null",
            () -> new SpreadingBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE),
                    20
            )
    );

    public static final Supplier<Block> NULL2 = ApocaBuckets.REGISTRY.registerBlock(
            "null2",
            () -> new SpreadingBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_CONCRETE),
                    20,
                    Blocks.AIR.defaultBlockState(),
                    ApocaItems.NULL2_BUCKET
            )
    );

    public static final Supplier<Block> CONCRETE = ApocaBuckets.REGISTRY.registerBlock(
            "concrete",
            () -> new ColorBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_CONCRETE),
                    20,
                    ApocaItems.CONCRETE_BUCKET,
                    null,
                    null
            )
    );

    public static final Supplier<Block> FIRE = ApocaBuckets.REGISTRY.registerBlock(
            "fire",
            () -> new FireSpreadingBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.FIRE),
                    20,
                    null,
                    ApocaItems.FIRE_BUCKET,
                    null
            )
    );

    public static final Supplier<Block> SPONGE = ApocaBuckets.REGISTRY.registerBlock(
            "sponge",
            () -> new Sponge(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SPONGE),
                    10
            )
    );

    public static final Supplier<Block> TOXIC = ApocaBuckets.REGISTRY.registerBlock(
            "toxic",
            () -> new ToxicLiquidBlock(
                    (FlowingFluid) ApocaFluids.TOXIC_FLUID.getSource(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
            )
    );

    public static final Supplier<Block> FLOOD = ApocaBuckets.REGISTRY.registerBlock(
            "flood",
            () -> new GenericLiquidBlock(
                    (FlowingFluid) ApocaFluids.FLOOD_FLUID.getSource(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
            )
    );

    public static final Supplier<Block> DARKNESS = ApocaBuckets.REGISTRY.registerBlock(
            "darkness",
            () -> new GenericLiquidBlock(
                    (FlowingFluid) ApocaFluids.DARKNESS_FLUID.getSource(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA)
            )
    );

    public static final Supplier<Block> ICE = ApocaBuckets.REGISTRY.registerBlock(
            "ice",
            () -> new GenericLiquidBlock(
                    (FlowingFluid) ApocaFluids.ICE_FLUID.getSource(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
            )
    );

    public static void init() {
    }
}
