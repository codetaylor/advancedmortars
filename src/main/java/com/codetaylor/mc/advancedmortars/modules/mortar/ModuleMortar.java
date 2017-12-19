package com.codetaylor.mc.advancedmortars.modules.mortar;

import com.codetaylor.mc.advancedmortars.ModAdvancedMortars;
import com.codetaylor.mc.advancedmortars.lib.module.ModuleBase;
import com.codetaylor.mc.advancedmortars.lib.module.helper.ModelRegistrationHelper;
import com.codetaylor.mc.advancedmortars.lib.module.helper.TileEntityRegistrationHelper;
import com.codetaylor.mc.advancedmortars.modules.mortar.api.MortarAPI;
import com.codetaylor.mc.advancedmortars.modules.mortar.block.BlockMortar;
import com.codetaylor.mc.advancedmortars.modules.mortar.integration.crafttweaker.PluginCraftTweaker;
import com.codetaylor.mc.advancedmortars.modules.mortar.item.ItemBlockMortar;
import com.codetaylor.mc.advancedmortars.modules.mortar.reference.EnumMortarType;
import com.codetaylor.mc.advancedmortars.modules.mortar.tile.*;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleMortar
    extends ModuleBase {

  public static final String MOD_ID = ModAdvancedMortars.MOD_ID;
  public static final boolean IS_DEV = ModAdvancedMortars.IS_DEV;

  public static class Lang {

    public static final String TOOLTIP_DURABILITY = "tooltip." + MOD_ID + ".durability";
    public static final String TOOLTIP_DURABILITY_UNBREAKABLE = "tooltip." + MOD_ID + ".durability.unbreakable";
    public static final String TOOLTIP_EXTENDED = "tooltip." + MOD_ID + ".extended";
    public static final String TOOLTIP_EXTENDED_PICKUP = "tooltip." + MOD_ID + ".extended.pickup";
    public static final String TOOLTIP_EXTENDED_SWITCHMODES_LINE1 = "tooltip." + MOD_ID + ".extended.switchmodes.line1";
    public static final String TOOLTIP_EXTENDED_SWITCHMODES_LINE2 = "tooltip." + MOD_ID + ".extended.switchmodes.line2";
  }

  public static class Blocks {

    @GameRegistry.ObjectHolder(MOD_ID + ":" + BlockMortar.NAME)
    public static final BlockMortar MORTAR;

    static {
      MORTAR = null;
    }
  }

  @Override
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    if (Loader.isModLoaded("crafttweaker")) {
      PluginCraftTweaker.init();
    }

    if (ModuleConfig.Recipes.ENABLE_DEFAULT_RECIPES) {

      for (EnumMortarType type : EnumMortarType.values()) {

        MortarAPI.RECIPE_REGISTRY.addCrushingRecipe(
            type,
            new ItemStack(Items.DYE, 4, 15),
            4,
            Ingredient.fromStacks(new ItemStack(Items.BONE, 1, 0))
        );

        MortarAPI.RECIPE_REGISTRY.addMixingRecipe(
            type,
            new ItemStack(Items.DYE, 4, 9),
            4,
            new Ingredient[]{
                Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 1)),
                Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 15))
            }
        );
      }

    }
  }

  @Override
  public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {

    event.getRegistry().registerAll(
        new BlockMortar()
    );
  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    ItemBlock itemBlock = new ItemBlockMortar(Blocks.MORTAR, Blocks.MORTAR::getName);

    event.getRegistry().registerAll(
        itemBlock
    );
  }

  @Override
  public void onRegisterTileEntitiesEvent() {

    TileEntityRegistrationHelper.registerTileEntities(
        MOD_ID,
        TileEntityMortarWood.class,
        TileEntityMortarStone.class,
        TileEntityMortarIron.class,
        TileEntityMortarDiamond.class
    );
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    ModelRegistrationHelper.registerVariantBlockItemModelsSeparately(
        Blocks.MORTAR.getDefaultState(),
        BlockMortar.VARIANT
    );

    ClientRegistry.bindTileEntitySpecialRenderer(
        TileEntityMortarWood.class,
        new TESRMortar()
    );
    ClientRegistry.bindTileEntitySpecialRenderer(
        TileEntityMortarStone.class,
        new TESRMortar()
    );
    ClientRegistry.bindTileEntitySpecialRenderer(
        TileEntityMortarIron.class,
        new TESRMortar()
    );
    ClientRegistry.bindTileEntitySpecialRenderer(
        TileEntityMortarDiamond.class,
        new TESRMortar()
    );
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    if (Loader.isModLoaded("crafttweaker")) {
      PluginCraftTweaker.apply();
    }
  }

}
