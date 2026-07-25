package com.arteoformance.apocabuckets.fabric.client.datagen;

import com.arteoformance.apocabuckets.ApocaItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ApocaBucketsRecipeProvider extends FabricRecipeProvider {
    public ApocaBucketsRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeExporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ApocaItems.APOCALYPTIC_BUCKET.get())
                .pattern("bnb")
                .pattern(" b ")
                .define('b', Items.NETHER_BRICK)
                .define('n', Items.NETHER_STAR)
                .group("multi_bench")
                .unlockedBy(FabricRecipeProvider.getHasName(Items.NETHER_STAR), FabricRecipeProvider.has(Items.NETHER_STAR))
                .save(recipeExporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ApocaItems.FLOOD_BUCKET.get())
                .requires(ApocaItems.APOCALYPTIC_BUCKET.get())
                .requires(Items.DIAMOND)
                .unlockedBy(FabricRecipeProvider.getHasName(ApocaItems.APOCALYPTIC_BUCKET.get()), FabricRecipeProvider.has(ApocaItems.APOCALYPTIC_BUCKET.get()))
                .save(recipeExporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ApocaItems.DARKNESS_BUCKET.get())
                .requires(ApocaItems.APOCALYPTIC_BUCKET.get())
                .requires(Items.CRYING_OBSIDIAN)
                .unlockedBy(FabricRecipeProvider.getHasName(ApocaItems.APOCALYPTIC_BUCKET.get()), FabricRecipeProvider.has(ApocaItems.APOCALYPTIC_BUCKET.get()))
                .save(recipeExporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ApocaItems.FIRE_BUCKET.get())
                .requires(ApocaItems.APOCALYPTIC_BUCKET.get())
                .requires(Items.FLINT_AND_STEEL)
                .unlockedBy(FabricRecipeProvider.getHasName(ApocaItems.APOCALYPTIC_BUCKET.get()), FabricRecipeProvider.has(ApocaItems.APOCALYPTIC_BUCKET.get()))
                .save(recipeExporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ApocaItems.TOXIC_BUCKET.get())
                .requires(ApocaItems.FLOOD_BUCKET.get())
                .requires(Items.SPORE_BLOSSOM)
                .unlockedBy(FabricRecipeProvider.getHasName(ApocaItems.FLOOD_BUCKET.get()), FabricRecipeProvider.has(ApocaItems.FLOOD_BUCKET.get()))
                .save(recipeExporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ApocaItems.ICE_BUCKET.get())
                .requires(ApocaItems.FLOOD_BUCKET.get())
                .requires(Items.BLUE_ICE)
                .unlockedBy(FabricRecipeProvider.getHasName(ApocaItems.FLOOD_BUCKET.get()), FabricRecipeProvider.has(ApocaItems.FLOOD_BUCKET.get()))
                .save(recipeExporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ApocaItems.NULL_BUCKET.get())
                .requires(ApocaItems.APOCALYPTIC_BUCKET.get())
                .requires(Items.BONE_MEAL)
                .unlockedBy(FabricRecipeProvider.getHasName(ApocaItems.APOCALYPTIC_BUCKET.get()), FabricRecipeProvider.has(ApocaItems.APOCALYPTIC_BUCKET.get()))
                .save(recipeExporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ApocaItems.NULL2_BUCKET.get())
                .requires(ApocaItems.APOCALYPTIC_BUCKET.get())
                .requires(Items.MAGENTA_GLAZED_TERRACOTTA)
                .unlockedBy(FabricRecipeProvider.getHasName(ApocaItems.APOCALYPTIC_BUCKET.get()), FabricRecipeProvider.has(ApocaItems.APOCALYPTIC_BUCKET.get()))
                .save(recipeExporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ApocaItems.CONCRETE_BUCKET.get())
                .requires(ApocaItems.NULL_BUCKET.get())
                .requires(Items.MAGENTA_CONCRETE_POWDER)
                .requires(Items.CYAN_CONCRETE_POWDER)
                .requires(Items.BROWN_CONCRETE_POWDER)
                .requires(Items.GRAY_CONCRETE_POWDER)
                .unlockedBy(FabricRecipeProvider.getHasName(ApocaItems.NULL_BUCKET.get()), FabricRecipeProvider.has(ApocaItems.NULL_BUCKET.get()))
                .save(recipeExporter);
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ApocaItems.SPONGE_BUCKET.get())
                .requires(ApocaItems.APOCALYPTIC_BUCKET.get())
                .requires(Items.SPONGE)
                .unlockedBy(FabricRecipeProvider.getHasName(ApocaItems.APOCALYPTIC_BUCKET.get()), FabricRecipeProvider.has(ApocaItems.APOCALYPTIC_BUCKET.get()))
                .save(recipeExporter);
    }
}
