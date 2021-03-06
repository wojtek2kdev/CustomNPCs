package noppes.npcs.blocks;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import noppes.npcs.CustomNpcs;
import noppes.npcs.blocks.BlockRotated;
import noppes.npcs.blocks.tiles.TileBarrel;
import noppes.npcs.blocks.tiles.TileNpcContainer;
import noppes.npcs.constants.EnumGuiType;

public class BlockBarrel extends BlockRotated {

   public BlockBarrel() {
      super(Blocks.planks);
   }

   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(new ItemStack(par1, 1, 0));
      par3List.add(new ItemStack(par1, 1, 1));
      par3List.add(new ItemStack(par1, 1, 2));
      par3List.add(new ItemStack(par1, 1, 3));
      par3List.add(new ItemStack(par1, 1, 4));
      par3List.add(new ItemStack(par1, 1, 5));
   }

   public int damageDropped(int par1) {
      return par1;
   }

   public int maxRotation() {
      return 8;
   }

   public boolean onBlockActivated(World par1World, int i, int j, int k, EntityPlayer player, int par6, float par7, float par8, float par9) {
      if(par1World.isRemote) {
         return true;
      } else {
         par1World.playSoundEffect((double)i, (double)j + 0.5D, (double)k, "random.chestopen", 0.5F, par1World.rand.nextFloat() * 0.1F + 0.9F);
         player.openGui(CustomNpcs.instance, EnumGuiType.Crate.ordinal(), par1World, i, j, k);
         return true;
      }
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
      par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileBarrel();
   }

   public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
      TileNpcContainer tile = (TileNpcContainer)world.getTileEntity(x, y, z);
      if(tile != null) {
         tile.dropItems(world, x, y, z);
         world.updateNeighborsAboutBlockChange(x, y, z, block);
         super.breakBlock(world, x, y, z, block, p_149749_6_);
      }
   }
}
