package com.codetaylor.mc.advancedmortars.modules.mortar.render;

import com.codetaylor.mc.advancedmortars.lib.IRecipeOutputProvider;
import com.codetaylor.mc.advancedmortars.lib.gui.GuiHelper;
import com.codetaylor.mc.advancedmortars.modules.mortar.ModuleMortar;
import com.codetaylor.mc.advancedmortars.modules.mortar.tile.TileEntityMortarBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class HUDRender {

  private static final ResourceLocation TEXTURE = new ResourceLocation(
      ModuleMortar.MOD_ID,
      "textures/gui/hud_mortar.png"
  );

  public static final int TEXTURE_WIDTH = 44;
  public static final int TEXTURE_HEIGHT = 55;

  public static void render(ScaledResolution resolution) {

    Minecraft minecraft = Minecraft.getMinecraft();
    RayTraceResult rayTraceResult = minecraft.objectMouseOver;

    if (rayTraceResult == null
        || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {
      return;
    }

    BlockPos blockPos = rayTraceResult.getBlockPos();

    if (blockPos.getY() < 0 || blockPos.getY() >= 256) {
      // Sanity check.
      return;
    }

    TileEntity tileEntity = minecraft.world.getTileEntity(blockPos);

    if (tileEntity instanceof TileEntityMortarBase) {
      ItemStackHandler itemStackHandler = ((TileEntityMortarBase) tileEntity).getItemStackHandler();

      List<ItemStack> itemStackList = new ArrayList<>();

      for (int i = 0; i < itemStackHandler.getSlots(); i++) {
        ItemStack stackInSlot = itemStackHandler.getStackInSlot(i);

        if (stackInSlot.isEmpty()) {
          break;
        }

        itemStackList.add(stackInSlot);
      }

      float angle = (float) (Math.PI * 2 / itemStackList.size());
      double radius = 32;

      GlStateManager.enableBlend();
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
      GlStateManager.color(1, 1, 1, 1);
      RenderHelper.enableGUIStandardItemLighting();

      { // Render ingredients.
        for (int i = 0; i < itemStackList.size(); i++) {
          ItemStack itemStack = itemStackList.get(i);
          int x = (int) (MathHelper.cos(angle * i) * radius + resolution.getScaledWidth() / 2) - 8;
          int y = (int) (MathHelper.sin(angle * i) * radius + resolution.getScaledHeight() / 2) - 8;
          minecraft.getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);
          minecraft.getRenderItem().renderItemOverlays(minecraft.fontRenderer, itemStack, x, y);
        }
      }

      IRecipeOutputProvider recipe = ((TileEntityMortarBase) tileEntity).getRecipe();

      { // Render recipe output.
        if (recipe != null) {
          ItemStack output = recipe.getOutput();
          int x = (int) (radius + resolution.getScaledWidth() / 2) - 8 + 64;
          int y = resolution.getScaledHeight() / 2 - 8;
          minecraft.getRenderItem().renderItemAndEffectIntoGUI(output, x, y);
          minecraft.getRenderItem().renderItemOverlays(minecraft.fontRenderer, output, x, y);
        }
      }

      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableLighting();

      { // Render recipe output arrow.
        if (recipe != null) {
          int x = (int) (radius + resolution.getScaledWidth() / 2) - 8 + 30;
          int y = resolution.getScaledHeight() / 2 - 4;

          // Mouse button hint
          if (minecraft.player.getHeldItemMainhand().isEmpty()) {
            GuiHelper.drawTexturedRect(minecraft, TEXTURE, x, y, 8, 10, 100, 10, 23, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            x += 5;
          }

          // Arrow
          GuiHelper.drawTexturedRect(minecraft, TEXTURE, x + 5, y, 12, 11, 100, 32, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
      }

      { // Render mortar pick-up hint.
        int x = resolution.getScaledWidth() / 2 - 9;
        int y = resolution.getScaledHeight() / 2 - 64 - 16;
        GuiHelper.drawTexturedRect(minecraft, TEXTURE, x, y, 18, 22, 100, 18, 24, TEXTURE_WIDTH, TEXTURE_HEIGHT);
      }

      { // Render ingredient return hint.
        if (!((TileEntityMortarBase) tileEntity).isEmpty()
            && minecraft.player.getHeldItemMainhand().isEmpty()) {
          int x = resolution.getScaledWidth() / 2 - 9;
          int y = resolution.getScaledHeight() / 2 - 6;
          GuiHelper.drawTexturedRect(minecraft, TEXTURE, x, y, 18, 22, 100, 0, 23, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
      }

      { // Render mode graphic.
        if (recipe != null
            && minecraft.player.getHeldItemMainhand().isEmpty()) {
          int x = resolution.getScaledWidth() / 2 - 8 + 65;
          int y = resolution.getScaledHeight() / 2 - 8 - 20;
          GuiHelper.drawTexturedRect(minecraft, TEXTURE, x, y, 16, 23, 100, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
      }

      GlStateManager.disableBlend();
    }
  }

  private HUDRender() {
    //
  }

}
