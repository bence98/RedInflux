package csokicraft.forge17.redinflux.block;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import csokicraft.forge17.redinflux.*;
import csokicraft.forge17.redinflux.tileentity.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHeatDynamo extends BlockInfluxMachine{

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		return new TileEntityHeatDynamo();
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z,EntityPlayer p, int s, float f1, float f2, float f3) {
		if(super.onBlockActivated(w, x, y, z, p, s, f1, f2, f3)) return true;
		if(!w.isRemote){
			FMLNetworkHandler.openGui(p, RedInfluxMod.inst, CommonProxy.HDYN_ID, w, x, y, z);
		}
		return true;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister arg0) {
		blockIcon=arg0.registerIcon("RedInflux:heatDyn");
		faceIcon=arg0.registerIcon("RedInflux:heatDynFace");
	}
}
