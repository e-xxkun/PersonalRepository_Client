package com.xxkun.client.pojo.request;

public enum MessageType {
    PUNCH(0),
    HEARTBEAT(1);


    private final int code;

    MessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
