package com.arteoformance.apocabuckets.block;

import com.arteoformance.apocabuckets.ApocaBuckets;
import com.arteoformance.apocabuckets.BlockPalettes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class ColorBlock extends SpreadingBlock {
    private Map<DyeColor, BlockState> DECAY_CANDIDATES = BlockPalettes.CONCRETE_POWDER;

    public static final EnumProperty<DyeColor> TINT = EnumProperty.create("tint", DyeColor.class);

    public ColorBlock(Properties properties, int tickDelay, @Nullable Supplier<Item> bucket, @Nullable Map<DyeColor, BlockState> decayStates, @Nullable Iterable<Direction> spreadDirections) {
        super(properties, tickDelay, null, bucket, spreadDirections);
        registerDefaultState(defaultBlockState().setValue(TINT, DyeColor.WHITE));
        if (decayStates != null) {
            DECAY_CANDIDATES = decayStates;
        }
    }

    public ColorBlock(Properties properties, int tickDelay, @Nullable Map<DyeColor, BlockState> decayStates) {
        this(properties, tickDelay, null, decayStates, null);
    }

    public ColorBlock(Properties properties, int tickDelay) {
        this(properties, tickDelay, null, null, null);
    }

    public ColorBlock(Properties properties) {
        this(properties, 20, null, null, null);
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        for(Direction direction : spreadableDirections) {
            BlockPos targetPos = blockPos.relative(direction);
            BlockState targetState = serverLevel.getBlockState(targetPos);
            MapColor color = targetState.getMapColor(serverLevel, targetPos);
            // Avoid spreading into own decay material
            if (!targetState.is(this) && !targetState.is(BlockTags.AIR) && !DECAY_CANDIDATES.containsValue(targetState)) {
                // Get block color
                DyeColor blockColor = ApocaBuckets.getClosestDyeTo(color.col & 0xFFFFFF);
                serverLevel.setBlock(targetPos, blockState.setValue(TINT, blockColor), 3);
            }
            // Get decay candidates depending on color
            @Nullable
            BlockState targetDye = DECAY_CANDIDATES.get((DyeColor) blockState.getValue(TINT));
            if (targetDye == null) {
                List<BlockState> vList = new ArrayList<>(DECAY_CANDIDATES.values());
                int rIndex = new Random().nextInt(vList.size());
                targetDye = vList.get(rIndex);
            }
            serverLevel.setBlock(blockPos, targetDye, 3);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TINT);
    }
}
