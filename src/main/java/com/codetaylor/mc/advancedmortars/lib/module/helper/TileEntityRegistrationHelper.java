package com.sudoplay.mc.pwcustom.lib.module.helper;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistrationHelper {

  @SafeVarargs
  public final void registerTileEntities(String modId, Class<? extends TileEntity>... tileEntityClasses) {

    for (Class<? extends TileEntity> tileEntityClass : tileEntityClasses) {
      GameRegistry.registerTileEntity(tileEntityClass, modId + ".tile." + tileEntityClass.getSimpleName());
    }
  }
}
