package csokicraft.forge17.redinflux.block;

import cofh.api.item.IToolHammer;
import csokicraft.forge17.redinflux.network.IInfluxSynced;
import csokicraft.forge17.redinflux.tileentity.*;
import csokicraft.util.UtilMcForge;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockInfluxMachine extends BlockContainer{

	protected IIcon faceIcon;

	public BlockInfluxMachine() {
		super(Material.iron);
		setHarvestLevel("pickaxe", 0);
		setHardness(1f);
	}
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z,EntityPlayer p, int s, float f1, float f2, float f3){
		if(p.isSneaking()){
			if(p.getHeldItem()!=null&&isWrench(p.getHeldItem())){
				this.breakBlock(w, x, y, z, this, w.getBlockMetadata(x, y, z));
				wrenchUsed(p.getHeldItem());
				return true;
			}
			IInfluxSynced te=(IInfluxSynced) w.getTileEntity(x, y, z);
			p.addChatMessage(new ChatComponentText(getSide(w)+te.getSync().toString()));
			return true;
		}
		return false;
	}
	
	private String getSide(World w) {
		return w.isRemote?"Client>":"Server>";
	}
	
	@Override
	public IIcon getIcon(IBlockAccess w, int x, int y, int z, int s) {
		ForgeDirection face=((TileEntityInfluxMachine) w.getTileEntity(x, y, z)).getFace();
		if(s==face.ordinal()) return faceIcon;
		return blockIcon;
	}

	/**for future wrench compat*/
	private static void wrenchUsed(ItemStack item) {}

	public static boolean isWrench(ItemStack i) {
		return i.getItem() instanceof IToolHammer;
	}

	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase e, ItemStack is) {
		ForgeDirection dir=UtilMcForge.getEntityOrientation(e.rotationYaw, e.rotationPitch).getOpposite();
		w.setBlock(x, y, z, this);
		w.setBlockMetadataWithNotify(x, y, z, dir.ordinal(), 3);
	}
}
