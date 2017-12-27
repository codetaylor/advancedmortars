package com.codetaylor.mc.advancedmortars.modules.mortar.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeMortar
    implements IRecipeMortar {

  private ItemStack output;
  private ItemStack secondaryOutput;
  private float secondaryOutputChance;
  private int duration;
  private List<Ingredient> inputs;

  public RecipeMortar(ItemStack output, int duration, ItemStack secondaryOutput, float secondaryOutputChance, List<Ingredient> inputs) {

    this.duration = duration;
    this.output = output;
    this.secondaryOutput = secondaryOutput;
    this.secondaryOutputChance = secondaryOutputChance;

    this.inputs = new ArrayList<>(inputs);
  }

  public List<Ingredient> getIngredients() {

    return this.inputs;
  }

  @Override
  public ItemStack getOutput() {

    return this.output.copy();
  }

  @Override
  public ItemStack getSecondaryOutput() {

    return this.secondaryOutput;
  }

  @Override
  public float getSecondaryOutputChance() {

    return this.secondaryOutputChance;
  }

  @Override
  public int getDuration() {

    return this.duration;
  }

  public boolean matches(ItemStack[] inputs) {

    return this.checkAvailableIngredients(inputs);
  }

  private boolean checkAvailableIngredients(ItemStack[] inputs) {

    boolean[] matchedInputs = new boolean[inputs.length];
    int[] availableCounts = new int[inputs.length];

    for (int i = 0; i < inputs.length; i++) {
      availableCounts[i] = inputs[i].getCount();
    }

    for (int i = 0; i < this.inputs.size(); i++) { // for each recipe ingredient
      Ingredient ingredient = this.inputs.get(i);
      ItemStack[] matchingStacks = ingredient.getMatchingStacks();

      if (matchingStacks.length == 0) {
        return false;
      }

      int requiredCount = matchingStacks[0].getCount();

      for (int j = 0; j < inputs.length; j++) { // for each provided ingredient
        boolean ingredientMatches = ingredient.apply(inputs[j]);

        if (ingredientMatches) {
          matchedInputs[j] = true;
        }

        if (availableCounts[j] > 0
            && ingredientMatches) {

          if (requiredCount == availableCounts[j]) {
            requiredCount -= availableCounts[j];
            availableCounts[j] = 0;
            // requirements satisfied

          } else if (requiredCount < availableCounts[j]) {
            int availableCount = availableCounts[j];
            availableCounts[j] -= requiredCount;
            requiredCount -= availableCount;
            // requirements satisfied

          } else { // requiredCount > availableCounts[j]
            requiredCount -= availableCounts[j];
            availableCounts[j] = 0;
            // only partial requirements satisfied
          }
        }
      }

      if (requiredCount > 0) {
        // requirements were not met
        return false;
      }
    }

    for (boolean matchedInput : matchedInputs) {

      if (!matchedInput) {
        return false;
      }
    }

    return true;
  }

}
