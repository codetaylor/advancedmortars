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

    return this.getUniqueItemCount(inputs) == this.inputs.size()
        && this.checkAvailableIngredients(inputs);
  }

  private int getUniqueItemCount(ItemStack[] inputs) {

    List<ItemStack> itemList = new ArrayList<>();

    for (ItemStack input : inputs) {

      boolean hasItem = false;

      for (ItemStack itemStack : itemList) {

        if (itemStack.getItem() == input.getItem()
            && itemStack.getMetadata() == input.getMetadata()) {
          hasItem = true;
        }
      }

      if (!hasItem) {
        itemList.add(input);
      }
    }

    return itemList.size();
  }

  private boolean checkAvailableIngredients(ItemStack[] inputs) {

    int[] availableCounts = new int[inputs.length];

    for (int i = 0; i < inputs.length; i++) {
      availableCounts[i] = inputs[i].getCount();
    }

    for (int i = 0; i < this.inputs.size(); i++) {
      Ingredient ingredient = this.inputs.get(i);
      ItemStack[] matchingStacks = ingredient.getMatchingStacks();

      if (matchingStacks.length == 0) {
        return false;
      }

      int requiredCount = matchingStacks[0].getCount();

      for (int j = 0; j < inputs.length; j++) {
        ItemStack input = inputs[j];

        if (availableCounts[j] > 0
            && ingredient.apply(input)) {

          if (requiredCount == availableCounts[j]) {
            requiredCount -= availableCounts[j];
            availableCounts[j] = 0;
            break; // requirements satisfied

          } else if (requiredCount < availableCounts[j]) {
            int availableCount = availableCounts[j];
            availableCounts[j] -= requiredCount;
            requiredCount -= availableCount;
            break; // requirements satisfied

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

    return true;
  }

}
