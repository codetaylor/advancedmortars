package com.sudoplay.mc.pwcustom.lib.gui;

import com.sudoplay.mc.pwcustom.lib.tile.IContainerProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler
    implements IGuiHandler {

  @Nullable
  @Override
  public Object getServerGuiElement(
      int ID, EntityPlayer player, World world, int x, int y, int z
  ) {

    BlockPos blockPos = new BlockPos(x, y, z);
    IBlockState blockState = world.getBlockState(blockPos);
    TileEntity tileEntity = world.getTileEntity(blockPos);

    if (tileEntity instanceof IContainerProvider) {
      return ((IContainerProvider) tileEntity).getContainer(player.inventory, world, blockState, blockPos);
    }

    return null;
  }

  @Nullable
  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

    BlockPos blockPos = new BlockPos(x, y, z);
    IBlockState blockState = world.getBlockState(blockPos);
    TileEntity tileEntity = world.getTileEntity(blockPos);

    if (tileEntity instanceof IContainerProvider) {
      return ((IContainerProvider) tileEntity).getGuiContainer(player.inventory, world, blockState, blockPos);
    }

    return null;
  }
}
