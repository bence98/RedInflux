package csokicraft.forge17.redinflux.tileentity;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerChemicalDeconstructor extends ContainerInflux {

	public ContainerChemicalDeconstructor(TileEntityInfluxMachine machine, InventoryPlayer ip) {
		super(machine, ip);
	}

	@Override
	protected void addSlots() {
		addSlotToContainer(new Slot(te, 0, 35, 15));
		addSlotToContainer(new Slot(te, 1, 65, 15));
		addSlotToContainer(new Slot(te, 2, 85, 15));
		addSlotToContainer(new Slot(te, 3, 105, 15));
		addSlotToContainer(new Slot(te, 4, 125, 15));
	}

}
