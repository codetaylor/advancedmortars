package com.codetaylor.mc.advancedmortars.lib.spi;

import com.codetaylor.mc.advancedmortars.ModAdvancedMortars;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public abstract class BlockBase
    extends Block {

  public BlockBase(Material materialIn, String name) {

    this(materialIn, name, materialIn.getMaterialMapColor());
  }

  public BlockBase(Material materialIn, String name, MapColor mapColor) {

    super(materialIn, mapColor);
    this.setRegistryName(ModAdvancedMortars.MOD_ID, name);
    this.setUnlocalizedName(ModAdvancedMortars.MOD_ID + "." + name);
    this.setCreativeTab(ModAdvancedMortars.CREATIVE_TAB);
  }

}
