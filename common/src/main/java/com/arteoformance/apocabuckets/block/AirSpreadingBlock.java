package com.arteoformance.apocabuckets.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class AirSpreadingBlock extends SpreadingBlock {

    public Iterable<Direction> spreadableDirections = List.of(
            Direction.NORTH,
            Direction.SOUTH,
            Direction.WEST,
            Direction.EAST,
            Direction.DOWN
    );
    @Nullable
    public BlockState decayResult;
    public int tickDelay = 10;

    public AirSpreadingBlock(Properties properties, int tickDelay, @Nullable BlockState decayInto, @Nullable Supplier<Item> bucket, @Nullable Iterable<Direction> spreadDirections) {
        super(properties, tickDelay, decayInto, bucket, spreadDirections);
    }

    public AirSpreadingBlock(Properties properties, int tickDelay, @Nullable BlockState decayInto) {
        this(properties, tickDelay, decayInto, null, null);
    }

    public AirSpreadingBlock(Properties properties, int tickDelay) {
        this(properties, tickDelay, null, null, null);
    }

    public AirSpreadingBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        for(Direction direction : spreadableDirections) {
            BlockPos targetPos = blockPos.relative(direction);
            BlockState targetState = serverLevel.getBlockState(targetPos);
            if (targetState.is(BlockTags.REPLACEABLE)) {
                serverLevel.setBlock(targetPos, this.defaultBlockState(), 3);
            }
            if (null != decayResult) {
                serverLevel.setBlock(blockPos, decayResult, 3);
            }
        }
    }
}
