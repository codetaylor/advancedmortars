package com.codetaylor.mc.advancedmortars.modules.mortar.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;

public class Utils {

  public static boolean ingredientApplyWithNBT(Ingredient ingredient, ItemStack toMatch) {

    if (toMatch == null) {
      return false;
    }

    ItemStack[] matchingStacks = ingredient.getMatchingStacks();

    for (int i = 0; i < matchingStacks.length; i++) {

      if (matchingStacks[i].getItem() != toMatch.getItem()) {
        continue;
      }

      int meta = matchingStacks[i].getMetadata();

      if (meta != OreDictionary.WILDCARD_VALUE && meta != toMatch.getMetadata()) {
        continue;
      }

      if (!ItemStack.areItemStackTagsEqual(matchingStacks[i], toMatch)) {
        continue;
      }

      return true;
    }

    return false;
  }

  private Utils() {
    //
  }

}
