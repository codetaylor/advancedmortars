package com.sudoplay.mc.pwcustom.lib.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PortalFrameUtil {

  private final World world;
  private final EnumFacing.Axis axis;
  private final EnumFacing rightDir;
  private final EnumFacing leftDir;
  private int portalBlockCount;
  private BlockPos bottomLeft;
  private int height;
  private int width;

  private final IBlockState portalFrameBlockState;
  private final Block portalBlock;

  public PortalFrameUtil(
      World worldIn,
      BlockPos pos,
      EnumFacing.Axis axis,
      IBlockState portalFrameBlockState,
      Block portalBlock
  ) {

    this.world = worldIn;
    this.axis = axis;
    this.portalFrameBlockState = portalFrameBlockState;
    this.portalBlock = portalBlock;

    if (axis == EnumFacing.Axis.X) {
      this.leftDir = EnumFacing.EAST;
      this.rightDir = EnumFacing.WEST;

    } else {
      this.leftDir = EnumFacing.NORTH;
      this.rightDir = EnumFacing.SOUTH;
    }

    for (BlockPos blockpos = pos;
         pos.getY() > blockpos.getY() - 21 && pos.getY() > 0 && this.isEmptyBlock(worldIn.getBlockState(
             pos.down()).getBlock());
         pos = pos.down()) {
      //
    }

    int i = this.getDistanceUntilEdge(pos, this.leftDir) - 1;

    if (i >= 0) {
      this.bottomLeft = pos.offset(this.leftDir, i);
      this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);

      if (this.width < 2 || this.width > 21) {
        this.bottomLeft = null;
        this.width = 0;
      }
    }

    if (this.bottomLeft != null) {
      this.height = this.calculatePortalHeight();
    }
  }

  protected int getDistanceUntilEdge(BlockPos p_180120_1_, EnumFacing p_180120_2_) {

    int i;

    for (i = 0; i < 22; ++i) {
      BlockPos blockpos = p_180120_1_.offset(p_180120_2_, i);

      if (!this.isEmptyBlock(this.world.getBlockState(blockpos).getBlock())
          || this.world.getBlockState(blockpos.down()) != this.portalFrameBlockState) {
        break;
      }
    }

    IBlockState blockState = this.world.getBlockState(p_180120_1_.offset(p_180120_2_, i));
    return blockState == this.portalFrameBlockState ? i : 0;
  }

  public int getHeight() {

    return this.height;
  }

  public int getWidth() {

    return this.width;
  }

  protected int calculatePortalHeight() {

    label56:

    for (this.height = 0; this.height < 21; ++this.height) {
      for (int i = 0; i < this.width; ++i) {
        BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
        IBlockState blockState = this.world.getBlockState(blockpos);
        Block block = blockState.getBlock();

        if (!this.isEmptyBlock(block)) {
          break label56;
        }

        if (block == this.portalBlock) {
          ++this.portalBlockCount;
        }

        if (i == 0) {
          blockState = this.world.getBlockState(blockpos.offset(this.leftDir));

          if (blockState != this.portalFrameBlockState) {
            break label56;
          }
        } else if (i == this.width - 1) {
          blockState = this.world.getBlockState(blockpos.offset(this.rightDir));

          if (blockState != this.portalFrameBlockState) {
            break label56;
          }
        }
      }
    }

    for (int j = 0; j < this.width; ++j) {
      IBlockState blockState = this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height));

      if (blockState != this.portalFrameBlockState) {
        this.height = 0;
        break;
      }
    }

    if (this.height <= 21 && this.height >= 3) {
      return this.height;

    } else {
      this.bottomLeft = null;
      this.width = 0;
      this.height = 0;
      return 0;
    }
  }

  protected boolean isEmptyBlock(Block blockIn) {

    return blockIn.getMaterial(blockIn.getDefaultState()) == Material.AIR
        || blockIn == this.portalBlock;
  }

  public boolean isValid() {

    return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
  }

  public void placePortalBlocks() {

    for (int i = 0; i < this.width; ++i) {
      BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

      for (int j = 0; j < this.height; ++j) {
        this.world.setBlockState(
            blockpos.up(j),
            this.portalBlock.getDefaultState().withProperty(Props.PORTAL_AXIS, this.axis),
            2
        );
      }
    }
  }

  public int getPortalBlockCount() {

    return this.portalBlockCount;
  }

  public EnumFacing getRightDir() {

    return this.rightDir;
  }

  public EnumFacing getLeftDir() {

    return this.leftDir;
  }

  public BlockPos getBottomLeft() {

    return this.bottomLeft;
  }

}
