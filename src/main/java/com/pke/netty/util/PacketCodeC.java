package com.pke.netty.util;

import com.pke.netty.pack.*;
import com.pke.netty.serializer.JSONSerializer;
import com.pke.netty.serializer.Serializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pke
 * @data 2022/4/28 17:48
 */
public class PacketCodeC {

    private PacketCodeC(){};
    public static PacketCodeC getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private static final PacketCodeC INSTANCE = new PacketCodeC();
    }

    private static final int MAGIC_NUMBER = 0XCAFEBABE;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE,MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(),serializer);
    }

    public ByteBuf encode(ByteBuf buffer,Packet packet){
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        buffer.writeInt(MAGIC_NUMBER);
        buffer.writeByte(packet.getVersion());
        buffer.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        buffer.writeByte(packet.getCommand());
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
        return buffer;
    }

    public Packet decode(ByteBuf buffer){
        buffer.skipBytes(4);
        buffer.skipBytes(1);
        byte serializerAlgorithm = buffer.readByte();
        byte command = buffer.readByte();
        int length = buffer.readInt();
        byte[] content = new byte[length];
        buffer.readBytes(content);
        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);
        if (requestType != null || serializer != null){
            return serializer.deserialize(requestType,content);
        }
        return null;
    }

    private Serializer getSerializer(byte serializerAlgorithm){
        return serializerMap.get(serializerAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command){
        return packetTypeMap.get(command);
    }


}
