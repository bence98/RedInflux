package csokicraft.forge17.redinflux.recipes;

import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;

public class HeatRegistry {
	private Map<ItemStack, Integer> heat=new HashMap<ItemStack, Integer>();
	public static HeatRegistry inst=new HeatRegistry();
	
	/** Positive values mean the item is hot, negative if it is cold*/
	public int getHeat(ItemStack is){
		if(is==null) return 0;
		for(ItemStack e:heat.keySet()){
			if(e.isItemEqual(is)) return heat.get(e);
		}
		return 0;
	}
	
	public void registerItem(ItemStack is, int h){
		if(is==null||h==0) return;
		System.out.println("[Debug-Redstone Influx] HeatRegistry: registering item "+is+" to have a heat of "+h);
		heat.put(is, h);
	}
	
	static{
		inst.registerItem(new ItemStack(Items.blaze_powder), 1200);
		inst.registerItem(new ItemStack(Items.blaze_rod), 2400);
		inst.registerItem(new ItemStack(Items.redstone), 1000);
		inst.registerItem(new ItemStack(Items.gunpowder), 300);
		inst.registerItem(new ItemStack(Items.lava_bucket), 20000);
		inst.registerItem(new ItemStack(Items.fire_charge), 600);
		inst.registerItem(new ItemStack(Items.magma_cream), 1600);
		
		inst.registerItem(new ItemStack(Items.water_bucket), -1200);
		inst.registerItem(new ItemStack(Items.potionitem, 1, 0), -400);
		inst.registerItem(new ItemStack(Items.snowball), -200);
		inst.registerItem(new ItemStack(Blocks.ice), -1500);
		inst.registerItem(new ItemStack(Blocks.packed_ice), -13500);
		inst.registerItem(new ItemStack(Blocks.snow), -800);
	}
}
