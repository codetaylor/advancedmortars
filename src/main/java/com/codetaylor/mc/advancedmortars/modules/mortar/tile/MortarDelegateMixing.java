package com.sudoplay.mc.pwcustom.modules.mortar.tile;

import com.sudoplay.mc.pwcustom.lib.util.StackUtil;
import com.sudoplay.mc.pwcustom.modules.mortar.ModuleMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.api.MortarAPI;
import com.sudoplay.mc.pwcustom.modules.mortar.recipe.IRecipeMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.recipe.RecipeMortarMixing;
import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarMode;
import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MortarDelegateMixing
    implements IMortar {

  private ItemStackHandler itemStackHandler;
  private EnumMortarType type;
  private Runnable changeObserver;

  public MortarDelegateMixing(
      EnumMortarType type,
      Runnable changeObserver
  ) {

    this.type = type;

    this.changeObserver = changeObserver;

    this.itemStackHandler = new ItemStackHandler(8) {

      @Override
      protected int getStackLimit(int slot, @Nonnull ItemStack stack) {

        return 1;
      }
    };
  }

  @Override
  public EnumMortarMode getMortarMode() {

    return EnumMortarMode.MIXING;
  }

  @Override
  public String getMortarModeString() {

    return ModuleMortar.Lang.MORTAR_MODE_MIXING;
  }

  @Override
  public ItemStackHandler getItemStackHandler() {

    return this.itemStackHandler;
  }

  @Override
  public boolean canInsertItem(ItemStack itemStack) {

    if (itemStack.isEmpty()) {
      // Trying to add air.  :-/
      return false;
    }

    if (this.getFirstEmptySlotIndex() == -1) {
      // All slots are full.
      return false;
    }

    return true;
  }

  @Override
  public void insertItem(ItemStack itemStack) {

    if (this.canInsertItem(itemStack)) {
      int index = this.getFirstEmptySlotIndex();

      ItemStack stackToInsert = itemStack.copy();
      stackToInsert.setCount(1);
      this.itemStackHandler.setStackInSlot(index, stackToInsert);
      itemStack.shrink(1);

      this.changeObserver.run();
    }
  }

  @Override
  public ItemStack removeItem() {

    int index = this.getLastNonEmptySlotIndex();

    if (index == -1) {
      return ItemStack.EMPTY;
    }

    ItemStack toReturn = this.itemStackHandler.getStackInSlot(index);
    this.itemStackHandler.setStackInSlot(index, ItemStack.EMPTY);

    this.changeObserver.run();

    return toReturn;
  }

  @Override
  public void dropAllItems(World world, BlockPos pos) {

    while (!this.isEmpty()) {
      StackUtil.spawnStackOnTop(world, this.removeItem(), pos);
    }
  }

  @Override
  public int getItemCount() {

    int index = this.getFirstEmptySlotIndex();

    if (index == -1) {
      return this.itemStackHandler.getSlots();
    }

    return index;
  }

  @Override
  public boolean isEmpty() {

    return this.getItemCount() == 0;
  }

  @Override
  public NBTTagCompound serializeNBT() {

    return this.itemStackHandler.serializeNBT();
  }

  @Override
  public void deserializeNBT(NBTTagCompound compound) {

    this.itemStackHandler.deserializeNBT(compound);
  }

  @Override
  public IRecipeMortar getRecipe() {

    List<ItemStack> itemStackList = new ArrayList<>();

    for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {
      ItemStack stackInSlot = this.itemStackHandler.getStackInSlot(i);

      if (stackInSlot.isEmpty()) {
        break;
      }

      itemStackList.add(stackInSlot);
    }

    return MortarAPI.RECIPE_REGISTRY.findMixingRecipe(
        this.type,
        itemStackList.toArray(new ItemStack[itemStackList.size()])
    );
  }

  @Override
  public ItemStack doCrafting() {

    RecipeMortarMixing recipe = (RecipeMortarMixing) this.getRecipe();

    for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {
      this.itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
    }

    this.changeObserver.run();
    return recipe.getOutput();
  }

  private int getFirstEmptySlotIndex() {

    for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {

      if (this.itemStackHandler.getStackInSlot(i).isEmpty()) {
        return i;
      }
    }

    return -1;
  }

  private int getLastNonEmptySlotIndex() {

    for (int i = this.itemStackHandler.getSlots() - 1; i >= 0; i--) {

      if (!this.itemStackHandler.getStackInSlot(i).isEmpty()) {
        return i;
      }
    }

    return -1;
  }
}
