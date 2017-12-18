package com.codetaylor.mc.advancedmortars.lib.module;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModuleEventRouter {

  private final ModuleRegistry registry;

  public ModuleEventRouter(ModuleRegistry registry) {

    this.registry = registry;
  }

  // --------------------------------------------------------------------------
  // - Common
  // --------------------------------------------------------------------------

  // - Registration

  @SubscribeEvent
  public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {

    this.fireEvent(module -> {
      module.onRegisterBlockEvent(event);
    });

    this.onRegisterTileEntitiesEvent();
  }

  @SubscribeEvent
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    this.fireEvent(module -> module.onRegisterItemEvent(event));
  }

  @SubscribeEvent
  public void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {

    this.fireEvent(module -> module.onRegisterPotionEvent(event));
  }

  @SubscribeEvent
  public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {

    this.fireEvent(module -> module.onRegisterBiomeEvent(event));
  }

  @SubscribeEvent
  public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {

    this.fireEvent(module -> module.onRegisterSoundEvent(event));
  }

  @SubscribeEvent
  public void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {

    this.fireEvent(module -> module.onRegisterPotionTypeEvent(event));
  }

  @SubscribeEvent
  public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {

    this.fireEvent(module -> module.onRegisterEnchantmentEvent(event));
  }

  @SubscribeEvent
  public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {

    this.fireEvent(module -> module.onRegisterVillagerProfessionEvent(event));
  }

  @SubscribeEvent
  public void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {

    this.fireEvent(module -> module.onRegisterEntityEvent(event));
  }

  @SubscribeEvent
  public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {

    this.fireEvent(module -> module.onRegisterRecipesEvent(event));
  }

  public void onRegisterTileEntitiesEvent() {

    this.fireEvent(IModule::onRegisterTileEntitiesEvent);
  }

  // - FML State

  public void onConstructionEvent(FMLConstructionEvent event) {

    this.fireEvent(module -> module.onConstructionEvent(event));
  }

  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    this.fireEvent(module -> module.onLoadCompleteEvent(event));
  }

  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    this.fireEvent(module -> module.onPreInitializationEvent(event));

    if (event.getSide() == Side.CLIENT) {
      this.onClientPreInitializationEvent(event);
    }
  }

  public void onInitializationEvent(FMLInitializationEvent event) {

    this.fireEvent(module -> module.onInitializationEvent(event));

    if (event.getSide() == Side.CLIENT) {
      this.onClientInitializationEvent(event);
    }
  }

  public void onPostInitializationEvent(FMLPostInitializationEvent event) {

    this.fireEvent(module -> module.onPostInitializationEvent(event));

    if (event.getSide() == Side.CLIENT) {
      this.onClientPostInitializationEvent(event);
    }
  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    this.fireEvent(module -> module.onClientRegisterModelsEvent(event));
  }

  @SideOnly(Side.CLIENT)
  public void onClientPreInitializationEvent(FMLPreInitializationEvent event) {

    this.fireEvent(module -> module.onClientPreInitializationEvent(event));
  }

  @SideOnly(Side.CLIENT)
  public void onClientInitializationEvent(FMLInitializationEvent event) {

    this.fireEvent(module -> module.onClientInitializationEvent(event));
  }

  @SideOnly(Side.CLIENT)
  public void onClientPostInitializationEvent(FMLPostInitializationEvent event) {

    this.fireEvent(module -> module.onClientPostInitializationEvent(event));
  }

  // --------------------------------------------------------------------------
  // - Server
  // --------------------------------------------------------------------------

  public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {

    this.fireEvent(module -> module.onServerAboutToStartEvent(event));
  }

  public void onServerStartingEvent(FMLServerStartingEvent event) {

    this.fireEvent(module -> module.onServerStartingEvent(event));
  }

  public void onServerStartedEvent(FMLServerStartedEvent event) {

    this.fireEvent(module -> module.onServerStartedEvent(event));
  }

  public void onServerStoppingEvent(FMLServerStoppingEvent event) {

    this.fireEvent(module -> module.onServerStoppingEvent(event));
  }

  public void onServerStoppedEvent(FMLServerStoppedEvent event) {

    this.fireEvent(module -> module.onServerStoppedEvent(event));
  }

  // --------------------------------------------------------------------------
  // - Internal
  // --------------------------------------------------------------------------

  private void fireEvent(Consumer<IModule> moduleConsumer) {

    List<IModule> modules = this.registry.getModules(new ArrayList<>());

    for (IModule module : modules) {
      moduleConsumer.accept(module);
    }
  }

}
