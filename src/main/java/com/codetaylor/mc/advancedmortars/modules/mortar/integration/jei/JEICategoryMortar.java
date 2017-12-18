package com.sudoplay.mc.pwcustom.modules.mortar.integration.jei;

import com.sudoplay.mc.pwcustom.modules.mortar.ModuleMortar;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class JEICategoryMortar
    implements IRecipeCategory {

  private String uid;
  private String titleTranslateKey;
  private IDrawable background;

  public JEICategoryMortar(String uid, String titleTranslateKey, IDrawable background) {

    this.uid = uid;
    this.titleTranslateKey = titleTranslateKey;
    this.background = background;
  }

  @Override
  public String getUid() {

    return this.uid;
  }

  @Override
  public String getTitle() {

    return I18n.format(this.titleTranslateKey);
  }

  @Override
  public String getModName() {

    return ModuleMortar.MOD_ID;
  }

  @Override
  public IDrawable getBackground() {

    return this.background;
  }

  @Override
  public void setRecipe(
      @Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients
  ) {

    if (recipeWrapper instanceof JEIRecipeWrapperMortarMixing) {
      this.setRecipeMixing(recipeLayout, (JEIRecipeWrapperMortarMixing) recipeWrapper, ingredients);

    } else if (recipeWrapper instanceof JEIRecipeWrapperMortarCrushing) {
      this.setRecipeCrushing(recipeLayout, (JEIRecipeWrapperMortarCrushing) recipeWrapper, ingredients);
    }
  }

  private void setRecipeMixing(
      IRecipeLayout recipeLayout, JEIRecipeWrapperMortarMixing recipeWrapper, IIngredients ingredients
  ) {

    IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

    List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
    List<ItemStack> outputs = ingredients.getOutputs(ItemStack.class).get(0);

    stacks.init(0, false, 94, 18);
    stacks.set(0, outputs);

    int count = inputs.size();
    float angle = (float) (Math.PI * 2 / (float) count);
    float radius = 18 + 8;
    int offsetX = 17;
    int offsetY = 17;

    for (int index = 0; index < count; index++) {

      int x = (int) (MathHelper.cos(angle * index) * radius) + offsetX;
      int y = (int) (MathHelper.sin(angle * index) * radius) + offsetY;
      stacks.init(index + 1, true, x, y);
    }

    for (int i = 1; i <= count; i++) {
      stacks.set(i, inputs.get(i - 1));
    }

    //recipeLayout.setRecipeTransferButton(156, 66);
  }

  private void setRecipeCrushing(
      IRecipeLayout recipeLayout, JEIRecipeWrapperMortarCrushing recipeWrapper, IIngredients ingredients
  ) {

    IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

    List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
    List<ItemStack> outputs = ingredients.getOutputs(ItemStack.class).get(0);

    stacks.init(0, false, 94, 18);
    stacks.set(0, outputs);

    stacks.init(1, true, 40, 18);
    stacks.set(1, inputs.get(0));

    //recipeLayout.setRecipeTransferButton(156, 66);
  }
}
