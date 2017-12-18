package com.sudoplay.mc.pwcustom.modules.mortar.integration.jei;

import com.sudoplay.mc.pwcustom.modules.mortar.recipe.RecipeMortarMixing;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JEIRecipeWrapperMortarMixing
    implements IRecipeWrapper {

  private List<List<ItemStack>> inputs;
  private ItemStack output;

  public JEIRecipeWrapperMortarMixing(RecipeMortarMixing recipe) {

    this.inputs = new ArrayList<>();

    for (Ingredient input : recipe.getIngredients()) {
      this.inputs.add(Arrays.asList(input.getMatchingStacks()));
    }

    this.output = recipe.getOutput();
  }

  @Override
  public void getIngredients(@Nonnull IIngredients ingredients) {

    ingredients.setInputLists(ItemStack.class, this.inputs);
    ingredients.setOutput(ItemStack.class, this.output);
  }

}
