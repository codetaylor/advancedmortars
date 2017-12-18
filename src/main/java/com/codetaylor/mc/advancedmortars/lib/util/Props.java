package com.sudoplay.mc.pwcustom.lib.util;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;

public class Props {

  public static final PropertyEnum<EnumFacing.Axis> PORTAL_AXIS = PropertyEnum.create(
      "axis",
      EnumFacing.Axis.class,
      EnumFacing.Axis.X,
      EnumFacing.Axis.Z
  );

}
