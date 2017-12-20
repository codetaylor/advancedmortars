package com.codetaylor.mc.advancedmortars.modules.mortar.tile;

import com.codetaylor.mc.advancedmortars.modules.mortar.recipe.IRecipeMortar;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public interface IMortar {

  boolean canInsertItem(ItemStack itemStack);

  void insertItem(ItemStack itemStack);

  ItemStack removeItem();

  int getOccupiedSlotCount();

  boolean isEmpty();

  NBTTagCompound serializeNBT();

  void deserializeNBT(NBTTagCompound compound);

  ItemStackHandler getItemStackHandler();

  IRecipeMortar getRecipe();

  ItemStack[] doCrafting();

  void dropAllItems(World world, BlockPos pos);

}
