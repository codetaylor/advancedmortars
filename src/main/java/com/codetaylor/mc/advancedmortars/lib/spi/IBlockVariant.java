package com.sudoplay.mc.pwcustom.lib.spi;

import net.minecraft.block.properties.IProperty;
import net.minecraft.item.ItemStack;

public interface IBlockVariant<T extends IVariant & Comparable<T>> {

  String getName(ItemStack stack);

  IProperty<T> getVariant();

}
