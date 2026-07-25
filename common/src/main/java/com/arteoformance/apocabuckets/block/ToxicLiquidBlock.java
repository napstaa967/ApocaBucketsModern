package com.arteoformance.apocabuckets.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class ToxicLiquidBlock extends LiquidBlock {
    public ToxicLiquidBlock(FlowingFluid flowingFluid, Properties properties) {
        super(flowingFluid, properties);
    }

    protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && !(livingEntity instanceof Zombie)) {
            if (livingEntity instanceof Player || livingEntity.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 300, -10));
            } else {
                Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
                zombie.setPos(livingEntity.position());
                zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
                level.addFreshEntity(zombie);
                livingEntity.discard();
            }
        }
        super.entityInside(blockState, level, blockPos, entity);
    }
}
