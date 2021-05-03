package com.xxkun.client.net;

public abstract class ProtocolHandler {
    public static boolean receive(BasePacket packet, BaseServer server) {
        return true;
    }

    public static void sendBefor(BasePacket packet, BaseServer server) {
    }

    public static void sendAfter(BasePacket packet, BaseServer server) {
    }
}
