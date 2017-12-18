package com.sudoplay.mc.pwcustom.modules.mortar.integration.crafttweaker;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseUndoable;
import com.sudoplay.mc.pwcustom.lib.util.CTUtil;
import com.sudoplay.mc.pwcustom.modules.mortar.api.MortarAPI;
import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarType;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

import static com.sudoplay.mc.pwcustom.modules.mortar.integration.crafttweaker.ZenMortar.NAME;

@ZenClass(NAME)
public class ZenMortar {

  public static final String NAME = "mods.advancedmortars.Mortar";

  @ZenMethod
  public static void addMixingRecipe(String[] types, IItemStack output, int duration, IIngredient[] inputs) {

    PluginCraftTweaker.LATE_ADDITIONS.add(new AddMixing(
        types,
        InputHelper.toStack(output),
        duration,
        CTUtil.toIngredientArray(inputs)
    ));
  }

  private static class AddMixing
      extends BaseUndoable {

    private String[] types;
    private final ItemStack output;
    private final int duration;
    private final Ingredient[] inputs;

    /* package */ AddMixing(
        String[] types,
        ItemStack output,
        int duration,
        Ingredient[] inputs
    ) {

      super(NAME);
      this.types = types;
      this.output = output;
      this.duration = duration;
      this.inputs = inputs;
    }

    @Override
    public void apply() {

      for (String type : this.types) {
        EnumMortarType enumMortarType = EnumMortarType.fromName(type);

        if (enumMortarType != null) {
          MortarAPI.RECIPE_REGISTRY.addMixingRecipe(enumMortarType, this.output, this.duration, this.inputs);

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

  @ZenMethod
  public static void addCrushingRecipe(String[] types, IItemStack output, int duration, IIngredient input) {

    PluginCraftTweaker.LATE_ADDITIONS.add(new AddCrushing(
        types,
        InputHelper.toStack(output),
        duration,
        CTUtil.toIngredient(input)
    ));
  }

  private static class AddCrushing
      extends BaseUndoable {

    private String[] types;
    private final ItemStack output;
    private final int duration;
    private final Ingredient input;

    /* package */ AddCrushing(String[] types, ItemStack output, int duration, Ingredient input) {

      super(NAME);
      this.types = types;
      this.output = output;
      this.duration = duration;
      this.input = input;
    }

    @Override
    public void apply() {

      for (String type : this.types) {
        EnumMortarType enumMortarType = EnumMortarType.fromName(type);

        if (enumMortarType != null) {
          MortarAPI.RECIPE_REGISTRY.addCrushingRecipe(enumMortarType, this.output, this.duration, this.input);

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
