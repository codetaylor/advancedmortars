package com.sudoplay.mc.pwcustom.lib.spi;

import com.sudoplay.mc.pwcustom.ModPWCustom;
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
    this.setRegistryName(ModPWCustom.MOD_ID, name);
    this.setUnlocalizedName(ModPWCustom.MOD_ID + "." + name);
    this.setCreativeTab(ModPWCustom.CREATIVE_TAB);
  }

}
