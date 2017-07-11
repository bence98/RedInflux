package csokicraft.forge17.redinflux.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityInfluxMachine extends TileEntityInfluxSynced implements IInventory{
	ItemStack slots[];

	public abstract String getInvNameUnlocalised();

	@Override
	public void closeInventory() {}

	public ForgeDirection getFace(){
		return ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
	}
	
	@Override
	public ItemStack decrStackSize(int s, int n) {
		int rm=Math.min(slots[s].stackSize, n);
		ItemStack is=slots[s].copy();
		is.stackSize=rm;
		slots[s].stackSize-=rm;
		if(slots[s].stackSize<=0) slots[s]=null;
		return is;
	}
	
	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal(getInvNameUnlocalised());
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public int getSizeInventory() {
		return slots.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int i) {
		return ItemStack.copyItemStack(slots[i]);
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return slots[i];
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer arg0) {
		return true;
	}
	
	@Override
	public void openInventory() {}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack is) {
		slots[i]=is;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		NBTTagList l=tag.getTagList("items", 2);
		for(int i=0;i<slots.length;i++){
			int s=l.getCompoundTagAt(i).getInteger("slot");
			slots[s]=ItemStack.loadItemStackFromNBT(l.getCompoundTagAt(i));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		NBTTagList l=new NBTTagList();
		for(int i=0;i<slots.length;i++){
			if(slots[i]==null) continue;
			NBTTagCompound nbt=new NBTTagCompound();
			slots[i].writeToNBT(nbt);
			l.appendTag(nbt);
			l.getCompoundTagAt(i).setInteger("slot", i);
		}
		tag.setTag("items", l);
	}
}
