package com.xxkun.client.common;

import com.xxkun.client.pojo.NatType;
import com.xxkun.client.pojo.UserStatus;

import java.net.InetSocketAddress;

public enum ServerInfo {
    SERVER_1(-87994, "", 8874),
    SERVER_2(-87995, "", 8931);

    private final long serverId;
    private final InetSocketAddress socketAddress;

    ServerInfo(long serverId, String url, int port) {
        this.serverId = serverId;
        socketAddress = new InetSocketAddress(url, port);
    }
    public long getServerId() {
        return serverId;
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }
}
