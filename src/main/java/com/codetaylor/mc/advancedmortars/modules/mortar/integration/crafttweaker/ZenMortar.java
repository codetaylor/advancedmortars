package com.codetaylor.mc.advancedmortars.modules.mortar.integration.crafttweaker;

import com.codetaylor.mc.advancedmortars.lib.util.CraftTweakerUtil;
import com.codetaylor.mc.advancedmortars.modules.mortar.api.MortarAPI;
import com.codetaylor.mc.advancedmortars.modules.mortar.integration.crafttweaker.mtlib.BaseUndoable;
import com.codetaylor.mc.advancedmortars.modules.mortar.integration.crafttweaker.mtlib.InputHelper;
import com.codetaylor.mc.advancedmortars.modules.mortar.integration.crafttweaker.mtlib.LogHelper;
import com.codetaylor.mc.advancedmortars.modules.mortar.reference.EnumMortarType;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.List;

import static com.codetaylor.mc.advancedmortars.modules.mortar.integration.crafttweaker.ZenMortar.NAME;

@ZenClass(NAME)
public class ZenMortar {

  public static final String NAME = "mods.advancedmortars.Mortar";

  @ZenMethod
  public static void addRecipe(String[] types, IItemStack output, int duration, IIngredient[] inputs) {

    ZenMortar.addRecipe(
        types,
        output,
        duration,
        null,
        0,
        inputs
    );
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

    // Check that inputs are defined and within the bounds.
    if (inputs == null || inputs.length == 0) {
      CraftTweakerUtil.logError("No inputs defined");
      return;

    } else if (inputs.length > 8) {
      CraftTweakerUtil.logError("Maximum number of 8 input ingredients exceeded: " + inputs.length);
      return;
    }

    // Check that all types are valid.
    for (String type : types) {
      EnumMortarType enumMortarType = EnumMortarType.fromName(type);

      if (enumMortarType == null) {
        CraftTweakerUtil.logError("Invalid mortar type: " + type);
        return;
      }
    }

    PluginCraftTweaker.LATE_ADDITIONS.add(new Add(
        types,
        InputHelper.toStack(output),
        duration,
        InputHelper.toStack(secondaryOutput),
        secondaryOutputChance,
        inputs
    ));
  }

  private static class Add
      extends BaseUndoable {

    private String[] types;
    private final ItemStack output;
    private final int duration;
    private final ItemStack secondaryOutput;
    private final float secondaryOutputChance;
    private final IIngredient[] inputs;

    /* package */ Add(
        String[] types,
        ItemStack output,
        int duration,
        ItemStack secondaryOutput,
        float secondaryOutputChance,
        IIngredient[] inputs
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
          Ingredient[] ingredients = new Ingredient[this.inputs.length];

          for (int i = 0; i < this.inputs.length; i++) {
            ItemStack[] itemStacks;

            if (this.inputs[i] instanceof IOreDictEntry) {
              NonNullList<ItemStack> ores = OreDictionary.getOres(((IOreDictEntry) this.inputs[i]).getName());
              itemStacks = ores.toArray(new ItemStack[ores.size()]);

            } else if (this.inputs[i] instanceof IItemStack) {
              itemStacks = new ItemStack[]{InputHelper.toStack((IItemStack) this.inputs[i])};

            } else if (this.inputs[i] instanceof IngredientStack) {
              List<IItemStack> items = this.inputs[i].getItems();
              itemStacks = new ItemStack[items.size()];

              for (int j = 0; j < items.size(); j++) {
                itemStacks[j] = InputHelper.toStack(items.get(j));
              }

            } else {
              LogHelper.logError("Unknown input type: " + this.inputs[i]);
              return;
            }

            ingredients[i] = Ingredient.fromStacks(itemStacks);

            for (ItemStack itemStack : ingredients[i].getMatchingStacks()) {
              itemStack.setCount(this.inputs[i].getAmount());
            }
          }

          MortarAPI.RECIPE_REGISTRY.addRecipe(
              enumMortarType,
              this.output,
              this.duration,
              this.secondaryOutput,
              this.secondaryOutputChance,
              ingredients
          );

        } else {
          // Sanity check. Should never happen because we check the mortar types in the ZenMethod
          // in order to log a line number at the point of failure.
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
