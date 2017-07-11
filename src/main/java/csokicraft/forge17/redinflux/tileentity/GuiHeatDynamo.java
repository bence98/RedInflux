package csokicraft.forge17.redinflux.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import csokicraft.util.FormatterUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GuiHeatDynamo extends GuiContainer {
	ResourceLocation res=new ResourceLocation("redinflux:textures/gui/heatDyn.png");
	TileEntityHeatDynamo dyn;
	
	public GuiHeatDynamo(TileEntityHeatDynamo te, InventoryPlayer ip) {
		super(new ContainerHeatDynamo(te, ip));
		dyn=te;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float arg0, int arg1,
			int arg2) {
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(res);
		int x=(width-xSize)/2,
			y=(height-ySize)/2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		if(dyn.rf>0){
			int hcol=(50*dyn.rf)/dyn.cap;
			drawTexturedModalRect(x+7, y+64-hcol, 177, 14, 18, hcol);
		}
		if(dyn.hot>0){
			int hcol=(50*dyn.hot)/dyn.cap;
			drawTexturedModalRect(x+61, y+64-hcol, 195, 14, 18, hcol);
		}
		if(dyn.cold>0){
			int hcol=(50*dyn.cold)/dyn.cap;
			drawTexturedModalRect(x+115, y+64-hcol, 213, 14, 18, hcol);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int arg0, int arg1) {
		super.drawGuiContainerForegroundLayer(arg0, arg1);
		fontRendererObj.drawString(dyn.getInventoryName(), 8, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		
		fontRendererObj.drawString("RF:"+FormatterUtil.formatPostfix(dyn.rf), 28, 40, 4210752);
		fontRendererObj.drawString("+T:"+FormatterUtil.formatPostfix(dyn.hot), 82, 40, 4210752);
		fontRendererObj.drawString("-T:"+FormatterUtil.formatPostfix(dyn.cold), 136, 40, 4210752);
	}
}
