package com.codetaylor.mc.advancedmortars.modules.mortar.reference;

import com.codetaylor.mc.advancedmortars.lib.spi.IVariant;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EnumMortarType
    implements IVariant {

  WOOD(0, "wood", Material.WOOD, Material.WOOD.getMaterialMapColor(), 2, 5, "axe", 0, SoundType.WOOD),
  STONE(1, "stone", Material.ROCK, Material.ROCK.getMaterialMapColor(), 1.5f, 10, "pickaxe", 1, SoundType.STONE),
  IRON(2, "iron", Material.IRON, Material.IRON.getMaterialMapColor(), 5, 10, "pickaxe", 2, SoundType.METAL),
  DIAMOND(3, "diamond", Material.IRON, MapColor.DIAMOND, 5, 10, "pickaxe", 3, SoundType.METAL),
  GOLD(4, "gold", Material.IRON, MapColor.GOLD, 2, 5, "pickaxe", 2, SoundType.METAL),
  OBSIDIAN(5, "obsidian", Material.IRON, MapColor.GOLD, 5, 10, "pickaxe", 3, SoundType.METAL),
  EMERALD(6, "emerald", Material.IRON, MapColor.GOLD, 5, 10, "pickaxe", 3, SoundType.METAL);

  private static final EnumMortarType[] META_LOOKUP = Stream.of(EnumMortarType.values())
      .sorted(Comparator.comparing(EnumMortarType::getMeta))
      .toArray(EnumMortarType[]::new);

  public static final String[] NAMES = Stream.of(EnumMortarType.values())
      .sorted(Comparator.comparing(EnumMortarType::getMeta))
      .map(enumMortarType -> enumMortarType.name)
      .toArray(String[]::new);

  public static final Map<String, EnumMortarType> NAME_MAP = Stream.of(EnumMortarType.values())
      .collect(Collectors.toMap(EnumMortarType::getName, Function.identity()));

  private final int meta;
  private final String name;
  private final Material material;
  private final MapColor mapColor;
  private final float hardness;
  private final float resistance;
  private final String harvestTool;
  private final int harvestLevel;
  private final SoundType soundType;

  EnumMortarType(
      int meta,
      String name,
      Material material,
      MapColor mapColor,
      float hardness,
      float resistance,
      String harvestTool,
      int harvestLevel,
      SoundType soundType
  ) {

    this.meta = meta;
    this.name = name;
    this.material = material;
    this.mapColor = mapColor;
    this.hardness = hardness;
    this.resistance = resistance;
    this.harvestTool = harvestTool;
    this.harvestLevel = harvestLevel;
    this.soundType = soundType;
  }

  @Override
  public int getMeta() {

    return this.meta;
  }

  @Override
  public String getName() {

    return this.name;
  }

  public Material getMaterial() {

    return material;
  }

  public MapColor getMapColor() {

    return mapColor;
  }

  public float getHardness() {

    return hardness;
  }

  public float getResistance() {

    return resistance;
  }

  public String getHarvestTool() {

    return harvestTool;
  }

  public int getHarvestLevel() {

    return harvestLevel;
  }

  public SoundType getSoundType() {

    return soundType;
  }

  public static EnumMortarType fromMeta(int meta) {

    if (meta < 0 || meta >= META_LOOKUP.length) {
      meta = 0;
    }

    return META_LOOKUP[meta];
  }

  public static EnumMortarType fromName(String name) {

    return NAME_MAP.get(name);
  }

}
