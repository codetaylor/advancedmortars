package com.sudoplay.mc.pwcustom.modules.mortar.reference;

public enum EnumMortarMode {

  MIXING(0),
  CRUSHING(1);

  private int id;

  EnumMortarMode(int id) {

    this.id = id;
  }

  public int getId() {

    return this.id;
  }

  public static EnumMortarMode fromId(int id) {

    switch (id) {
      case 0:
        return MIXING;
      case 1:
        return CRUSHING;
      default:
        throw new IllegalArgumentException("Unknown mortar mode id: " + id);
    }
  }

}
