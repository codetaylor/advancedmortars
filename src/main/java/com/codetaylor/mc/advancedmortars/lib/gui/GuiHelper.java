package com.codetaylor.mc.advancedmortars.lib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiHelper {

  public static void drawTexturedRect(
      Minecraft minecraft,
      ResourceLocation texture,
      int x,
      int y,
      int width,
      int height,
      int zLevel,
      int texPosX,
      int texPosY,
      int texWidth,
      int texHeight
  ) {

    double u0 = texPosX / (double) texWidth;
    double v0 = texPosY / (double) texHeight;

    double u1 = u0 + width / (double) texWidth;
    double v1 = v0 + height / (double) texHeight;

    TextureManager renderEngine = minecraft.getRenderManager().renderEngine;
    renderEngine.bindTexture(texture);

    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
    bufferbuilder
        .pos(x, (y + height), zLevel)
        .tex(u0, v1)
        .endVertex();
    bufferbuilder
        .pos((x + width), (y + height), zLevel)
        .tex(u1, v1)
        .endVertex();
    bufferbuilder
        .pos((x + width), y, zLevel)
        .tex(u1, v0)
        .endVertex();
    bufferbuilder
        .pos(x, y, zLevel)
        .tex(u0, v0)
        .endVertex();
    tessellator.draw();
  }

  public static void drawColoredRect(
      BufferBuilder renderer,
      int x,
      int y,
      int width,
      int height,
      int red,
      int green,
      int blue,
      int alpha
  ) {

    renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
    renderer.pos((double) (x + 0), (double) (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
    renderer.pos((double) (x + 0), (double) (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
    renderer.pos((double) (x + width), (double) (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
    renderer.pos((double) (x + width), (double) (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
    Tessellator.getInstance().draw();
  }

  public static void drawDurabilityBar(int xPosition, int yPosition, double durabilityPercentage, int color, int width) {

    GlStateManager.disableLighting();
    GlStateManager.disableDepth();
    GlStateManager.disableTexture2D();
    GlStateManager.disableAlpha();
    GlStateManager.disableBlend();
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    int subWidth = Math.round((float) width - (float) durabilityPercentage * (float) width);
    xPosition += 2;
    yPosition += 13;
    GuiHelper.drawColoredRect(bufferbuilder, xPosition, yPosition, width, 2, 0, 0, 0, 255);

    int red = color >> 16 & 255;
    int green = color >> 8 & 255;
    int blue = color & 255;
    GuiHelper.drawColoredRect(bufferbuilder, xPosition, yPosition, subWidth, 1, red, green, blue, 255);
    GlStateManager.enableBlend();
    GlStateManager.enableAlpha();
    GlStateManager.enableTexture2D();
    GlStateManager.enableLighting();
    GlStateManager.enableDepth();
  }

  private GuiHelper() {
    //
  }

}
