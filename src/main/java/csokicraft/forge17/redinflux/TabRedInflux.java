package csokicraft.forge17.redinflux;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class TabRedInflux extends CreativeTabs {

	public TabRedInflux() {
		super("RedInflux");
	}

	@Override
	public Item getTabIconItem() {
		return Items.redstone;
	}

}
