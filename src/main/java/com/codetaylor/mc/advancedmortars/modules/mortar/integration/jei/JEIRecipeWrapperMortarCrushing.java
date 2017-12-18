package com.sudoplay.mc.pwcustom.modules.mortar.integration.jei;

import com.sudoplay.mc.pwcustom.lib.gui.GuiHelper;
import com.sudoplay.mc.pwcustom.modules.mortar.ModuleMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.recipe.RecipeMortarCrushing;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JEIRecipeWrapperMortarCrushing
    implements IRecipeWrapper {

  private List<ItemStack> input;
  private ItemStack output;
  private ResourceLocation texture;

  public JEIRecipeWrapperMortarCrushing(RecipeMortarCrushing recipe) {

    this.input = new ArrayList<>();
    this.input.addAll(Arrays.asList(recipe.getIngredient().getMatchingStacks()));
    this.output = recipe.getOutput();

    this.texture = new ResourceLocation(
        ModuleMortar.MOD_ID,
        "textures/gui/jei_mortar_crushing.png"
    );

  }

  @Override
  public void getIngredients(@Nonnull IIngredients ingredients) {

    ingredients.setInputs(ItemStack.class, this.input);
    ingredients.setOutput(ItemStack.class, this.output);
  }

  @Override
  public void drawInfo(
      Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY
  ) {

    GuiHelper.drawTexturedRect(minecraft, this.texture, 20, 16, 14, 21, 100, 0, 0, 1, 1);

    /*TextureManager renderEngine = minecraft.getRenderManager().renderEngine;
    renderEngine.bindTexture(this.texture);

    int x = 20;
    int y = 16;
    int width = 14;
    int height = 21;
    int zLevel = 100;

    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
    bufferbuilder
        .pos(x, (y + height), zLevel)
        .tex(0, 1)
        .endVertex();
    bufferbuilder
        .pos((x + width), (y + height), zLevel)
        .tex(1, 1)
        .endVertex();
    bufferbuilder
        .pos((x + width), y, zLevel)
        .tex(1, 0)
        .endVertex();
    bufferbuilder
        .pos(x, y, zLevel)
        .tex(0, 0)
        .endVertex();
    tessellator.draw();*/
  }
}
