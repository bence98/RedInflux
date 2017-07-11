package csokicraft.forge17.redinflux.tileentity;

import java.util.List;

import javax.vecmath.Vector3d;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import csokicraft.forge17.redinflux.network.PacketInfluxSync.PayloadInfluxSync;
import csokicraft.forge17.redinflux.recipes.RecipesInfluxMachines;
import csokicraft.util.UtilMcForge;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityInfluxPowered extends TileEntityInfluxMachine implements IEnergyHandler{
	protected int rf=0, cap;

	@Override
	public int getEnergyStored(ForgeDirection arg0) {
		return rf;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection arg0) {
		return cap;
	}
	
	public abstract boolean canExtract(ForgeDirection dir);
	public abstract boolean canInsert(ForgeDirection dir);

	@Override
	public int extractEnergy(ForgeDirection dir, int nrg, boolean sim) {
		if(!canExtract(dir)||!canConnectEnergy(dir)) return 0;
		int rm=Math.min(nrg,rf);
		if(!sim) rf-=rm;
		return rm;
	}

	@Override
	public int receiveEnergy(ForgeDirection dir, int nrg, boolean sim){
		if(!canInsert(dir)||!canConnectEnergy(dir)) return 0;
		int add=Math.min(nrg,cap-rf);
		if(!sim) rf+=add;
		return add;
	}
	
	protected void discharge(ForgeDirection dir, int rate){
		if(rf>0){
			int add=Math.min(rate, rf);
			Vector3d t=UtilMcForge.getPosAtSide(new Vector3d(xCoord, yCoord, zCoord), dir);
			TileEntity n=worldObj.getTileEntity((int) t.x, (int) t.y, (int) t.z);
			if(n instanceof IEnergyReceiver){
				IEnergyReceiver c=(IEnergyReceiver) n;
				int rm=c.receiveEnergy(dir.getOpposite(), add, false);
				extractEnergy(dir, rm, false);
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		rf=tag.getInteger("rf");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("rf", rf);
	}
	
	@Override
	public PayloadInfluxSync getSync() {
		PayloadInfluxSync payload = super.getSync();
		payload.put("rf", rf);
		return payload;
	}
	
	@Override
	public void onSync(PayloadInfluxSync payload) {
		super.onSync(payload);
		rf = (Integer) payload.get("rf");
	}
	
	protected boolean canCraft(int inputSlotsCount){
		List<ItemStack> out = RecipesInfluxMachines.recipesChemicalDeconstructor.getForInput(slots[0]);
		if(out == null || out.size() > getSizeInventory()-inputSlotsCount) return false;
		Double energy = RecipesInfluxMachines.recipesChemicalDeconstructor.getEnergyForInput(slots[0]);
		if(energy != null && rf < energy) return false;
		
		for(int i=0;i<out.size();i++)
			if(getStackInSlot(i+inputSlotsCount) != null)
				if(!out.get(i).isItemEqual(getStackInSlot(i+inputSlotsCount)))
					return false;
		
		return true;
	}
	
	/** Calls {@link #canCraft(int)} with 1 as input slot count */
	protected boolean canCraft(){
		return canCraft(1);
	}
	
	protected void doCraft(int inputSlotsCount){
		if(!canCraft(inputSlotsCount)) return;
		List<ItemStack> out = RecipesInfluxMachines.recipesChemicalDeconstructor.getForInput(slots[0]);
		for(int i=0;i<4;i++){
			ItemStack is = out.get(i);
			if(getStackInSlot(i) != null) is.stackSize += getStackInSlot(i).stackSize;
			setInventorySlotContents(i+1, is);
		}
		
		for(int i=0;i<inputSlotsCount;i++){
			if(--getStackInSlot(i).stackSize <= 0) setInventorySlotContents(i, null);
		}
		
		Double energy = RecipesInfluxMachines.recipesChemicalDeconstructor.getEnergyForInput(slots[0]);
		if(energy != null) rf -= energy;
	}
	
	/** Calls {@link #doCraft(int)} with 1 as input slot count */
	protected void doCraft(){
		doCraft(1);
	}
}
