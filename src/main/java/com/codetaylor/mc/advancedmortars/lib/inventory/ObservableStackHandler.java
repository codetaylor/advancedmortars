package com.sudoplay.mc.pwcustom.lib.inventory;

import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class ObservableStackHandler
    extends ItemStackHandler {

  public interface IContentsChangedEventHandler {

    void onContentsChanged(ItemStackHandler stackHandler, int slotIndex);

  }

  private final List<IContentsChangedEventHandler> eventHandlerList;

  public ObservableStackHandler(int size) {

    super(size);
    this.eventHandlerList = new ArrayList<>();
  }

  public void addObserver(IContentsChangedEventHandler handler) {

    this.eventHandlerList.add(handler);
  }

  @Override
  protected void onContentsChanged(int slot) {

    for (IContentsChangedEventHandler handler : this.eventHandlerList) {
      handler.onContentsChanged(this, slot);
    }

    super.onContentsChanged(slot);
  }
}
