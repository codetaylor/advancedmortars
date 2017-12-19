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
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

public abstract class ModuleBase
    implements IModule {

  private final String name;
  private Logger logger;

  protected ModuleBase() {

    this.name = this.getClass().getSimpleName();
  }

  // --------------------------------------------------------------------------
  // - Name
  // --------------------------------------------------------------------------

  @Override
  public String getName() {

    return this.name;
  }

  // --------------------------------------------------------------------------
  // - Logger
  // --------------------------------------------------------------------------

  @Override
  public Logger getLogger() {

    return this.logger;
  }

  @Override
  public void setLogger(Logger logger) {

    if (this.logger != null) {
      throw new IllegalStateException("Logger already set!");
    }
    this.logger = logger;
  }

  // --------------------------------------------------------------------------
  // - Common
  // --------------------------------------------------------------------------

  // - Registration

  @Override
  public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {
    //
  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {
    //
  }

  @Override
  public void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {
    //
  }

  @Override
  public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
    //
  }

  @Override
  public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
    //
  }

  @Override
  public void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
    //
  }

  @Override
  public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
    //
  }

  @Override
  public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
    //
  }

  @Override
  public void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {
    //
  }

  @Override
  public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {
    //
  }

  @Override
  public void onRegisterTileEntitiesEvent() {
    //
  }

  // - FML State

  @Override
  public void onConstructionEvent(FMLConstructionEvent event) {
    //
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {
    //
  }

  @Override
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {
    //
  }

  @Override
  public void onInitializationEvent(FMLInitializationEvent event) {
    //
  }

  @Override
  public void onPostInitializationEvent(FMLPostInitializationEvent event) {
    //
  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  @Override
  @SideOnly(Side.CLIENT)
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {
    //
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void onClientPreInitializationEvent(FMLPreInitializationEvent event) {
    //
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void onClientInitializationEvent(FMLInitializationEvent event) {
    //
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void onClientPostInitializationEvent(FMLPostInitializationEvent event) {
    //
  }

  // --------------------------------------------------------------------------
  // - Server
  // --------------------------------------------------------------------------

  @Override
  public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {
    //
  }

  @Override
  public void onServerStartingEvent(FMLServerStartingEvent event) {
    //
  }

  @Override
  public void onServerStartedEvent(FMLServerStartedEvent event) {
    //
  }

  @Override
  public void onServerStoppingEvent(FMLServerStoppingEvent event) {
    //
  }

  @Override
  public void onServerStoppedEvent(FMLServerStoppedEvent event) {
    //
  }

}
