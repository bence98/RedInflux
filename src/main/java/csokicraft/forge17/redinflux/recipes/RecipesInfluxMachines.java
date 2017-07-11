package csokicraft.forge17.redinflux.recipes;

import csokicraft.forge17.redinflux.RedInfluxMod;
import csokicraft.util.CollectionUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipesInfluxMachines{
	public static RecipeHandlerInflux recipesChemicalDeconstructor = new RecipeHandlerInflux(),
			recipesReactor = new RecipeHandlerInflux();
	
	public static void init(){
		recipesChemicalDeconstructor.addForOutput(new ItemStack(Items.emerald), 200, CollectionUtils.fromArray(new ItemStack[]{
				new ItemStack(RedInfluxMod.dust, 1, 0),
				new ItemStack(RedInfluxMod.dust, 2, 1),
				new ItemStack(Blocks.sand, 6),
				new ItemStack(RedInfluxMod.dust, 1, 2)
		}));
		
		recipesReactor.addForOutput(new ItemStack(RedInfluxMod.dust, 1, 0), 22.8912, CollectionUtils.fromArray(new ItemStack[]{
				new ItemStack(Items.coal, 2)
		}));
	}
}
