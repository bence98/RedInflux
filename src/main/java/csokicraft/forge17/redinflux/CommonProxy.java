package csokicraft.forge17.redinflux;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import csokicraft.forge17.redinflux.network.PacketHeatDynamoUpd;
import csokicraft.forge17.redinflux.network.PacketInfluxSync;
import csokicraft.forge17.redinflux.network.PacketInfluxSync.PayloadInfluxSync;
import csokicraft.forge17.redinflux.tileentity.*;

public class CommonProxy implements IGuiHandler{
	public static final int HDYN_ID=42;
	public static final int CHDE_ID=43;
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer p, World w,
			int x, int y, int z) {
		TileEntity te=w.getTileEntity(x, y, z);
		switch (id) {
		case HDYN_ID:
			if(te instanceof TileEntityHeatDynamo) return new GuiHeatDynamo((TileEntityHeatDynamo) te, p.inventory);
			break;

		case CHDE_ID:
			if(te instanceof TileEntityChemicalDeconstructor) return new GuiChemicalDeconstructor((TileEntityChemicalDeconstructor) te, p.inventory);
			break;
		}
		
		return null;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer p, World w,
			int x, int y, int z) {
		TileEntity te=w.getTileEntity(x, y, z);
		switch (id) {
		case HDYN_ID:
			if(te instanceof TileEntityHeatDynamo) return new ContainerHeatDynamo((TileEntityHeatDynamo) te, p.inventory);
			break;

		case CHDE_ID:
			if(te instanceof TileEntityChemicalDeconstructor) return new ContainerChemicalDeconstructor((TileEntityChemicalDeconstructor) te, p.inventory);
			break;
		}
		return null;
	}
	
	@Deprecated
	public IMessage onMessage(PacketHeatDynamoUpd pck, MessageContext ctx) {
		StateHeatDynamo dyn=pck.te;
		if(dyn==null){
			System.out.println("TE was null!");
			return null;
		}
		TileEntity te=Minecraft.getMinecraft().theWorld.getTileEntity(dyn.x, dyn.y, dyn.z);
		if(!(te instanceof TileEntityHeatDynamo))
			System.out.println("There weren't a TileEntityHeatDynamo at "+dyn.x+" "+dyn.y+" "+dyn.z);
		else ((TileEntityHeatDynamo) te).setState(dyn);
		return null;
	}


}
