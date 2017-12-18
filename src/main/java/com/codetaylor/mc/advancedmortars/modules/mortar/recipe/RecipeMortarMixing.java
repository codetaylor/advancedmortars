package com.codetaylor.mc.advancedmortars.modules.mortar.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class RecipeMortarMixing
    implements IRecipeMortar {

  private ItemStack output;
  private int duration;
  private NonNullList<Ingredient> inputs;

  public RecipeMortarMixing(ItemStack output, int duration, NonNullList<Ingredient> inputs) {

    this.duration = duration;
    this.inputs = inputs;
    this.output = output;
  }

  public NonNullList<Ingredient> getIngredients() {

    return this.inputs;
  }

  @Override
  public ItemStack getOutput() {

    return this.output.copy();
  }

  @Override
  public int getDuration() {

    return this.duration;
  }

  public boolean matches(ItemStack[] inputs) {

    int count = 0;
    List<ItemStack> itemList = new ArrayList<>();

    for (int i = 0; i < inputs.length; i++) {
      ItemStack itemStack = inputs[i];

      if (!itemStack.isEmpty()) {
        count += 1;
        itemList.add(itemStack);
      }
    }

    if (count != this.inputs.size()) {
      return false;
    }

    return RecipeMatcher.findMatches(itemList, this.inputs) != null;
  }
}
