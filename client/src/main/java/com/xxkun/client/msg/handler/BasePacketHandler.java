package com.xxkun.client.msg.handler;

import com.google.protobuf.Any;
import com.xxkun.client.msg.bean.BasePacket;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public enum  BasePacketHandler {
    INSTANCE;

    private static Map<BasePacket.Packet.Type, NextHandler> map = new HashMap<>();

    public static NextHandler getNextHandler(BasePacket.Packet.Type type) {
        return map.get(type);
    }
    
    public boolean check(BasePacket.Packet packet) {
        return true;
    }

    public abstract static class NextHandler {

        NextHandler() {
            map.put(getType(), this);
        }
        abstract BasePacket.Packet.Type getType();
        public abstract boolean consume(Any body, InetSocketAddress socketAddress);
    }
}
