package com.sudoplay.mc.pwcustom.modules.mortar.recipe;

import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

public class RegistryRecipeMortar {

  private EnumMap<EnumMortarType, List<RecipeMortarMixing>> recipeMapMixing;
  private EnumMap<EnumMortarType, List<RecipeMortarCrushing>> recipeMapCrushing;

  public RegistryRecipeMortar() {

    this.recipeMapMixing = new EnumMap<>(EnumMortarType.class);
    this.recipeMapCrushing = new EnumMap<>(EnumMortarType.class);
  }

  public List<IRecipeMortar> getMixingRecipes(EnumMortarType type, List<IRecipeMortar> result) {

    List<RecipeMortarMixing> recipes = this.recipeMapMixing.get(type);

    if (recipes != null) {
      result.addAll(recipes);
    }

    return result;
  }

  public List<IRecipeMortar> getCrushingRecipes(EnumMortarType type, List<IRecipeMortar> result) {

    List<RecipeMortarCrushing> recipes = this.recipeMapCrushing.get(type);

    if (recipes != null) {
      result.addAll(recipes);
    }

    return result;
  }

  @Nonnull
  public RecipeMortarMixing addMixingRecipe(EnumMortarType type, ItemStack output, int duration, Ingredient[] inputs) {

    NonNullList<Ingredient> inputList = NonNullList.create();
    Collections.addAll(inputList, inputs);
    RecipeMortarMixing recipe = new RecipeMortarMixing(output, duration, inputList);
    List<RecipeMortarMixing> list = this.recipeMapMixing.computeIfAbsent(type, k -> new ArrayList<>());
    list.add(recipe);
    return recipe;
  }

  public RecipeMortarCrushing addCrushingRecipe(EnumMortarType type, ItemStack output, int duration, Ingredient input) {

    RecipeMortarCrushing recipe = new RecipeMortarCrushing(output, duration, input);
    List<RecipeMortarCrushing> list = this.recipeMapCrushing.computeIfAbsent(type, k -> new ArrayList<>());
    list.add(recipe);
    return recipe;
  }

  @Nullable
  public RecipeMortarMixing findMixingRecipe(EnumMortarType type, ItemStack[] inputs) {

    List<RecipeMortarMixing> list = this.recipeMapMixing.get(type);

    if (list == null || list.isEmpty()) {
      return null;
    }

    for (RecipeMortarMixing recipe : list) {

      if (recipe.matches(inputs)) {
        return recipe;
      }
    }

    return null;
  }

  @Nullable
  public RecipeMortarCrushing findCrushingRecipe(EnumMortarType type, ItemStack input) {

    List<RecipeMortarCrushing> list = this.recipeMapCrushing.get(type);

    if (list == null || list.isEmpty()) {
      return null;
    }

    for (RecipeMortarCrushing recipe : list) {

      if (recipe.matches(input)) {
        return recipe;
      }
    }

    return null;
  }
}
