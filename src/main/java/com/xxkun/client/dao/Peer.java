package com.xxkun.client.dao;

public class Peer {
    private long userId;

    public Peer(long userIp) {
        this.userId = userIp;
    }

    public long getUserId() {
        return userId;
    }
}
