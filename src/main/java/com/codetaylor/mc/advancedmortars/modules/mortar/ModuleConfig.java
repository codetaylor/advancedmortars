package com.codetaylor.mc.advancedmortars.modules.mortar;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleConfig {

  @Config(modid = ModuleMortar.MOD_ID, category = "mortar.durability", name = ModuleMortar.MOD_ID + ".module.Mortar")
  public static class Durability {

    @Config.Comment({"Set to 0 for infinite uses."})
    @Config.RangeInt(min = 0)
    public static int WOOD = 16;

    @Config.Comment({"Set to 0 for infinite uses."})
    @Config.RangeInt(min = 0)
    public static int STONE = 64;

    @Config.Comment({"Set to 0 for infinite uses."})
    @Config.RangeInt(min = 0)
    public static int IRON = 256;

    @Config.Comment({"Set to 0 for infinite uses."})
    @Config.RangeInt(min = 0)
    public static int DIAMOND = 1024;

  }

  @Mod.EventBusSubscriber(modid = ModuleMortar.MOD_ID)
  private static class EventHandler {

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {

      if (event.getModID().equals(ModuleMortar.MOD_ID)) {
        ConfigManager.sync(ModuleMortar.MOD_ID, Config.Type.INSTANCE);
      }
    }
  }
}
