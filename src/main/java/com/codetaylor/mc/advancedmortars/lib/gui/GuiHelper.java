package com.sudoplay.mc.pwcustom.lib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
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
      double u0,
      double v0,
      double u1,
      double v1
  ) {

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

  private GuiHelper() {
    //
  }

}
