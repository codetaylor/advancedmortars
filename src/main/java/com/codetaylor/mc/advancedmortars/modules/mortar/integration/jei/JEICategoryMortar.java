package com.codetaylor.mc.advancedmortars.modules.mortar.integration.jei;

import com.codetaylor.mc.advancedmortars.modules.mortar.ModuleMortar;
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

    if (recipeWrapper instanceof JEIRecipeWrapperMortar) {

      IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

      List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
      List<List<ItemStack>> outputList = ingredients.getOutputs(ItemStack.class);
      List<ItemStack> outputs = outputList.get(0);
      boolean secondaryOutputExists = outputList.size() > 1;

      int outputYPosition = 18;

      if (secondaryOutputExists) {
        outputYPosition = 4;
      }

      stacks.init(0, false, 94, outputYPosition);
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

      if (secondaryOutputExists) {
        stacks.init(count + 1, false, 94, outputYPosition + 23);
        stacks.set(count + 1, outputList.get(1));
      }
    }
  }

}
