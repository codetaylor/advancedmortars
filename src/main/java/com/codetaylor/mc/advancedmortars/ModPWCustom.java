package com.sudoplay.mc.pwcustom;

import com.sudoplay.mc.pwcustom.lib.gui.GuiHandler;
import com.sudoplay.mc.pwcustom.lib.module.IModuleLoggerFactory;
import com.sudoplay.mc.pwcustom.lib.module.ModuleEventRouter;
import com.sudoplay.mc.pwcustom.lib.module.ModuleRegistry;
import com.sudoplay.mc.pwcustom.modules.blocks.ModuleBlocks;
import com.sudoplay.mc.pwcustom.modules.casts.ModuleCasts;
import com.sudoplay.mc.pwcustom.modules.craftingparts.ModuleCraftingParts;
import com.sudoplay.mc.pwcustom.modules.enchanting.ModuleEnchanting;
import com.sudoplay.mc.pwcustom.modules.mortar.ModuleMortar;
import com.sudoplay.mc.pwcustom.modules.portals.ModulePortals;
import com.sudoplay.mc.pwcustom.modules.sawing.ModuleSawing;
import com.sudoplay.mc.pwcustom.modules.toolparts.ModuleToolParts;
import com.sudoplay.mc.pwcustom.modules.workbench.ModuleWorkbench;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;

@Mod(
    modid = ModPWCustom.MOD_ID,
    version = ModPWCustom.VERSION,
    name = ModPWCustom.NAME,
    dependencies = ModPWCustom.DEPENDENCIES
)
public class ModPWCustom {

  public static final String MOD_ID = Reference.MOD_ID;
  public static final String VERSION = Reference.VERSION;
  public static final String NAME = Reference.NAME;
  public static final String DEPENDENCIES = Reference.DEPENDENCIES;

  public static final boolean IS_DEV = Reference.IS_DEV;

  @SuppressWarnings("unused")
  @Mod.Instance
  public static ModPWCustom INSTANCE;

  public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_ID) {

    @Override
    public ItemStack getTabIconItem() {

      return new ItemStack(ModulePortals.Items.PORTAL_WAND, 1, 0);
    }
  };

  private ModuleEventRouter moduleEventRouter;

  public ModPWCustom() {

    IModuleLoggerFactory moduleLoggerFactory = module -> LogManager.getLogger(MOD_ID + "." + module.getName());
    ModuleRegistry moduleRegistry = new ModuleRegistry(moduleLoggerFactory);
    this.moduleEventRouter = new ModuleEventRouter(moduleRegistry);
    MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);

    moduleRegistry.registerModules(
        new ModuleWorkbench(),
        new ModulePortals(),
        new ModuleSawing(),
        new ModuleToolParts(),
        new ModuleCasts(),
        new ModuleEnchanting(),
        new ModuleBlocks(),
        new ModuleCraftingParts(),
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

    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

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
