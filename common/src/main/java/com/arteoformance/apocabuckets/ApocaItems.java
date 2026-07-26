package com.arteoformance.apocabuckets;

import com.arteoformance.apocabuckets.item.ApocalypticBucket;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.function.Supplier;

public class ApocaItems {
    /*public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(MOD_ID, Registries.ITEM);*/

    public static Supplier<Item> APOCALYPTIC_BUCKET = ApocaBuckets.REGISTRY.registerItem("apocalyptic_bucket", () -> new ApocalypticBucket(
            Fluids.EMPTY,
            ArmorMaterials.DIAMOND,
            ArmorItem.Type.HELMET,
            new Item.Properties()
                    .stacksTo(1)
                    .durability(ArmorItem.Type.HELMET.getDurability(33))
    ));

    public static Supplier<Item> FLOOD_BUCKET = ApocaBuckets.REGISTRY.registerItem("flood_bucket", () -> new BucketItem(
            ApocaFluids.FLOOD_FLUID.getSource(),
            new Item.Properties()
                    .stacksTo(1)
                    
    ));

    public static Supplier<Item> ICE_BUCKET = ApocaBuckets.REGISTRY.registerItem("ice_bucket", () -> new BucketItem(
            ApocaFluids.ICE_FLUID.getSource(),
            new Item.Properties()
                    .stacksTo(1)
    ));

    public static Supplier<Item> DARKNESS_BUCKET = ApocaBuckets.REGISTRY.registerItem("darkness_bucket", () -> new BucketItem(
            ApocaFluids.DARKNESS_FLUID.getSource(),
            new Item.Properties()
                    .stacksTo(1)
    ));

    public static Supplier<Item> TOXIC_BUCKET = ApocaBuckets.REGISTRY.registerItem("toxic_bucket", () -> new BucketItem(
            ApocaFluids.TOXIC_FLUID.getSource(),
            new Item.Properties()
                    .stacksTo(1)
                    
    ));

    public static Supplier<Item> NULL_BUCKET = ApocaBuckets.REGISTRY.registerItem("null_bucket", () -> new SolidBucketItem(
            ApocaBlocks.NULL.get(),
            SoundEvents.BUCKET_EMPTY,
            new Item.Properties()
                    
                    .stacksTo(1)
    ));

    public static Supplier<Item> NULL2_BUCKET = ApocaBuckets.REGISTRY.registerItem("null2_bucket", () -> new SolidBucketItem(
            ApocaBlocks.NULL2.get(),
            SoundEvents.BUCKET_EMPTY,
            new Item.Properties()
                    
                    .stacksTo(1)
    ));

    public static Supplier<Item> CONCRETE_BUCKET = ApocaBuckets.REGISTRY.registerItem("concrete_bucket", () -> new SolidBucketItem(
            ApocaBlocks.CONCRETE.get(),
            SoundEvents.BUCKET_EMPTY,
            new Item.Properties()
                    
                    .stacksTo(1)
    ));

    public static Supplier<Item> FIRE_BUCKET = ApocaBuckets.REGISTRY.registerItem("fire_bucket", () -> new SolidBucketItem(
            ApocaBlocks.FIRE.get(),
            SoundEvents.BUCKET_EMPTY,
            new Item.Properties()
                    
                    .stacksTo(1)
    ));

    public static Supplier<Item> SPONGE_BUCKET = ApocaBuckets.REGISTRY.registerItem("sponge_bucket", () -> new SolidBucketItem(
            ApocaBlocks.SPONGE.get(),
            SoundEvents.BUCKET_EMPTY,
            new Item.Properties()
                    
                    .stacksTo(1)
    ));

    public static Supplier<CreativeModeTab> APOCALYPTIC_BUCKETS_ITEMS = ApocaBuckets.REGISTRY.registerCreativeTab(
            "apocalyptic_buckets",
            () -> new ItemStack(APOCALYPTIC_BUCKET.get()),
            List.of(
                    APOCALYPTIC_BUCKET,
                    FLOOD_BUCKET,
                    DARKNESS_BUCKET,
                    FIRE_BUCKET,
                    TOXIC_BUCKET,
                    ICE_BUCKET,
                    NULL_BUCKET,
                    NULL2_BUCKET,
                    CONCRETE_BUCKET,
                    SPONGE_BUCKET
            )
    );

    public static void init() {
    }
}
