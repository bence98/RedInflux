package csokicraft.forge17.redinflux.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.*;
import java.util.Map.Entry;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import csokicraft.util.StringInputStream;
import csokicraft.util.StringOutputStream;

/** A multi-purpose packet system to replace the old one 
  * @author CsokiCraft */
public class PacketInfluxSync implements IMessage{
	public PayloadInfluxSync payload;
	
	public PacketInfluxSync(PayloadInfluxSync data){
		payload = data;
	}
	
	public PacketInfluxSync(){
		this(new PayloadInfluxSync());
	}

	@Override
	public void fromBytes(ByteBuf buf){
		try{
			int len = buf.readInt();
			for(int i=0;i<len;i++){
				String name = readString(buf);
				switch(buf.readByte()){
				case 0:
					payload.put(name, buf.readInt());
					break;
				case 1:
					payload.put(name, buf.readByte());
					break;
				case 2:
					payload.put(name, buf.readDouble());
					break;
				case 3:
					payload.put(name, buf.readFloat());
					break;
				case 4:
					payload.put(name, readString(buf));
					break;
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf){
		try{
			buf.writeInt(payload.length());
			for(Map.Entry<String, Object> e:payload){
				String name = e.getKey();
				writeString(buf, name);
				Object obj = e.getValue();
				if(obj instanceof Integer){
					buf.writeByte(0);
					buf.writeInt((Integer) obj);
				}else if(obj instanceof Byte){
					buf.writeByte(1);
					buf.writeByte((Byte) obj);
				}else if(obj instanceof Double){
					buf.writeByte(2);
					buf.writeDouble((Double) obj);
				}else if(obj instanceof Float){
					buf.writeByte(3);
					buf.writeFloat((Float) obj);
				}else if(obj instanceof String){
					buf.writeByte(4);
					writeString(buf, (String) obj);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void writeString(ByteBuf buf, String s) throws IOException{
		StringInputStream in = new StringInputStream(s, 0);
		int len = s.length();
		buf.writeInt(len);
		buf.writeBytes(in, len);
	}
	
	public static String readString(ByteBuf buf) throws IOException{
		int len = buf.readInt();
		StringOutputStream out = new StringOutputStream();
		buf.readBytes(out, len);
		return out.toString();
	}
	
	public static class PayloadInfluxSync implements Iterable<Map.Entry<String, Object>>{
		protected Map<String, Object> data;
		
		public PayloadInfluxSync(){
			data = new HashMap<String, Object>();
		}
		
		public void put(String key, Object obj){
			data.put(key, obj);
		}
		
		public Object get(String key){
			return data.get(key);
		}
		
		public int length(){
			return data.size();
		}

		@Override
		public Iterator<Entry<String, Object>> iterator(){
			return data.entrySet().iterator();
		}
		
		@Override
		public String toString() {
			return data.toString();
		}
	}
}
