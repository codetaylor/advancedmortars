package com.codetaylor.mc.advancedmortars.modules.mortar.block;

import com.codetaylor.mc.advancedmortars.lib.spi.BlockBase;
import com.codetaylor.mc.advancedmortars.lib.spi.IBlockVariant;
import com.codetaylor.mc.advancedmortars.lib.util.StackUtil;
import com.codetaylor.mc.advancedmortars.modules.mortar.ModuleConfig;
import com.codetaylor.mc.advancedmortars.modules.mortar.reference.EnumMortarType;
import com.codetaylor.mc.advancedmortars.modules.mortar.tile.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockMortar
    extends BlockBase
    implements IBlockVariant<EnumMortarType> {

  public static final String NAME = "mortar";

  public static final IProperty<EnumMortarType> VARIANT = PropertyEnum.create("variant", EnumMortarType.class);

  private static final AxisAlignedBB AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.25, 0.75);

  public BlockMortar() {

    super(Material.WOOD, NAME);
    this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumMortarType.WOOD));
  }

  @Override
  public Material getMaterial(IBlockState state) {

    return state.getValue(VARIANT).getMaterial();
  }

  @Override
  public MapColor getMapColor(
      IBlockState state, IBlockAccess worldIn, BlockPos pos
  ) {

    return state.getValue(VARIANT).getMapColor();
  }

  @Override
  public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {

    return blockState.getValue(VARIANT).getHardness();
  }

  @Override
  public float getExplosionResistance(
      World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion
  ) {

    return world.getBlockState(pos).getValue(VARIANT).getResistance();
  }

  @Nullable
  @Override
  public String getHarvestTool(IBlockState state) {

    return state.getValue(VARIANT).getHarvestTool();
  }

  @Override
  public int getHarvestLevel(IBlockState state) {

    return state.getValue(VARIANT).getHarvestLevel();
  }

  @Override
  public SoundType getSoundType(
      IBlockState state, World world, BlockPos pos, @Nullable Entity entity
  ) {

    return state.getValue(VARIANT).getSoundType();
  }

  @Override
  public boolean removedByPlayer(
      IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest
  ) {

    // Delay the destruction of the TE until after BlockMortar#getDrops is called. We need
    // access to the TE while creating the dropped item in order to serialize it.
    return willHarvest || super.removedByPlayer(state, world, pos, player, false);
  }

  @Override
  public void harvestBlock(
      World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack
  ) {

    super.harvestBlock(worldIn, player, pos, state, te, stack);
    worldIn.setBlockToAir(pos);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {

    return null;
  }

  @Override
  public void getDrops(
      NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune
  ) {

    // TODO: Fix
    // This method should simply return a list of drops like the javadoc says.
    // Calling the destroy method on the TE below will spawn items directly in
    // the world. Calling this method should not spawn anything in the world.

    drops.clear();

    TileEntity tileEntity = world.getTileEntity(pos);

    if (tileEntity != null
        && tileEntity instanceof TileEntityMortarBase) {

      boolean dropAllItems = !ModuleConfig.KEEP_CONTENTS;
      drops.add(((TileEntityMortarBase) tileEntity).destroy(dropAllItems, false, null));
    }
  }

  @Override
  public boolean canPlaceBlockAt(World world, BlockPos pos) {

    if (!super.canPlaceBlockAt(world, pos)) {
      return false;
    }

    return world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP);
  }

  @Override
  public void neighborChanged(
      IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos
  ) {

    if (!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP)) {
      TileEntity tileEntity = world.getTileEntity(pos);

      if (tileEntity instanceof TileEntityMortarBase) {
        ItemStack itemStack = ((TileEntityMortarBase) tileEntity).destroy(true, false, SoundEvents.ENTITY_ITEM_PICKUP);
        StackUtil.spawnStackOnTop(world, itemStack, pos);
      }
    }
  }

  @Override
  public boolean onBlockActivated(
      World world,
      BlockPos pos,
      IBlockState state,
      EntityPlayer player,
      EnumHand hand,
      EnumFacing facing,
      float hitX,
      float hitY,
      float hitZ
  ) {

    // Only do this stuff on the server.
    if (world.isRemote) {
      return true;
    }

    if (hand == EnumHand.MAIN_HAND) {

      ItemStack heldItem = player.getHeldItem(hand);
      TileEntity tileEntity = world.getTileEntity(pos);

      if (tileEntity instanceof TileEntityMortarBase) {
        TileEntityMortarBase tile = (TileEntityMortarBase) tileEntity;

        if (heldItem.isEmpty() && player.isSneaking()) {

          if (!tile.isEmpty()) {
            // If the player is sneaking and activating with an empty hand, pop the last item out of the tile.
            StackUtil.spawnStackOnTop(world, tile.removeItem(), pos);
          }

          return true;

        } else if (!heldItem.isEmpty() && tile.canInsertItem(heldItem)) {

          // If we can insert the item, do eet!
          tile.insertItem(heldItem);
          return true;

        } else if (heldItem.isEmpty() || !ModuleConfig.RECIPES.REQUIRE_EMPTY_HAND_TO_USE) {

          if (player.getFoodStats().getFoodLevel() >= ModuleConfig.RECIPES.MINIMUM_HUNGER_TO_USE) {
            // Only perform crafting if the player has enough hunger.
            // Setting MINIMUM_HUNGER_TO_USE to 0 will ensure this check is always true.

            if (tile.incrementCraftingProgress()) {
              // Only inflict exhaustion if the crafting process was incremented.
              // This check prevents exhaustion from always being applied when the mortar is right-clicked.
              // Setting EXHAUSTION_COST_PER_CLICK to 0 disables exhaustion.
              player.addExhaustion((float) ModuleConfig.RECIPES.EXHAUSTION_COST_PER_CLICK);
            }
          }
        }
      }
    }

    return true;
  }

  public int getMaxDurability(ItemStack itemStack) {

    int metadata = itemStack.getMetadata();
    EnumMortarType type = EnumMortarType.fromMeta(metadata);
    return this.getMaxDurability(type);
  }

  public int getMaxDurability(EnumMortarType type) {

    switch (type) {
      case WOOD:
        return ModuleConfig.DURABILITY.WOOD;
      case STONE:
        return ModuleConfig.DURABILITY.STONE;
      case IRON:
        return ModuleConfig.DURABILITY.IRON;
      case GOLD:
        return ModuleConfig.DURABILITY.GOLD;
      case DIAMOND:
        return ModuleConfig.DURABILITY.DIAMOND;
      case OBSIDIAN:
        return ModuleConfig.DURABILITY.OBSIDIAN;
      case EMERALD:
        return ModuleConfig.DURABILITY.EMERALD;
      default:
        throw new IllegalArgumentException("Unknown mortar type: " + type);
    }
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {

    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(
      World world, IBlockState state
  ) {

    EnumMortarType type = state.getValue(VARIANT);

    switch (type) {
      case WOOD:
        return new TileEntityMortarWood();
      case STONE:
        return new TileEntityMortarStone();
      case IRON:
        return new TileEntityMortarIron();
      case DIAMOND:
        return new TileEntityMortarDiamond();
      case GOLD:
        return new TileEntityMortarGold();
      case OBSIDIAN:
        return new TileEntityMortarObsidian();
      case EMERALD:
        return new TileEntityMortarEmerald();
      default:
        throw new IllegalArgumentException("Unknown variant: " + type);
    }
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

    return AABB;
  }

  @Override
  protected BlockStateContainer createBlockState() {

    return new BlockStateContainer(this, VARIANT);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {

    return this.getDefaultState().withProperty(VARIANT, EnumMortarType.fromMeta(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {

    return state.getValue(VARIANT).getMeta();
  }

  @Override
  public int damageDropped(IBlockState state) {

    return this.getMetaFromState(state);
  }

  @Override
  public void getSubBlocks(
      CreativeTabs tab,
      NonNullList<ItemStack> list
  ) {

    for (EnumMortarType type : EnumMortarType.values()) {
      list.add(new ItemStack(this, 1, type.getMeta()));
    }
  }

  @Override
  public boolean isFullCube(IBlockState state) {

    return false;
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {

    return false;
  }

  @Override
  @Nonnull
  public String getName(ItemStack stack) {

    return NAME + "_" + EnumMortarType.fromMeta(stack.getMetadata()).getName();
  }

  @Override
  public IProperty<EnumMortarType> getVariant() {

    return VARIANT;
  }

}
