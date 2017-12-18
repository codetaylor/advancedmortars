package com.codetaylor.mc.advancedmortars.modules.mortar.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class RecipeMortarCrushing
    implements IRecipeMortar {

  private ItemStack output;
  private int duration;
  private Ingredient input;

  public RecipeMortarCrushing(ItemStack output, int duration, Ingredient input) {

    this.duration = duration;
    this.input = input;
    this.output = output;
  }

  @Override
  public ItemStack getOutput() {

    return this.output.copy();
  }

  @Override
  public int getDuration() {

    return this.duration;
  }

  public Ingredient getIngredient() {

    return this.input;
  }

  public boolean matches(ItemStack input) {

    return this.input.apply(input);
  }
}
