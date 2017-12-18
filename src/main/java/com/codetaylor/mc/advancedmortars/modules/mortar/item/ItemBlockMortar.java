package com.codetaylor.mc.advancedmortars.modules.mortar.item;

import com.codetaylor.mc.advancedmortars.modules.mortar.ModuleMortar;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockMortar
    extends ItemMultiTexture {

  public ItemBlockMortar(
      Block block,
      Mapper mapper
  ) {

    super(block, block, mapper);
    this.setMaxStackSize(1);

    ResourceLocation registryName = block.getRegistryName();

    if (registryName != null) {
      this.setRegistryName(registryName);

    } else {
      throw new RuntimeException("Block missing registry name: " + block.getClass().getName());
    }
  }

  @Override
  public int getItemBurnTime(ItemStack itemStack) {

    return 0;
  }

  @Override
  public void addInformation(
      ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn
  ) {

    int maxDurability = ModuleMortar.Blocks.MORTAR.getMaxDurability(stack);

    if (maxDurability > 0) {
      NBTTagCompound stackCompound = stack.getTagCompound();

      if (stackCompound != null
          && stackCompound.hasKey("BlockEntityTag")) {
        NBTTagCompound compound = stackCompound.getCompoundTag("BlockEntityTag");
        int durability = maxDurability - compound.getInteger("durability");
        tooltip.add("Durability: " + durability + "/" + maxDurability);

      } else {
        tooltip.add("Durability: " + maxDurability + "/" + maxDurability);
      }

    } else {
      tooltip.add("Durability: Unbreakable");
    }

    if (GuiScreen.isShiftKeyDown()) {
      tooltip.add("SHIFT + LEFT-CLICK to pick up mortar.");
      tooltip.add("SHIFT + RIGHT-CLICK with an empty hand");
      tooltip.add("  on an empty mortar to switch modes.");

    } else {
      tooltip.add("Hold SHIFT for more info.");
    }
  }
}
