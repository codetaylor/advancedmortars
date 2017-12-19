package com.codetaylor.mc.advancedmortars.lib.module.helper;

import com.google.common.base.Preconditions;
import com.codetaylor.mc.advancedmortars.ModAdvancedMortars;
import com.codetaylor.mc.advancedmortars.lib.spi.IBlockVariant;
import com.codetaylor.mc.advancedmortars.lib.spi.IVariant;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import java.util.function.ToIntFunction;

public class ModelRegistrationHelper {

  /**
   * A {@link StateMapperBase} used to create property strings.
   */
  public static final StateMapperBase PROPERTY_STRING_MAPPER = new StateMapperBase() {

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {

      return new ModelResourceLocation("minecraft:air");
    }
  };

  // --------------------------------------------------------------------------
  // - ItemBlock
  // --------------------------------------------------------------------------

  public static void registerBlockItemModels(Block... blocks) {

    for (Block block : blocks) {

      if (block instanceof IBlockVariant) {
        //noinspection unchecked
        ModelRegistrationHelper.registerVariantBlockItemModels(
            block.getDefaultState(),
            ((IBlockVariant) block).getVariant()
        );

      } else {
        ModelRegistrationHelper.registerBlockItemModel(block.getDefaultState());
      }
    }
  }

  public static void registerBlockItemModel(IBlockState blockState) {

    Block block = blockState.getBlock();
    Item item = Item.getItemFromBlock(block);

    ModelRegistrationHelper.registerItemModel(
        item,
        new ModelResourceLocation(
            Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block),
            PROPERTY_STRING_MAPPER.getPropertyString(blockState.getProperties())
        )
    );
  }

  public static <T extends IVariant & Comparable<T>> void registerVariantBlockItemModels(
      IBlockState baseState,
      IProperty<T> property
  ) {

    ModelRegistrationHelper.registerVariantBlockItemModels(baseState, property, IVariant::getMeta);
  }

  public static <T extends IVariant & Comparable<T>> void registerVariantBlockItemModelsSeparately(
      IBlockState state,
      IProperty<T> property
  ) {

    ModelRegistrationHelper.registerVariantBlockItemModelsSeparately(state, property, "");
  }

  public static <T extends IVariant & Comparable<T>> void registerVariantBlockItemModelsSeparately(
      IBlockState state,
      IProperty<T> property,
      String suffix
  ) {

    for (T value : property.getAllowedValues()) {

      Block block = state.getBlock();
      Item item = Item.getItemFromBlock(block);

      String name;

      if (block instanceof IBlockVariant) {
        name = ((IBlockVariant) block).getName(new ItemStack(item, 1, value.getMeta())) + suffix;

      } else {
        name = value.getName() + suffix;
      }

      if (item != Items.AIR) {
        ModelRegistrationHelper.registerItemModel(
            item,
            value.getMeta(),
            new ModelResourceLocation(ModAdvancedMortars.MOD_ID + ":" + name, "inventory")
        );
      }

    }
  }

  public static <T extends Comparable<T>> void registerVariantBlockItemModels(
      IBlockState baseState,
      IProperty<T> property,
      ToIntFunction<T> getMeta
  ) {

    property.getAllowedValues()
        .forEach(value -> ModelRegistrationHelper.registerBlockItemModelForMeta(
            baseState.withProperty(property, value),
            getMeta.applyAsInt(value)
        ));
  }

  public static void registerBlockItemModelForMeta(final IBlockState state, final int metadata) {

    Item item = Item.getItemFromBlock(state.getBlock());

    if (item != Items.AIR) {
      ModelRegistrationHelper.registerItemModel(
          item,
          metadata,
          PROPERTY_STRING_MAPPER.getPropertyString(state.getProperties())
      );
    }
  }

  // --------------------------------------------------------------------------
  // - Item
  // --------------------------------------------------------------------------

  public static void registerItemModels(Item... items) {

    for (Item item : items) {
      ModelRegistrationHelper.registerItemModel(item, item.getRegistryName().toString());
    }
  }

  public static void registerItemModel(Item item, String modelLocation) {

    ModelResourceLocation resourceLocation = new ModelResourceLocation(modelLocation, "inventory");
    ModelRegistrationHelper.registerItemModel(item, 0, resourceLocation);
  }

  public static void registerItemModel(Item item, ModelResourceLocation resourceLocation) {

    ModelRegistrationHelper.registerItemModel(item, 0, resourceLocation);
  }

  public static void registerItemModel(final Item item, final int metadata, final String variant) {

    ModelRegistrationHelper.registerItemModel(
        item,
        metadata,
        new ModelResourceLocation(item.getRegistryName(), variant)
    );
  }

  public static void registerItemModel(Item item, int meta, ModelResourceLocation resourceLocation) {

    ModelLoader.setCustomModelResourceLocation(item, meta, resourceLocation);
  }

  public static <T extends IVariant> void registerVariantItemModels(Item item, String variantName, T[] values) {

    for (T value : values) {
      ModelRegistrationHelper.registerItemModel(item, value.getMeta(), variantName + "=" + value.getName());
    }
  }

}
