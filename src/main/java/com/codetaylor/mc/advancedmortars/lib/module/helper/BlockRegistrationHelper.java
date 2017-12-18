package com.sudoplay.mc.pwcustom.lib.module.helper;

import com.google.common.base.Preconditions;
import com.sudoplay.mc.pwcustom.lib.spi.IBlockColored;
import com.sudoplay.mc.pwcustom.lib.spi.IBlockVariant;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.util.ResourceLocation;

public class BlockRegistrationHelper {

  public ItemBlock[] createItemBlocks(Block... blocks) {

    ItemBlock[] result = new ItemBlock[blocks.length];

    for (int i = 0; i < blocks.length; i++) {
      ItemBlock itemBlock = this.createItemBlock(blocks[i]);
      result[i] = itemBlock;
    }

    return result;
  }

  public ItemBlock createItemBlock(Block block) {

    ItemBlock itemBlock;

    if (block instanceof IBlockColored) {
      itemBlock = new ItemColored(block, ((IBlockColored) block).hasBlockColoredSubtypes());

    } else if (block instanceof IBlockVariant) {
      itemBlock = new ItemMultiTexture(block, block, ((IBlockVariant) block)::getName);

    } else {
      itemBlock = new ItemBlock(block);
    }

    this.setRegistryName(block, itemBlock);
    return itemBlock;
  }

  private void setRegistryName(Block block, ItemBlock itemBlock) {

    ResourceLocation registryName = block.getRegistryName();
    Preconditions.checkNotNull(registryName, "Block %s has null registry name", block);
    itemBlock.setRegistryName(registryName);
  }

}
