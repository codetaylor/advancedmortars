package com.codetaylor.mc.advancedmortars.lib;

import net.minecraft.item.ItemStack;

public interface IRecipeOutputProvider {

  ItemStack getOutput();

  ItemStack getSecondaryOutput();

  float getSecondaryOutputChance();

}
