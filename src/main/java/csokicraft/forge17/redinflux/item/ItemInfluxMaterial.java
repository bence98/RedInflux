package csokicraft.forge17.redinflux.item;

import net.minecraft.item.*;
import net.minecraft.util.IIcon;

/** Metas:
  * 0: Beryllium
  * 1: Aluminium dust
  * 2: Tiny Chromium dust
  * 3: Alu ingot
  * 4: Neutron shielding*/
public class ItemInfluxMaterial extends Item {
	protected IIcon[] icons = new IIcon[5];
	
	@Override
	public String getUnlocalizedName(ItemStack is) {
		return "item.itemInfluxDust."+is.getItemDamage();
	}
}
