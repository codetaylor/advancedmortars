package com.codetaylor.mc.advancedmortars.lib.module.helper;

import com.codetaylor.mc.advancedmortars.lib.spi.IBlockColored;
import com.codetaylor.mc.advancedmortars.lib.spi.IBlockVariant;
import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.util.ResourceLocation;

public class BlockRegistrationHelper {

  public static ItemBlock[] createItemBlocks(Block... blocks) {

    ItemBlock[] result = new ItemBlock[blocks.length];

    for (int i = 0; i < blocks.length; i++) {
      ItemBlock itemBlock = BlockRegistrationHelper.createItemBlock(blocks[i]);
      result[i] = itemBlock;
    }

    return result;
  }

  public static ItemBlock createItemBlock(Block block) {

    ItemBlock itemBlock;

    if (block instanceof IBlockColored) {
      itemBlock = new ItemColored(block, ((IBlockColored) block).hasBlockColoredSubtypes());

    } else if (block instanceof IBlockVariant) {
      itemBlock = new ItemMultiTexture(block, block, ((IBlockVariant) block)::getName);

    } else {
      itemBlock = new ItemBlock(block);
    }

    BlockRegistrationHelper.setRegistryName(block, itemBlock);
    return itemBlock;
  }

  private static void setRegistryName(Block block, ItemBlock itemBlock) {

    ResourceLocation registryName = block.getRegistryName();
    Preconditions.checkNotNull(registryName, "Block %s has null registry name", block);
    itemBlock.setRegistryName(registryName);
  }

}
