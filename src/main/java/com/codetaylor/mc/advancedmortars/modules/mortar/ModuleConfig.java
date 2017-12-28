package com.codetaylor.mc.advancedmortars.modules.mortar;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ModuleMortar.MOD_ID, name = ModuleMortar.MOD_ID + ".module.Mortar")
public class ModuleConfig {

  @Config.Comment({"Set to false to make the mortar drop its contents when moved."})
  public static boolean KEEP_CONTENTS = true;

  public static Client CLIENT = new Client();

  public static class Client {

    @Config.Comment({"Set to false to hide the mortar interaction hints in the UI overlay."})
    public boolean DISPLAY_INTERACTION_HINTS = true;

    @Config.Comment({"Set to false to hide the mortar durability in the UI overlay."})
    public boolean DISPLAY_MORTAR_DURABILITY = true;
  }

  public static Recipes RECIPES = new Recipes();

  public static class Recipes {

    @Config.Comment({"If set to true, the mortar will require an empty hand to use."})
    public boolean REQUIRE_EMPTY_HAND_TO_USE = false;

    @Config.RangeInt(min = 0, max = 20)
    @Config.Comment({"Minimum amount of hunger the player needs to operate a mortar.", "Set to 0 to disable."})
    public int MINIMUM_HUNGER_TO_USE = 0;

    @Config.RangeDouble(min = 0, max = 40)
    @Config.Comment({"Amount of exhaustion to charge the player per click required to complete a recipe.", "Set to 0 to disable."})
    public double EXHAUSTION_COST_PER_CLICK = 0;

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
