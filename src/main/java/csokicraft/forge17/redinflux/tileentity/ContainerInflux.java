package csokicraft.forge17.redinflux.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerInflux extends Container {
	protected TileEntityInfluxMachine te;
	
	public ContainerInflux(TileEntityInfluxMachine machine, InventoryPlayer ip) {
		te=machine;
		bindPlayerInventory(ip);
		addSlots();
	}
	
	protected abstract void addSlots();
	
	@Override
	public boolean canInteractWith(EntityPlayer arg0) {
		return te.isUseableByPlayer(arg0);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p, int i) {
		InventoryPlayer ip=p.inventory;
		ItemStack is=null;
		Slot s=(Slot) inventorySlots.get(i);
		if(s!=null&&s.getHasStack()){
			ItemStack slot=s.getStack();
			is=slot.copy();
			if(i<te.getSizeInventory()){ //Slot in TE
				if(!mergeItemStack(slot, te.getSizeInventory(), te.getSizeInventory()+36, false)) return null;
			}
			else{//Slot in IP
				if(!mergeItemStack(slot, 0, te.getSizeInventory(), false)) return null;
			}
			int slotSize = slot.stackSize;
			if(slotSize==0) s.putStack(null);
			else s.onSlotChanged();
			if(slotSize==is.stackSize) return null;
			s.onPickupFromSlot(p, slot);
		}
		return is;
	}

	/** Source: minecraftforge.net/wiki/Containers_and_GUIs */
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer){
        for(int i=0; i<3; i++){
          for(int j = 0; j < 9; j++){
             addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                        8 + j * 18, 84 + i * 18));
          }
        }

        for(int i=0; i<9; i++){
           addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }
}
