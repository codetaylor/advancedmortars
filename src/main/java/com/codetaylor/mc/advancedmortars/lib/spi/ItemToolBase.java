package com.sudoplay.mc.pwcustom.lib.spi;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public abstract class ItemToolBase
    extends ItemTool {

  public ItemToolBase(
      String name,
      float attackDamageIn,
      float attackSpeedIn,
      Item.ToolMaterial materialIn,
      Set<Block> effectiveBlocksIn
  ) {

    super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
    this.setRegistryName(new ResourceLocation(ModPWCustom.MOD_ID, name));
    this.setUnlocalizedName(ModPWCustom.MOD_ID + "." + name);
    this.setCreativeTab(ModPWCustom.CREATIVE_TAB);
  }

  public ItemToolBase(
      String name,
      Item.ToolMaterial materialIn,
      Set<Block> effectiveBlocksIn
  ) {

    super(materialIn, effectiveBlocksIn);
    this.setRegistryName(new ResourceLocation(ModPWCustom.MOD_ID, name));
    this.setUnlocalizedName(ModPWCustom.MOD_ID + "." + name);
    this.setCreativeTab(ModPWCustom.CREATIVE_TAB);
  }
}
