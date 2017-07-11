package csokicraft.forge17.redinflux.tileentity;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class GuiChemicalDeconstructor extends GuiContainer{
	protected TileEntityChemicalDeconstructor machine;

	public GuiChemicalDeconstructor(TileEntityChemicalDeconstructor te, InventoryPlayer ip) {
		super(new ContainerChemicalDeconstructor(te, ip));
		machine = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		
	}

}
