package com.codetaylor.mc.advancedmortars.lib.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StackUtil {

  public static ItemStack decrease(ItemStack itemStack, int amount, boolean checkContainer) {

    if (itemStack.isEmpty()) {
      return ItemStack.EMPTY;
    }

    itemStack.shrink(amount);

    if (itemStack.getCount() <= 0) {

      if (checkContainer && itemStack.getItem().hasContainerItem(itemStack)) {
        return itemStack.getItem().getContainerItem(itemStack);

      } else {
        return ItemStack.EMPTY;
      }
    }

    return itemStack;
  }

  public static void spawnStackOnTop(World world, ItemStack itemStack, BlockPos pos) {

    double yOffset = 0.0;

    EntityItem entityItem = new EntityItem(
        world,
        pos.getX() + 0.5,
        pos.getY() + 0.5 + yOffset,
        pos.getZ() + 0.5,
        itemStack
    );
    entityItem.motionX = 0;
    entityItem.motionY = 0.1;
    entityItem.motionZ = 0;

    world.spawnEntity(entityItem);
  }

}
