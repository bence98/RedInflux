package csokicraft.forge17.redinflux.tileentity;

import java.util.List;

import csokicraft.forge17.redinflux.RedInfluxMod;
import csokicraft.forge17.redinflux.network.PacketInfluxSync.PayloadInfluxSync;
import csokicraft.forge17.redinflux.recipes.RecipesInfluxMachines;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityChemicalDeconstructor extends TileEntityInfluxPowered {
	public static String ID = "RedInflux:chemDec";
	protected int procTime = 0;

	public TileEntityChemicalDeconstructor() {
		slots = new ItemStack[5];
		cap = 1000;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		//if(slots[0]!=null) rf = 1000;
		if(canCraft()){
			List<ItemStack> out = RecipesInfluxMachines.recipesChemicalDeconstructor.getForInput(slots[0]);
			Double energy = RecipesInfluxMachines.recipesChemicalDeconstructor.getEnergyForInput(slots[0]);
			procTime++;
			if(procTime >= 200){
				slots[0].stackSize--;
				doCraft();
				procTime -= 200;
			}
		}
		sendSyncRequest();
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection dir) {
		return !dir.equals(getFace());
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		if(i!=0) return false;
		return RecipesInfluxMachines.recipesChemicalDeconstructor.getForInput(is) != null;
	}

	@Override
	public boolean canExtract(ForgeDirection dir) {
		return false;
	}

	@Override
	public boolean canInsert(ForgeDirection dir) {
		return true;
	}

	@Override
	public String getInvNameUnlocalised() {
		return "tile.blockChemDec.name";
	}

	@Override
	public PayloadInfluxSync getSync() {
		PayloadInfluxSync payload = super.getSync();
		payload.put("progress", procTime);
		return payload;
	}
	
	@Override
	public void onSync(PayloadInfluxSync payload) {
		super.onSync(payload);
		procTime = (Integer) payload.get("progress");
	}
}
