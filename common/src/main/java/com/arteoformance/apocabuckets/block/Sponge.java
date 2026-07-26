package com.arteoformance.apocabuckets.block;

import com.arteoformance.apocabuckets.ApocaItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class Sponge extends SpreadingBlock {
    public Sponge(Properties properties, int tickDelay) {
        super(properties, tickDelay, null, ApocaItems.SPONGE_BUCKET);
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        for(Direction direction : spreadableDirections) {
            BlockPos targetPos = blockPos.relative(direction);
            BlockState targetState = serverLevel.getBlockState(targetPos);
            if (targetState.getBlock() instanceof LiquidBlock ) {
                serverLevel.setBlock(targetPos, this.defaultBlockState(), 3);
            } else if (targetState.getBlock() instanceof SimpleWaterloggedBlock) {
                // De-waterlog the block
                ((SimpleWaterloggedBlock) targetState.getBlock()).placeLiquid(serverLevel, blockPos, targetState, Fluids.EMPTY.defaultFluidState());
            }
            serverLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
        }
    }
}