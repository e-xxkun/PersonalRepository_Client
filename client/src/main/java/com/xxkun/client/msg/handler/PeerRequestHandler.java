package com.xxkun.client.msg.handler;

import com.google.protobuf.Any;
import com.xxkun.client.msg.bean.BasePacket;

import java.net.InetSocketAddress;

public class PeerRequestHandler extends BasePacketHandler.NextHandler {
    @Override
    BasePacket.Packet.Type getType() {
        return BasePacket.Packet.Type.SERVER;
    }

    @Override
    public boolean consume(Any body, InetSocketAddress socketAddress) {
        return false;
    }
}
