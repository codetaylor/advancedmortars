package com.sudoplay.mc.pwcustom.lib;

import com.sudoplay.mc.pwcustom.material.EnumMaterial;
import com.sudoplay.mc.pwcustom.lib.spi.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemMaterialPart
    extends ItemBase {

  private final EnumMaterial[] materials;

  public ItemMaterialPart(String name, EnumMaterial[] materials) {

    super(name);
    this.materials = materials;
    this.setHasSubtypes(true);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    return super.getUnlocalizedName(stack) + "." + this.materials[stack.getMetadata()].getName();
  }

  @Override
  public void getSubItems(
      CreativeTabs tab, NonNullList<ItemStack> items
  ) {

    if (this.isInCreativeTab(tab)) {

      for (int i = 0; i < this.materials.length; i++) {
        items.add(new ItemStack(this, 1, i));
      }
    }
  }
}
