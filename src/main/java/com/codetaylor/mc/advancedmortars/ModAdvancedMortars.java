package com.codetaylor.mc.advancedmortars;

import com.codetaylor.mc.advancedmortars.lib.module.IModuleLoggerFactory;
import com.codetaylor.mc.advancedmortars.lib.module.ModuleEventRouter;
import com.codetaylor.mc.advancedmortars.lib.module.ModuleRegistry;
import com.codetaylor.mc.advancedmortars.modules.mortar.ModuleMortar;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;

@Mod(
    modid = ModAdvancedMortars.MOD_ID,
    version = ModAdvancedMortars.VERSION,
    name = ModAdvancedMortars.NAME,
    dependencies = ModAdvancedMortars.DEPENDENCIES
)
public class ModAdvancedMortars {

  public static final String MOD_ID = Reference.MOD_ID;
  public static final String VERSION = Reference.VERSION;
  public static final String NAME = Reference.NAME;
  public static final String DEPENDENCIES = Reference.DEPENDENCIES;

  public static final boolean IS_DEV = Reference.IS_DEV;

  @SuppressWarnings("unused")
  @Mod.Instance
  public static ModAdvancedMortars INSTANCE;

  public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_ID) {

    @Override
    public ItemStack getTabIconItem() {

      return new ItemStack(ModuleMortar.Blocks.MORTAR, 1, 0);
    }
  };

  private ModuleEventRouter moduleEventRouter;

  public ModAdvancedMortars() {

    IModuleLoggerFactory moduleLoggerFactory = module -> LogManager.getLogger(MOD_ID + "." + module.getName());
    ModuleRegistry moduleRegistry = new ModuleRegistry(moduleLoggerFactory);
    this.moduleEventRouter = new ModuleEventRouter(moduleRegistry);
    MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);

    moduleRegistry.registerModules(
        new ModuleMortar()
    );

  }

  @Mod.EventHandler
  public void onConstructionEvent(FMLConstructionEvent event) {

    this.moduleEventRouter.onConstructionEvent(event);
  }

  @Mod.EventHandler
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    this.moduleEventRouter.onLoadCompleteEvent(event);
  }

  @Mod.EventHandler
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    this.moduleEventRouter.onPreInitializationEvent(event);
  }

  @Mod.EventHandler
  public void onInitializationEvent(FMLInitializationEvent event) {

    this.moduleEventRouter.onInitializationEvent(event);
  }

  @Mod.EventHandler
  public void onPostInitializationEvent(FMLPostInitializationEvent event) {

    this.moduleEventRouter.onPostInitializationEvent(event);
  }

  @Mod.EventHandler
  public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {

    this.moduleEventRouter.onServerAboutToStartEvent(event);
  }

  @Mod.EventHandler
  public void onServerStartingEvent(FMLServerStartingEvent event) {

    this.moduleEventRouter.onServerStartingEvent(event);
  }

  @Mod.EventHandler
  public void onServerStartedEvent(FMLServerStartedEvent event) {

    this.moduleEventRouter.onServerStartedEvent(event);
  }

  @Mod.EventHandler
  public void onServerStoppingEvent(FMLServerStoppingEvent event) {

    this.moduleEventRouter.onServerStoppingEvent(event);
  }

  @Mod.EventHandler
  public void onServerStoppedEvent(FMLServerStoppedEvent event) {

    this.moduleEventRouter.onServerStoppedEvent(event);
  }
}
