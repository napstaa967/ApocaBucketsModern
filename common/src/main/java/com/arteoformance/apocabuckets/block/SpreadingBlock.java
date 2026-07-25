package com.arteoformance.apocabuckets.block;

import com.arteoformance.apocabuckets.ApocaItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class SpreadingBlock extends Block implements BucketPickup {

    public Iterable<Direction> spreadableDirections = List.of(Direction.values());
    @Nullable
    public BlockState decayResult;
    public int tickDelay = 20;
    private Supplier<Item> bucket;

    public SpreadingBlock(Properties properties, int tickDelay, @Nullable BlockState decayInto, @Nullable Supplier<Item> bucket, @Nullable Iterable<Direction> spreadDirections) {
        super(properties);
        this.decayResult = decayInto;
        this.tickDelay = tickDelay;
        if (spreadDirections != null) {
            this.spreadableDirections = spreadDirections;
        }
        if (bucket != null) {
            this.bucket = bucket;
        }
    }

    public SpreadingBlock(Properties properties, int tickDelay, @Nullable BlockState decayInto, @Nullable Supplier<Item> bucket) {
        this(properties, tickDelay, decayInto, bucket, null);
    }

    public SpreadingBlock(Properties properties, int tickDelay, @Nullable BlockState decayInto) {
        this(properties, tickDelay, decayInto, null, null);
    }

    public SpreadingBlock(Properties properties, int tickDelay) {
        this(properties, tickDelay, null, null, null);
    }

    public SpreadingBlock(Properties properties) {
        super(properties);
    }

    protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        level.scheduleTick(blockPos, blockState.getBlock(), tickDelay);
    }

    protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        level.scheduleTick(blockPos, blockState.getBlock(), tickDelay);
    }

    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        for(Direction direction : spreadableDirections) {
            BlockPos targetPos = blockPos.relative(direction);
            BlockState targetState = serverLevel.getBlockState(targetPos);
            if (!targetState.is(this) && !targetState.is(BlockTags.AIR) ) {
                serverLevel.setBlock(targetPos, this.defaultBlockState(), 3);
            }
            if (null != decayResult) {
                serverLevel.setBlock(blockPos, decayResult, 3);
            }
        }
    }

    @Override
    public ItemStack pickupBlock(@Nullable Player player, LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        levelAccessor.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);
        return new ItemStack(bucket.get());
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }
}
