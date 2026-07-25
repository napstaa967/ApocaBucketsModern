package com.arteoformance.apocabuckets.fabric.client.datagen;

import com.arteoformance.apocabuckets.ApocaBlocks;
import com.arteoformance.apocabuckets.ApocaItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.model.ModelTemplates;

public class ApocaBucketsModelProvider extends FabricModelProvider {
    public ApocaBucketsModelProvider(FabricDataOutput output) {
        super(output);
    }


    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createTrivialCube(ApocaBlocks.NULL.get());
        blockStateModelGenerator.createTrivialCube(ApocaBlocks.NULL2.get());
        // No fire datagen because neoforge requires the render type set on the model itself
        // blockStateModelGenerator.createFloorFireModels(ApocaBlocks.FIRE.get());
    }


    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ApocaItems.DARKNESS_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ApocaItems.ICE_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ApocaItems.FLOOD_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ApocaItems.APOCALYPTIC_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ApocaItems.NULL_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ApocaItems.NULL2_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ApocaItems.CONCRETE_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ApocaItems.FIRE_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ApocaItems.TOXIC_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        //itemModelGenerator.generateFlatItem(ApocaItems.MIDAS_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ApocaItems.SPONGE_BUCKET.get(), ModelTemplates.FLAT_ITEM);
    }

    @Override
    public String getName() {
        return "ApocaBucketsModelProvider";
    }
}