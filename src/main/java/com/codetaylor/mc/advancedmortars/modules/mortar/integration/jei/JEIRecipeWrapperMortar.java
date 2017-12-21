package com.codetaylor.mc.advancedmortars.modules.mortar.integration.jei;

import com.codetaylor.mc.advancedmortars.modules.mortar.recipe.RecipeMortar;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JEIRecipeWrapperMortar
    implements IRecipeWrapper {

  private List<List<ItemStack>> inputs;
  private ItemStack output;
  private ItemStack secondaryOutput;
  private float secondaryOutputChance;

  public JEIRecipeWrapperMortar(RecipeMortar recipe) {

    this.inputs = new ArrayList<>();

    for (Ingredient input : recipe.getIngredients()) {
      this.inputs.add(Arrays.asList(input.getMatchingStacks()));
    }

    this.output = recipe.getOutput();

    this.secondaryOutput = recipe.getSecondaryOutput();
    this.secondaryOutputChance = recipe.getSecondaryOutputChance();
  }

  @Override
  public void getIngredients(@Nonnull IIngredients ingredients) {

    ingredients.setInputLists(ItemStack.class, this.inputs);
    ingredients.setOutput(ItemStack.class, this.output);
  }

  public ItemStack getSecondaryOutput() {

    return this.secondaryOutput;
  }

  public float getSecondaryOutputChance() {

    return this.secondaryOutputChance;
  }

  @Override
  public void drawInfo(
      Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY
  ) {

  }
}
