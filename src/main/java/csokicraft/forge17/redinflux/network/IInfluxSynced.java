package csokicraft.forge17.redinflux.network;

import csokicraft.forge17.redinflux.network.PacketInfluxSync.PayloadInfluxSync;

public interface IInfluxSynced {
	/** Called to handle inbound sync requests */
	public void onSync(PayloadInfluxSync payload);
	/** Called to make outbound sync requests */
	public PayloadInfluxSync getSync();
}
