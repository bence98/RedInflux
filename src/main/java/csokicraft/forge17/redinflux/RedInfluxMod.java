package csokicraft.forge17.redinflux;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.*;
import net.minecraft.item.*;

import java.io.IOException;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import csokicraft.forge17.redinflux.block.*;
import csokicraft.forge17.redinflux.item.ItemInfluxMaterial;
import csokicraft.forge17.redinflux.network.*;
import csokicraft.forge17.redinflux.recipes.HeatRegistry;
import csokicraft.forge17.redinflux.recipes.RecipesInfluxMachines;
import csokicraft.forge17.redinflux.tileentity.*;
import io.netty.buffer.*;

/** A mod made for the fun of tinkering with CoFHAPI
  * @author CsokiCraft*/
@Mod(modid = RedInfluxMod.MODID, version = RedInfluxMod.VERSION)
public class RedInfluxMod{
    public static final String MODID = "RedInflux";
    public static final String VERSION = "1.1";
    
    @Instance
	public static RedInfluxMod inst=new RedInfluxMod();
    //@SidedProxy(clientSide="csokicraft.forge17.redinflux.ClientProxy", serverSide="csokicraft.forge17.redinflux.CommonProxy")
    public static CommonProxy proxy=new CommonProxy();
    public static SimpleNetworkWrapper net;
    public static InfluxServerMessageHandler nethndl;
    
    public static CreativeTabs tab=new TabRedInflux();
    public static Block blockDynHeat=new BlockHeatDynamo().setBlockName("blockDynHeat").setCreativeTab(tab),
    					blockChemDec=new BlockChemicalDeconstructor().setBlockName("blockChemDec").setCreativeTab(tab);
    
    public static Item dust = new ItemInfluxMaterial();
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		NetworkRegistry.INSTANCE.registerGuiHandler(inst, proxy);
		net=NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		net.registerMessage(InfluxClientMessageHandler.class, PacketInfluxSync.class, 0, Side.CLIENT);
		nethndl = new InfluxServerMessageHandler(net);
		
		GameRegistry.registerBlock(blockDynHeat, "blockDynHeat");
		GameRegistry.registerBlock(blockChemDec, "blockChemDec");
		GameRegistry.registerItem(dust, "itemDust");
		
		GameRegistry.registerTileEntity(TileEntityHeatDynamo.class, TileEntityHeatDynamo.ID);
		GameRegistry.registerTileEntity(TileEntityChemicalDeconstructor.class, TileEntityChemicalDeconstructor.ID);
		
		GameRegistry.addRecipe(new ItemStack(blockDynHeat), "rrr", "bpb", " f ", 'r', Items.redstone, 'b', Items.bucket, 'p', Blocks.piston, 'f', Blocks.furnace);
		GameRegistry.addSmelting(new ItemStack(dust, 1, 1),  new ItemStack(dust, 1, 3),  1);
		
		HeatRegistry.inst.getClass();
		RecipesInfluxMachines.init();
		
		if(Loader.isModLoaded("ThermalFoundation")){
			Item CoFHDust=GameRegistry.findItem("ThermalFoundation", "material");
			HeatRegistry.inst.registerItem(new ItemStack(CoFHDust, 1, 512), 25000);
			HeatRegistry.inst.registerItem(new ItemStack(CoFHDust, 1, 513), -25000);
			HeatRegistry.inst.registerItem(new ItemStack(CoFHDust, 1, 1024), -2400);
			HeatRegistry.inst.registerItem(new ItemStack(CoFHDust, 1, 1025), -1200);
		}
		if(Loader.isModLoaded("RotaryCraft")){
			Item RC = GameRegistry.findItem("RotaryCraft", "rotarycraft_item_powders");
			HeatRegistry.inst.registerItem(new ItemStack(RC, 1, 11), -10000);
		}
    }
}
