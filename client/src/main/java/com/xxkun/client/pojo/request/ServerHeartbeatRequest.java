package com.xxkun.client.pojo.request;

import java.net.InetSocketAddress;

public class ServerHeartbeatRequest extends STRRequest {

    public ServerHeartbeatRequest(InetSocketAddress socketAddress) {
        super(socketAddress);
    }

    @Override
    public ServerMessageType getMessageType() {
        return MessageType.GET.HEARTBEAT;
    }
}
