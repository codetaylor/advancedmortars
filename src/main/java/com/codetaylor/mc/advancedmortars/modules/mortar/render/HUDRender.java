package com.sudoplay.mc.pwcustom.modules.mortar.render;

import com.sudoplay.mc.pwcustom.lib.IRecipeOutputProvider;
import com.sudoplay.mc.pwcustom.lib.gui.GuiHelper;
import com.sudoplay.mc.pwcustom.modules.mortar.ModuleMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarMode;
import com.sudoplay.mc.pwcustom.modules.mortar.tile.TileEntityMortarBase;
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

  private static final ResourceLocation TEXTURE_MODE = new ResourceLocation(
      ModuleMortar.MOD_ID,
      "textures/gui/hud_mortar_mode.png"
  );

  private static final ResourceLocation TEXTURE_RETURN = new ResourceLocation(
      ModuleMortar.MOD_ID,
      "textures/gui/hud_mortar_return.png"
  );

  private static final ResourceLocation TEXTURE_CRUSHING = new ResourceLocation(
      ModuleMortar.MOD_ID,
      "textures/gui/hud_mortar_crushing.png"
  );

  private static final ResourceLocation TEXTURE_MIXING = new ResourceLocation(
      ModuleMortar.MOD_ID,
      "textures/gui/hud_mortar_mixing.png"
  );

  public static void render(ScaledResolution resolution) {

    Minecraft minecraft = Minecraft.getMinecraft();
    RayTraceResult rayTraceResult = minecraft.objectMouseOver;

    if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {
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

          if (((TileEntityMortarBase) tileEntity).getMortarMode() == EnumMortarMode.CRUSHING) {
            minecraft.getRenderItem().renderItemOverlays(minecraft.fontRenderer, itemStack, x, y);
          }
        }
      }

      IRecipeOutputProvider recipe = ((TileEntityMortarBase) tileEntity).getRecipe();

      { // Render recipe output.
        if (recipe != null) {
          ItemStack output = recipe.getOutput();
          int x = (int) (radius + resolution.getScaledWidth() / 2) - 8 + 64;
          int y = resolution.getScaledHeight() / 2 - 8;
          minecraft.getRenderItem().renderItemAndEffectIntoGUI(output, x, y);
        }
      }

      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableLighting();

      { // Render recipe output arrow.
        if (recipe != null) {
          int x = (int) (radius + resolution.getScaledWidth() / 2) - 8 + 28;
          int y = resolution.getScaledHeight() / 2 - 8;
          GuiHelper.drawTexturedRect(minecraft, TEXTURE, x, y, 22, 15, 100, 0, 0, 1, 1);
        }
      }

      { // Render ingredient return hint.
        if (!((TileEntityMortarBase) tileEntity).isEmpty()
            && minecraft.player.getHeldItemMainhand().isEmpty()) {
          int x = resolution.getScaledWidth() / 2 - 8;
          int y = resolution.getScaledHeight() / 2 - 8;
          GuiHelper.drawTexturedRect(minecraft, TEXTURE_RETURN, x, y, 18, 21, 100, 0, 0, 1, 1);
        }
      }

      { // Render mode graphic.
        int x = resolution.getScaledWidth() / 2 - 8 - 64;
        int y = resolution.getScaledHeight() / 2 - 8 - 1;
        EnumMortarMode mortarMode = ((TileEntityMortarBase) tileEntity).getMortarMode();

        if (((TileEntityMortarBase) tileEntity).isEmpty()) {
          x = resolution.getScaledWidth() / 2 - 8;
        }

        if (mortarMode == EnumMortarMode.CRUSHING) {
          GuiHelper.drawTexturedRect(minecraft, TEXTURE_CRUSHING, x, y, 16, 23, 100, 0, 0, 1, 1);

        } else if (mortarMode == EnumMortarMode.MIXING) {
          GuiHelper.drawTexturedRect(minecraft, TEXTURE_MIXING, x, y, 16, 23, 100, 0, 0, 1, 1);
        }

        if (((TileEntityMortarBase) tileEntity).isEmpty()
            && minecraft.player.getHeldItemMainhand().isEmpty()) {
          GuiHelper.drawTexturedRect(minecraft, TEXTURE_MODE, x - 20, y + 5, 18, 10, 100, 0, 0, 1, 1);
        }
      }

      /*{ // Render mortar mode string.
        String mode = I18n.format(((TileEntityMortarBase) tileEntity).getMortarModeString());
        //String modeWithLabel = I18n.format(ModuleMortar.Lang.MORTAR_MODE_LABEL, mode);

        int x = resolution.getScaledWidth() / 2 - minecraft.fontRenderer.getStringWidth(mode) / 2;
        int y = resolution.getScaledHeight() / 2 + 48;
        minecraft.fontRenderer.drawStringWithShadow(mode, x, y, 0xFFFFFF);

        if (((TileEntityMortarBase) tileEntity).isEmpty()
            && minecraft.player.getHeldItemMainhand().isEmpty()) {
          GuiHelper.drawTexturedRect(minecraft, TEXTURE_MODE, x - 21, y - 1, 16, 8, 100, 0, 0, 1, 1);
        }
      }*/

      /*if (ModuleMortar.IS_DEV) {
        String durabilityString = "Durability: " + ((TileEntityMortarBase) tileEntity).getDurability()
            + "/" + ((TileEntityMortarBase) tileEntity).getMaxDurability();

        int x = resolution.getScaledWidth() / 2 - minecraft.fontRenderer.getStringWidth(durabilityString) / 2;
        int y = resolution.getScaledHeight() / 2 + 58;
        minecraft.fontRenderer.drawStringWithShadow(durabilityString, x, y, 0xFFFFFF);
      }*/

      GlStateManager.disableBlend();
    }
  }

  private HUDRender() {
    //
  }

}
