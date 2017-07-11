package csokicraft.forge17.redinflux.tileentity;

import csokicraft.forge17.redinflux.RedInfluxMod;
import csokicraft.forge17.redinflux.network.IInfluxSynced;
import csokicraft.forge17.redinflux.network.PacketInfluxSync.PayloadInfluxSync;
import net.minecraft.tileentity.TileEntity;

public class TileEntityInfluxSynced extends TileEntity implements IInfluxSynced{
	public static String ID;

	@Override
	public void onSync(PayloadInfluxSync payload) {
		int x = (Integer) payload.get("x");
		int y = (Integer) payload.get("y");
		int z = (Integer) payload.get("z");
		int w = (Integer) payload.get("w");
		if(!(x == xCoord && y == yCoord && z == zCoord && w == worldObj.provider.dimensionId))
			throw new IllegalArgumentException("No TE found in dim "+w+" @ "+x+","+y+","+"z");
	}

	@Override
	public PayloadInfluxSync getSync() {
		PayloadInfluxSync payload = new PayloadInfluxSync();
		payload.put("x", xCoord);
		payload.put("y", yCoord);
		payload.put("z", zCoord);
		payload.put("w", worldObj.provider.dimensionId);
		return payload;
	}
	
	protected void sendSyncRequest(){
		if(!worldObj.isRemote)
			RedInfluxMod.nethndl.dispatchMessage(this);
	}
}
