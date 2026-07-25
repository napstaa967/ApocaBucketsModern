package com.arteoformance.apocabuckets.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FireSpreadingBlock extends AirSpreadingBlock {
    public FireSpreadingBlock(Properties properties, int tickDelay, @Nullable BlockState decayInto, @Nullable Supplier<Item> bucket, @Nullable Iterable<Direction> spreadDirections) {
        super(properties, tickDelay, decayInto, bucket, spreadDirections);
    }
}
