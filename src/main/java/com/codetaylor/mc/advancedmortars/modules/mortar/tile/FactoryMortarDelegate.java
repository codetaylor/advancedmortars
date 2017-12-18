package com.sudoplay.mc.pwcustom.modules.mortar.tile;

import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarMode;
import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarType;

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
