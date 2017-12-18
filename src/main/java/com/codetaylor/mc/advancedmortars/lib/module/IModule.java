package com.codetaylor.mc.advancedmortars.lib.module;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.apache.logging.log4j.Logger;

public interface IModule {

  String getName();

  Logger getLogger();

  void setLogger(Logger logger);

  // --------------------------------------------------------------------------
  // - Common
  // --------------------------------------------------------------------------

  // - FML State

  void onConstructionEvent(FMLConstructionEvent event);

  void onLoadCompleteEvent(FMLLoadCompleteEvent event);

  void onPreInitializationEvent(FMLPreInitializationEvent event);

  void onInitializationEvent(FMLInitializationEvent event);

  void onPostInitializationEvent(FMLPostInitializationEvent event);

  // - Registration

  void onRegisterBlockEvent(RegistryEvent.Register<Block> event);

  void onRegisterItemEvent(RegistryEvent.Register<Item> event);

  void onRegisterPotionEvent(RegistryEvent.Register<Potion> event);

  void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event);

  void onRegisterSoundEvent(RegistryEvent.Register<net.minecraft.util.SoundEvent> event);

  void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event);

  void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event);

  void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event);

  void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event);

  void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event);

  void onRegisterTileEntitiesEvent();

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  void onClientPreInitializationEvent(FMLPreInitializationEvent event);

  void onClientInitializationEvent(FMLInitializationEvent event);

  void onClientPostInitializationEvent(FMLPostInitializationEvent event);

  void onClientRegisterModelsEvent(ModelRegistryEvent event);

  // --------------------------------------------------------------------------
  // - Server
  // --------------------------------------------------------------------------

  void onServerAboutToStartEvent(FMLServerAboutToStartEvent event);

  void onServerStartingEvent(FMLServerStartingEvent event);

  void onServerStartedEvent(FMLServerStartedEvent event);

  void onServerStoppingEvent(FMLServerStoppingEvent event);

  void onServerStoppedEvent(FMLServerStoppedEvent event);

}
