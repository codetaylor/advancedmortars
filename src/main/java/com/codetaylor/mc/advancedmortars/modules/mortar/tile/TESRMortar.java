package com.codetaylor.mc.advancedmortars.modules.mortar.tile;

import com.codetaylor.mc.advancedmortars.ModAdvancedMortars;
import com.codetaylor.mc.advancedmortars.modules.mortar.recipe.IRecipeMortar;
import com.codetaylor.mc.advancedmortars.modules.mortar.reference.EnumMortarMode;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.opengl.GL11;

public class TESRMortar
    extends TileEntitySpecialRenderer<TileEntityMortarBase> {

  private IBakedModel bakedModel;
  //private ResourceLocation PESTLE_TEXTURE = new ResourceLocation(ModuleMortar.MOD_ID, "textures/blocks/soul_gravel.png");

  @Override
  public void render(
      TileEntityMortarBase tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha
  ) {

    GlStateManager.pushMatrix();
    this.renderModel(x, y, z, tile);
    GlStateManager.popMatrix();

    GlStateManager.pushMatrix();
    GlStateManager.translate((float) x + 0.5F, (float) y, (float) z + 0.5F);

    ItemStackHandler itemStackHandler = tile.getItemStackHandler();
    double offsetY = 0.125;
    double scale = 1.0;

    if (tile.getMortarMode() == EnumMortarMode.CRUSHING
        && !tile.isEmpty()
        && itemStackHandler.getStackInSlot(0).getItem() instanceof ItemBlock) {
      scale = 3.0;
      offsetY = 0.2;
    }

    for (int i = 0; i < itemStackHandler.getSlots(); i++) {
      offsetY = this.renderItem(itemStackHandler.getStackInSlot(i), offsetY, scale);
    }

    GlStateManager.popMatrix();
  }

  private IBakedModel getBakedModel() {

    if (this.bakedModel == null) {

      IModel model;

      try {
        /*
         * The texture for the pestle is actually loaded in the mortar block model. The texture specified in the
         * pestle model never actually gets baked into the sprite atlas.
         */
        model = ModelLoaderRegistry.getModel(new ResourceLocation(ModAdvancedMortars.MOD_ID, "block/pestle"));

      } catch (Exception e) {
        throw new RuntimeException(e);
      }

      this.bakedModel = model.bake(
          TRSRTransformation.identity(), DefaultVertexFormats.ITEM,
          location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString())
      );
    }

    return bakedModel;
  }

  private void renderModel(double x, double y, double z, TileEntityMortarBase tile) {

    World world = tile.getWorld();
    IBlockState blockState = world.getBlockState(tile.getPos());
    int current = tile.getCraftingProgress();
    IRecipeMortar recipe = tile.getRecipe();
    float percentage = (recipe != null) ? current / (float) recipe.getDuration() : 0;

    GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);
    GlStateManager.rotate(360 * percentage, 0, 1, 0);
    GlStateManager.translate(-0.5,-0.5,-0.5);

    // Render Model -----------------------------------------

    GlStateManager.pushMatrix();

    GlStateManager.translate(
        -tile.getPos().getX(),
        -tile.getPos().getY(),
        -tile.getPos().getZ()
    );

    RenderHelper.disableStandardItemLighting();
    this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

    if (Minecraft.isAmbientOcclusionEnabled()) {
      GlStateManager.shadeModel(GL11.GL_SMOOTH);

    } else {
      GlStateManager.shadeModel(GL11.GL_FLAT);
    }

    Tessellator tessellator = Tessellator.getInstance();
    tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
    Minecraft.getMinecraft()
        .getBlockRendererDispatcher()
        .getBlockModelRenderer()
        .renderModel(
            world,
            this.getBakedModel(),
            blockState,
            tile.getPos(),
            Tessellator.getInstance().getBuffer(),
            false,
            0
        );
    tessellator.draw();

    RenderHelper.enableStandardItemLighting();
    GlStateManager.popMatrix();

    // End: Render Model ----------------------------------------
  }

  private double renderItem(ItemStack itemStack, double offsetY, double scale) {

    if (!itemStack.isEmpty()) {

      Item item = itemStack.getItem();

      if (item instanceof ItemBlock) {
        Block block = ((ItemBlock) item).getBlock();
        int lightValue = block.getLightValue(block.getStateFromMeta(itemStack.getMetadata()));

        if (lightValue == 0) {
          RenderHelper.enableStandardItemLighting();
          GlStateManager.enableLighting();
        }
      }

      GlStateManager.pushMatrix();

      if (item instanceof ItemSkull) {
        GlStateManager.translate(0, offsetY + 0.05, 0);
        GlStateManager.scale(.2f * scale, .2f * scale, .2f * scale);
        offsetY += 0.1;

      } else if (item instanceof ItemBlock) {
        GlStateManager.translate(0, offsetY, 0);
        GlStateManager.scale(.1f * scale, .1f * scale, .1f * scale);
        //GlStateManager.rotate(90, 1, 0, 0);
        offsetY += 0.1;

      } else {
        GlStateManager.translate(0, offsetY - 0.05, 0);
        GlStateManager.scale(.2f * scale, .2f * scale, .2f * scale);
        GlStateManager.rotate(270, 1, 0, 0);
        offsetY += 0.015;
      }

      GlStateManager.pushMatrix();
      Minecraft.getMinecraft().getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.NONE);

      GlStateManager.popMatrix();
      GlStateManager.popMatrix();
    }

    return offsetY;
  }
}
