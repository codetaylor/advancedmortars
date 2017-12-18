package com.codetaylor.mc.advancedmortars.modules.mortar.tile;

import com.codetaylor.mc.advancedmortars.modules.mortar.reference.EnumMortarMode;
import com.codetaylor.mc.advancedmortars.modules.mortar.reference.EnumMortarType;

public class FactoryMortarDelegate {

  private Runnable changeObserver;

  public FactoryMortarDelegate(Runnable changeObserver) {

    this.changeObserver = changeObserver;
  }

  public IMortar create(EnumMortarType type, EnumMortarMode mortarMode) {

    switch (mortarMode) {
      case MIXING:
        return new MortarDelegateMixing(type, this.changeObserver);
      case CRUSHING:
        return new MortarDelegateCrushing(type, this.changeObserver);
      default:
        throw new IllegalArgumentException("Unknown mortar mode: " + mortarMode);
    }

  }

}
