package com.arteoformance.apocabuckets.block;

import com.arteoformance.apocabuckets.ApocaItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Sponge extends SpreadingBlock {
    public Sponge(Properties properties, int tickDelay) {
        super(properties, tickDelay, null, ApocaItems.SPONGE_BUCKET);
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        for(Direction direction : spreadableDirections) {
            BlockPos targetPos = blockPos.relative(direction);
            BlockState targetState = serverLevel.getBlockState(targetPos);
            if (targetState.getBlock() instanceof LiquidBlock) {
                serverLevel.setBlock(targetPos, this.defaultBlockState(), 3);
            }
            serverLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
        }
    }
}