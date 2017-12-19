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

    @Config.Comment({"Chance to inflict hunger per click.", "Uniform distribution is ensured.", "For example, setting this to 0.25 will apply the hunger cost every four clicks.", "Set to 0 to disable."})
    @Config.RangeDouble(min = 0, max = 1)
    public double HUNGER_COST_CHANCE = 0;

    @Config.RangeInt(min = 0, max = 20)
    @Config.Comment({"Amount of hunger to charge the player per click required to complete a recipe.", "Set to 0 to disable."})
    public int HUNGER_COST_PER_CLICK = 0;

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
