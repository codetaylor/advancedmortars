package com.sudoplay.mc.pwcustom.lib.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class PredicateSlotItemHandler
    extends SlotItemHandler {

  private final Predicate<ItemStack> toolValidationPredicate;

  public PredicateSlotItemHandler(
      Predicate<ItemStack> toolValidationPredicate,
      IItemHandler itemHandler,
      int index,
      int xPosition,
      int yPosition
  ) {

    super(itemHandler, index, xPosition, yPosition);
    this.toolValidationPredicate = toolValidationPredicate;
  }

  @Override
  public boolean isItemValid(@Nonnull ItemStack stack) {

    return this.toolValidationPredicate.test(stack);
  }
}
