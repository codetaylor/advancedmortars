package com.codetaylor.mc.advancedmortars.modules.mortar.integration.crafttweaker;

import com.codetaylor.mc.advancedmortars.lib.util.CTUtil;
import com.codetaylor.mc.advancedmortars.modules.mortar.api.MortarAPI;
import com.codetaylor.mc.advancedmortars.modules.mortar.integration.crafttweaker.mtlib.BaseUndoable;
import com.codetaylor.mc.advancedmortars.modules.mortar.integration.crafttweaker.mtlib.InputHelper;
import com.codetaylor.mc.advancedmortars.modules.mortar.integration.crafttweaker.mtlib.LogHelper;
import com.codetaylor.mc.advancedmortars.modules.mortar.reference.EnumMortarType;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

import static com.codetaylor.mc.advancedmortars.modules.mortar.integration.crafttweaker.ZenMortar.NAME;

@ZenClass(NAME)
public class ZenMortar {

  public static final String NAME = "mods.advancedmortars.Mortar";

  @ZenMethod
  public static void addRecipe(String[] types, IItemStack output, int duration, IIngredient[] inputs) {

    PluginCraftTweaker.LATE_ADDITIONS.add(new Add(
        types,
        InputHelper.toStack(output),
        duration,
        null,
        0,
        CTUtil.toIngredientArray(inputs)
    ));
  }

  @ZenMethod
  public static void addRecipe(
      String[] types,
      IItemStack output,
      int duration,
      IItemStack secondaryOutput,
      float secondaryOutputChance,
      IIngredient[] inputs
  ) {

    PluginCraftTweaker.LATE_ADDITIONS.add(new Add(
        types,
        InputHelper.toStack(output),
        duration,
        InputHelper.toStack(secondaryOutput),
        secondaryOutputChance,
        CTUtil.toIngredientArray(inputs)
    ));
  }

  private static class Add
      extends BaseUndoable {

    private String[] types;
    private final ItemStack output;
    private final int duration;
    private final ItemStack secondaryOutput;
    private final float secondaryOutputChance;
    private final Ingredient[] inputs;

    /* package */ Add(
        String[] types,
        ItemStack output,
        int duration,
        ItemStack secondaryOutput,
        float secondaryOutputChance,
        Ingredient[] inputs
    ) {

      super(NAME);
      this.types = types;
      this.output = output;
      this.duration = duration;
      this.secondaryOutput = secondaryOutput;
      this.secondaryOutputChance = secondaryOutputChance;
      this.inputs = inputs;
    }

    @Override
    public void apply() {

      for (String type : this.types) {
        EnumMortarType enumMortarType = EnumMortarType.fromName(type);

        if (enumMortarType != null) {
          MortarAPI.RECIPE_REGISTRY.addRecipe(
              enumMortarType,
              this.output,
              this.duration,
              this.secondaryOutput,
              this.secondaryOutputChance,
              this.inputs
          );

        } else {
          LogHelper.logError("Invalid mortar type: " + type + ". Valid types are: " + Arrays.toString(EnumMortarType.NAMES));
        }
      }
    }

    @Override
    protected String getRecipeInfo() {

      return LogHelper.getStackDescription(this.output);
    }
  }

}
