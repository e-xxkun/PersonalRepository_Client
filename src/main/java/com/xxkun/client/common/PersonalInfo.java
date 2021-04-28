package com.xxkun.client.common;

import com.xxkun.client.pojo.NatType;
import com.xxkun.client.pojo.UserStatus;

public enum PersonalInfo {
    INSTANCE;

    public static final int TOKEN_LEN = 16;

    private long userId;

    private String name;

    private UserStatus status;

    private NatType natType;

    private volatile String token;

    private String url;

    private String identifier;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public NatType getNatType() {
        return natType;
    }

    public void setNatType(NatType natType) {
        this.natType = natType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
