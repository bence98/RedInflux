package csokicraft.forge17.redinflux.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import csokicraft.forge17.redinflux.network.PacketInfluxSync.PayloadInfluxSync;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.DimensionManager;

public class InfluxClientMessageHandler implements IMessageHandler<PacketInfluxSync, IMessage>{

	@Override
	public IMessage onMessage(PacketInfluxSync message, MessageContext ctx){
		PayloadInfluxSync payload = message.payload;
		int x = (Integer) payload.get("x"),
			y = (Integer) payload.get("y"),
			z = (Integer) payload.get("z"),
			w = (Integer) payload.get("w");
		TileEntity te = DimensionManager.getProvider(w).worldObj.getTileEntity(x, y, z);
		if(!(te instanceof IInfluxSynced))
			System.out.println("[Error-Redstone Influx] There wasn't an IInfluxSynced at "+x+" "+y+" "+z);
		else
			((IInfluxSynced)te).onSync(payload);
		return null;
	}
	
}
