package com.codetaylor.mc.advancedmortars.modules.mortar.tile;

import com.codetaylor.mc.advancedmortars.lib.util.StackUtil;
import com.codetaylor.mc.advancedmortars.modules.mortar.api.MortarAPI;
import com.codetaylor.mc.advancedmortars.modules.mortar.recipe.IRecipeMortar;
import com.codetaylor.mc.advancedmortars.modules.mortar.recipe.RecipeMortar;
import com.codetaylor.mc.advancedmortars.modules.mortar.reference.EnumMortarType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class MortarDelegate
    implements IMortar {

  private ItemStackHandler itemStackHandler;
  private EnumMortarType type;
  private Runnable changeObserver;

  private IRecipeMortar recipe;

  public MortarDelegate(
      EnumMortarType type,
      Runnable changeObserver
  ) {

    this.type = type;
    this.changeObserver = changeObserver;
    this.itemStackHandler = new ItemStackHandler(8);
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

    int count = itemStack.getCount();

    for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {
      itemStack = this.itemStackHandler.insertItem(i, itemStack, true);

      if (itemStack.getCount() != count) {
        // This indicates that we can insert at least one item.
        return true;
      }
    }

    return false;
  }

  @Override
  public void insertItem(ItemStack itemStack) {
    this.insertItemInternal(itemStack);
    this.updateRecipe();
  }

  private void insertItemInternal(ItemStack itemStack) {

    ItemStack resultStack = itemStack.copy();

    for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {
      resultStack = this.itemStackHandler.insertItem(i, resultStack, false);

      if (resultStack.isEmpty()) {
        break;
      }
    }

    itemStack.setCount(resultStack.getCount());
    this.changeObserver.run();
  }

  @Override
  public ItemStack removeItem() {

    ItemStack itemStack = this.removeItemInternal();
    this.updateRecipe();
    return itemStack;
  }

  private ItemStack removeItemInternal() {

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
      // Use removeItemInternal so we don't trigger a recipe update during iteration.
      StackUtil.spawnStackOnTop(world, this.removeItemInternal(), pos);
    }
    this.updateRecipe();
  }

  @Override
  public int getOccupiedSlotCount() {

    int index = this.getFirstEmptySlotIndex();

    if (index == -1) {
      return this.itemStackHandler.getSlots();
    }

    return index;
  }

  @Override
  public boolean isEmpty() {

    return this.getOccupiedSlotCount() == 0;
  }

  @Override
  public NBTTagCompound serializeNBT() {

    return this.itemStackHandler.serializeNBT();
  }

  @Override
  public void deserializeNBT(NBTTagCompound compound) {

    this.itemStackHandler.deserializeNBT(compound);
    this.updateRecipe();
  }

  @Override
  public IRecipeMortar getRecipe() {

    return this.recipe;
  }

  private void updateRecipe() {

    List<ItemStack> itemStackList = new ArrayList<>();

    for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {
      ItemStack stackInSlot = this.itemStackHandler.getStackInSlot(i);

      if (stackInSlot.isEmpty()) {
        break;
      }

      itemStackList.add(stackInSlot);
    }

    this.recipe = MortarAPI.RECIPE_REGISTRY.findRecipe(
        this.type,
        itemStackList.toArray(new ItemStack[itemStackList.size()])
    );
  }

  @Override
  public ItemStack doCrafting() {

    RecipeMortar recipe = (RecipeMortar) this.getRecipe();

    List<Ingredient> ingredients = new ArrayList<>();
    ingredients.addAll(recipe.getIngredients());

    for (int i = 0; i < ingredients.size(); i++) {
      Ingredient ingredient = ingredients.get(i);
      ItemStack[] matchingStacks = ingredient.getMatchingStacks();

      if (matchingStacks.length > 0) {
        int requiredCount = matchingStacks[0].getCount();

        for (int j = 0; j < this.itemStackHandler.getSlots(); j++) {
          ItemStack stackInSlot = this.itemStackHandler.getStackInSlot(j);

          if (stackInSlot.isEmpty()) {
            continue;
          }

          if (ingredient.apply(stackInSlot)) {

            if (stackInSlot.getCount() >= requiredCount) {
              stackInSlot.shrink(requiredCount);
              break;

            } else {
              requiredCount -= stackInSlot.getCount();
              stackInSlot.setCount(0);
            }
          }
        }

      }
    }

    this.settleItemStacks();
    this.updateRecipe();
    this.changeObserver.run();
    return recipe.getOutput();
  }

  private void settleItemStacks() {

    List<ItemStack> stackList = new ArrayList<>();

    for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {
      ItemStack stackInSlot = this.itemStackHandler.getStackInSlot(i);

      if (!stackInSlot.isEmpty()) {
        stackList.add(stackInSlot);
        this.itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
      }
    }

    for (ItemStack itemStack : stackList) {
      // Use insertItemInternal here because we don't want to trigger a recipe update
      // here. We will trigger the recipe update after settling the itemStacks.
      this.insertItemInternal(itemStack);
    }
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
