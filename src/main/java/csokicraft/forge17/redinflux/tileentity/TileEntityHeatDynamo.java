package csokicraft.forge17.redinflux.tileentity;

import java.util.*;

import javax.vecmath.Vector3d;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.*;
import csokicraft.forge17.redinflux.*;
import csokicraft.forge17.redinflux.network.*;
import csokicraft.forge17.redinflux.network.PacketInfluxSync.PayloadInfluxSync;
import csokicraft.forge17.redinflux.recipes.HeatRegistry;
import csokicraft.util.UtilMcForge;

public class TileEntityHeatDynamo extends TileEntityInfluxPowered implements IEnergyProvider {
	public static String ID="RedInflux:heatDyn";
	int hot, cold;
	/** Slot 0: hot item, 1: cold item*/
	List<Type> biome;
	boolean init=false;
	
	public TileEntityHeatDynamo(){
		slots=new ItemStack[2];
		cap = 30000;
	}
	
	void init(){
		init=true;
		biome=Arrays.asList(BiomeDictionary.getTypesForBiome(worldObj.getBiomeGenForCoords(xCoord, zCoord)));
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(!init) init();
		int pos=HeatRegistry.inst.getHeat(slots[0]),
			neg=HeatRegistry.inst.getHeat(slots[1]);
		//add heat
		if(pos>0&&hot+pos<=cap){
			hot+=pos;
			if(slots[0].isItemEqual(new ItemStack(Items.lava_bucket))) slots[0]=new ItemStack(Items.bucket);
			else slots[0].stackSize--;
			if(slots[0].stackSize<=0) slots[0]=null;
		}
		//add coolant
		if(neg<0&&cold-neg<=cap){
			cold-=neg;
			if(slots[1].isItemEqual(new ItemStack(Items.water_bucket))) slots[1]=new ItemStack(Items.bucket);
			else slots[1].stackSize--;
			if(slots[1].stackSize<=0) slots[1]=null;
		}
		//generate RF
		if(hot>=10&&cold>=10&&rf+200<=cap){
			hot-=10;
			cold-=10;
			rf+=200;
		}
		//heat dissipation
		if(hot>0){
			if(biome.contains(Type.END)) hot-=3;
			else if(biome.contains(Type.COLD)) hot-=2;
			else if(biome.contains(Type.WET)
					||biome.contains(Type.WATER)
					||biome.contains(Type.SNOWY)) hot--;
			else if(biome.contains(Type.FROZEN)) hot--;
			
			int p=new Random().nextInt(100);
			if(!biome.contains(Type.HOT)&&p<10) hot--;
		}
		//heat leaking in
		if(cold>0){
			if(biome.contains(Type.NETHER)) cold-=3;
			else if(biome.contains(Type.HOT)) cold-=2;
			else if(biome.contains(Type.DRY)
					||biome.contains(Type.SANDY)
					||biome.contains(Type.JUNGLE)) cold--;
			else if(biome.contains(Type.DESERT)) cold--;
			
			int p=new Random().nextInt(100);
			if(!biome.contains(Type.COLD)&&p<10)cold--;
		}
		
		discharge(getFace(), 400);
		sendSyncRequest();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		hot=tag.getInteger("hot");
		cold=tag.getInteger("cold");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("hot", hot);
		tag.setInteger("cold", cold);
	}
	
	@Override
	public PayloadInfluxSync getSync() {
		PayloadInfluxSync payload = super.getSync();
		payload.put("hot", hot);
		payload.put("cold", cold);
		return payload;
	}
	
	@Override
	public void onSync(PayloadInfluxSync payload) {
		super.onSync(payload);
		this.hot = (Integer) payload.get("hot");
		this.cold = (Integer) payload.get("cold");
	}
	
	@Deprecated
	public StateHeatDynamo getState(){
		StateHeatDynamo s=new StateHeatDynamo();
		s.x=xCoord;
		s.y=yCoord;
		s.z=zCoord;
		s.rf=this.rf;
		s.hot=this.hot;
		s.cold=this.cold;
		return s;
	}
	
	@Deprecated
	public void setState(StateHeatDynamo s){
		if(s.x!=xCoord||s.y!=yCoord||s.z!=zCoord){
			System.out.println("Attempted to set state to the wrong TE!");
			TileEntity te=worldObj.getTileEntity(s.x, s.y, s.z);
			if(te instanceof TileEntityHeatDynamo) ((TileEntityHeatDynamo)te).setState(s);
			return;
		}
		this.rf=s.rf;
		this.hot=s.hot;
		this.cold=s.cold;
	}
	
	//IInventory
	@Override
	public boolean isItemValidForSlot(int arg0, ItemStack arg1) {
		return arg0==0?HeatRegistry.inst.getHeat(arg1)>0:HeatRegistry.inst.getHeat(arg1)<0;
	}
	
	//TEInfluxPowered
	@Override
	public boolean canConnectEnergy(ForgeDirection dir) {
		return dir.equals(getFace());
	}
	
	@Override
	public boolean canExtract(ForgeDirection dir) {
		return true;
	}
	
	@Override
	public boolean canInsert(ForgeDirection dir) {
		return false;
	}
	
	public String getInvNameUnlocalised(){
		return "tile.blockDynHeat.name";
	}
}
