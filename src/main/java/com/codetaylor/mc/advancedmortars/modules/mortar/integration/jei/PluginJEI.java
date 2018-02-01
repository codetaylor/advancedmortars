package com.codetaylor.mc.advancedmortars.modules.mortar.integration.jei;

import com.codetaylor.mc.advancedmortars.modules.mortar.ModuleMortar;
import com.codetaylor.mc.advancedmortars.modules.mortar.api.MortarAPI;
import com.codetaylor.mc.advancedmortars.modules.mortar.recipe.IRecipeMortar;
import com.codetaylor.mc.advancedmortars.modules.mortar.recipe.RecipeMortar;
import com.codetaylor.mc.advancedmortars.modules.mortar.reference.EnumMortarType;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.gui.elements.DrawableResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@JEIPlugin
public class PluginJEI
    implements IModPlugin {

  private static final String TEXTURE_BACKGROUND = "textures/gui/jei.png";
  private IJeiHelpers jeiHelpers;

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {

    this.jeiHelpers = registry.getJeiHelpers();

    for (EnumMortarType type : EnumMortarType.values()) {
      registry.addRecipeCategories(this.createCategory(type));
    }
  }

  @Override
  public void register(IModRegistry registry) {

    for (EnumMortarType type : EnumMortarType.values()) {
      registry.addRecipeCatalyst(
          new ItemStack(ModuleMortar.Blocks.MORTAR, 1, type.getMeta()),
          this.createUID(type)
      );
    }

    for (EnumMortarType type : EnumMortarType.values()) {
      registry.handleRecipes(RecipeMortar.class, JEIRecipeWrapperMortar::new, this.createUID(type));
    }

    for (EnumMortarType type : EnumMortarType.values()) {
      List<IRecipeMortar> recipeList = new ArrayList<>();
      MortarAPI.RECIPE_REGISTRY.getRecipes(type, recipeList);
      registry.addRecipes(recipeList, this.createUID(type));
    }
  }

  private JEICategoryMortar createCategory(EnumMortarType type) {

    return new JEICategoryMortar(
        this.createUID(type),
        this.createTitleTranslateKey(type),
        this.createBackground()
    );
  }

  private IDrawable createBackground() {

    ResourceLocation resourceLocation = new ResourceLocation(
        ModuleMortar.MOD_ID,
        TEXTURE_BACKGROUND
    );
    return new DrawableResource(resourceLocation, 0, 0, 116, 54, 0, 0, 0, 0, 116, 54) {

      @Override
      public void draw(Minecraft minecraft) {

        GlStateManager.enableBlend();
        super.draw(minecraft);
        GlStateManager.disableBlend();
      }
    };
  }

  private String createTitleTranslateKey(EnumMortarType type) {

    return ModuleMortar.Lang.JEI_CATEGORY_PREFIX + type.getName();
  }

  private String createUID(EnumMortarType type) {

    return ModuleMortar.MOD_ID + "_" + type.getName();
  }
}
