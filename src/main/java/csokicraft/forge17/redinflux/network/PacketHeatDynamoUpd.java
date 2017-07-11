package csokicraft.forge17.redinflux.network;

import java.io.*;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import csokicraft.forge17.redinflux.tileentity.*;
import csokicraft.util.QueuedStream;

@Deprecated
public class PacketHeatDynamoUpd implements IMessage {
	public StateHeatDynamo te;
	
	public PacketHeatDynamoUpd(StateHeatDynamo dyn) {
		te=dyn;
	}
	
	public PacketHeatDynamoUpd() {
		te=null;
	}

	@Override
	public void fromBytes(ByteBuf b) {
		QueuedStream qs=new QueuedStream();
		try {
			b.resetReaderIndex();
			OutputStream s=qs.getOutputStream();
			while(b.isReadable()){
				s.write(b.readByte());
			}
			s.close();
			ObjectInputStream in=new ObjectInputStream(qs.getInputStream());
			te=(StateHeatDynamo) in.readObject();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf b) {
		if(te==null) return;
		QueuedStream qs=new QueuedStream();
		try {
			ObjectOutputStream oos=new ObjectOutputStream(qs.getOutputStream());
			oos.writeObject(te);
			oos.flush();
			oos.close();
			InputStream is=qs.getInputStream();
			while(qs.ready())
				b.writeByte(is.read());
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
