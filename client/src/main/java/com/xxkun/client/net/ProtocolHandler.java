package com.xxkun.client.net;

import com.xxkun.client.msg.bean.BasePacket;

import java.net.InetSocketAddress;

public abstract class ProtocolHandler {
    public static boolean receive(BasePacket.Packet packet, InetSocketAddress socketAddress, BaseServer server) {
        return true;
    }

    public static void sendBefor(BasePacket.Packet packet, InetSocketAddress socketAddress, BaseServer server) {
    }

    public static void sendAfter(BasePacket.Packet packet, InetSocketAddress socketAddress, BaseServer server) {
    }
}
