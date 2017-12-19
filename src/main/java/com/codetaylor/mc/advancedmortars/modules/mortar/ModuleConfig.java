package com.codetaylor.mc.advancedmortars.modules.mortar;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ModuleMortar.MOD_ID, name = ModuleMortar.MOD_ID + ".module.Mortar")
public class ModuleConfig {

  public static Recipes RECIPES = new Recipes();

  public static class Recipes {

    @Config.RequiresMcRestart
    @Config.Comment({"Set to false to disable all default recipes."})
    public boolean ENABLE_DEFAULT_RECIPES = true;

  }

  public static Durability DURABILITY = new Durability();

  public static class Durability {

    @Config.RangeInt(min = 0)
    @Config.Comment({"Set to 0 for infinite uses."})
    public int WOOD = 16;

    @Config.RangeInt(min = 0)
    @Config.Comment({"Set to 0 for infinite uses."})
    public int STONE = 64;

    @Config.RangeInt(min = 0)
    @Config.Comment({"Set to 0 for infinite uses."})
    public int IRON = 256;

    @Config.RangeInt(min = 0)
    @Config.Comment({"Set to 0 for infinite uses."})
    public int DIAMOND = 1024;

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
