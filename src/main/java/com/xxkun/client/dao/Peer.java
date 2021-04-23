package com.xxkun.client.dao;

public class Peer {
    private long userId;
    private String name;
    private String url;

    public Peer(long userIp) {
        this.userId = userIp;
    }

    public long getUserId() {
        return userId;
    }

    public void setNameUrl(String nameUrl) {

    }
}
