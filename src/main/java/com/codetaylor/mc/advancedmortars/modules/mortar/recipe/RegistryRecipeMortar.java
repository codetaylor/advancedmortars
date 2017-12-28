package com.codetaylor.mc.advancedmortars.modules.mortar.recipe;

import com.codetaylor.mc.advancedmortars.modules.mortar.reference.EnumMortarType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

public class RegistryRecipeMortar {

  private EnumMap<EnumMortarType, List<RecipeMortar>> recipeMap;

  public RegistryRecipeMortar() {

    this.recipeMap = new EnumMap<>(EnumMortarType.class);
  }

  public List<IRecipeMortar> getRecipes(EnumMortarType type, List<IRecipeMortar> result) {

    List<RecipeMortar> recipes = this.recipeMap.get(type);

    if (recipes != null) {
      result.addAll(recipes);
    }

    return result;
  }

  @Nonnull
  public RecipeMortar addRecipe(
      EnumMortarType type,
      ItemStack output,
      int duration,
      ItemStack secondaryOutput,
      float secondaryOutputChance,
      Ingredient[] inputs
  ) {

    List<Ingredient> inputList = new ArrayList<>();
    Collections.addAll(inputList, inputs);
    RecipeMortar recipe = new RecipeMortar(output, duration, secondaryOutput, secondaryOutputChance, inputList);
    List<RecipeMortar> list = this.recipeMap.computeIfAbsent(type, k -> new ArrayList<>());
    list.add(recipe);
    return recipe;
  }

  @Nullable
  public RecipeMortar findRecipe(EnumMortarType type, ItemStack[] inputs) {

    List<RecipeMortar> list = this.recipeMap.get(type);

    if (list == null || list.isEmpty()) {
      return null;
    }

    for (RecipeMortar recipe : list) {

      if (recipe.matches(inputs)) {
        return recipe;
      }
    }

    return null;
  }

  public boolean matchesPartial(EnumMortarType type, ItemStack[] inputs) {

    List<RecipeMortar> list = this.recipeMap.get(type);

    if (list == null || list.isEmpty()) {
      return false;
    }

    for (RecipeMortar recipe : list) {

      if (recipe.matchesPartial(inputs)) {
        return true;
      }
    }

    return false;
  }

}
