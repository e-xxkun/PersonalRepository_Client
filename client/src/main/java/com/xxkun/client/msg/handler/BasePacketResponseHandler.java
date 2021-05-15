package com.xxkun.client.msg.handler;

import com.google.protobuf.Any;
import com.xxkun.client.msg.bean.BasePacket.Packet;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public enum BasePacketResponseHandler {
    INSTANCE;

    private static Map<Packet.Type, NextHandler> map = new HashMap<>();

    public static NextHandler getNextHandler(Packet.Type type) {
        return map.get(type);
    }
    
    public boolean check(Packet packet) {
        return true;
    }

    public abstract static class NextHandler {
        NextHandler() {
            map.put(getType(), this);
        }
        abstract Packet.Type getType();
        public abstract boolean consume(Any body, InetSocketAddress socketAddress);
    }
}
