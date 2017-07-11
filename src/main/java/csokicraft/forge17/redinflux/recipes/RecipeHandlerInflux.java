package csokicraft.forge17.redinflux.recipes;

import java.util.*;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;

public class RecipeHandlerInflux{
	protected Map<ItemStack, List<ItemStack>> recipeMap;
	protected Map<ItemStack, Double> energyMap;
	
	protected RecipeHandlerInflux(){
		recipeMap = new HashMap<ItemStack, List<ItemStack>>();
		energyMap = new HashMap<ItemStack, Double>();
	}
	
	public void addForOutput(ItemStack in, double nrg, List<ItemStack> out){
		recipeMap.put(in, out);
		energyMap.put(in, nrg);
	}
	
	public List<ItemStack> getForInput(ItemStack in){
		if(in == null) return null;
		for(Entry<ItemStack, List<ItemStack>> e:recipeMap.entrySet()){
			if(e.getKey().isItemEqual(in)) return e.getValue();
		}
		return null;
	}
	
	public Double getEnergyForInput(ItemStack in){
		if(in == null) return null;
		for(Entry<ItemStack, Double> e:energyMap.entrySet()){
			if(e.getKey().isItemEqual(in)) return e.getValue();
		}
		return null;
	}
}
