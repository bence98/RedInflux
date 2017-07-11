package csokicraft.forge17.redinflux.network;

import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import csokicraft.forge17.redinflux.network.PacketInfluxSync.PayloadInfluxSync;
import net.minecraft.entity.player.EntityPlayerMP;

public class InfluxServerMessageHandler{
	protected SimpleNetworkWrapper wrapper;

	public InfluxServerMessageHandler(SimpleNetworkWrapper net) {
		wrapper = net;
	}

	public void sendMessage(IInfluxSynced caller, List<EntityPlayerMP> l){
		for(EntityPlayerMP p:l)
			wrapper.sendTo(new PacketInfluxSync(caller.getSync()), p);
	}

	public void dispatchMessage(IInfluxSynced caller){
		wrapper.sendToAll(new PacketInfluxSync(caller.getSync()));
	}

}
