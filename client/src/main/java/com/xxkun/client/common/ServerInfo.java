package com.xxkun.client.common;

import java.net.InetSocketAddress;

public enum ServerInfo {
    SERVER_1,
    SERVER_2;

    private long serverId;
    private InetSocketAddress socketAddress;

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }
    public void setSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }
    public long getServerId() {
        return serverId;
    }
    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }
}
