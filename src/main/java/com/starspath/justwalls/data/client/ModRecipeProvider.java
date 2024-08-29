package com.starspath.justwalls.data.client;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STRAW_SCRAP.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', Items.STICK)
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STRAW_SCRAP.get(), 3)
                .pattern("ABA")
                .pattern("ABA")
                .pattern("ABA")
                .define('A', ItemTags.LEAVES)
                .define('B', Items.STICK)
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .save(consumer, "leaves_straw_scrap_recipe");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOOD_SCRAP.get(), 2)
                .pattern("AB")
                .pattern("BA")
                .define('A', Items.STICK)
                .define('B', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(Items.STICK), has(ItemTags.WOODEN_SLABS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_SCRAP.get(), 2)
                .pattern("AB")
                .pattern("BA")
                .define('A', Items.CLAY_BALL)
                .define('B', ItemTags.STONE_TOOL_MATERIALS)
                .unlockedBy(getHasName(Items.CLAY_BALL), has(ItemTags.STONE_TOOL_MATERIALS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.METAL_SCRAP.get(), 2)
                .pattern("AB")
                .pattern("BC")
                .define('A', Items.IRON_NUGGET)
                .define('B', Items.COPPER_INGOT)
                .define('C', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_NUGGET))
                .save(consumer);

        oreSmelting(consumer, List.of(ModItems.METAL_SCRAP.get()), RecipeCategory.MISC, ModItems.ARMORED_SCRAP.get(), 0.25f, 400, "armored_scrap");
        oreBlasting(consumer, List.of(ModItems.METAL_SCRAP.get()), RecipeCategory.MISC, ModItems.ARMORED_SCRAP.get(), 0.25f, 200, "armored_scrap");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SUPER_HAMMER.get())
                .requires(ModItems.STRAW_SCRAP.get())
                .requires(ModItems.WOOD_SCRAP.get())
                .unlockedBy(getHasName(Items.STICK), has(ItemTags.WOODEN_SLABS))
                .save(consumer);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup){
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  JustWalls.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
