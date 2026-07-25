package com.arteoformance.apocabuckets.fabric.client;

import com.arteoformance.apocabuckets.fabric.client.datagen.ApocaBucketsBlockTagProvider;
import com.arteoformance.apocabuckets.fabric.client.datagen.ApocaBucketsModelProvider;
import com.arteoformance.apocabuckets.fabric.client.datagen.ApocaBucketsRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ApocaBucketsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ApocaBucketsModelProvider::new);
		pack.addProvider(ApocaBucketsBlockTagProvider::new);
		pack.addProvider(ApocaBucketsRecipeProvider::new);
	}
}
