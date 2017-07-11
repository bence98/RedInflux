package csokicraft.forge17.redinflux.tileentity;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerHeatDynamo extends ContainerInflux {
	
	public ContainerHeatDynamo(TileEntityInfluxMachine machine, InventoryPlayer ip) {
		super(machine, ip);
	}

	protected void addSlots() {
		addSlotToContainer(new Slot(te, 0, 35, 15));
		addSlotToContainer(new Slot(te, 1, 89, 15));
	}

}
