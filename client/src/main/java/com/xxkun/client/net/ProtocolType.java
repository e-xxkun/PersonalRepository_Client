package com.xxkun.client.net;

public enum ProtocolType {
    NONE(0),
    TIMEOUT_RETRY(1);

    private final int code;
    ProtocolType(int code) {
        this.code = code;
    }

    byte getCode() {
        return (byte) code;
    }

    static ProtocolType fromCode(int code) {
        if (code >= 0 && code < ProtocolType.values().length) {
            return ProtocolType.values()[code];
        }
        return null;
    }

    public int getHeadLength() {
        return 0;
    }
}
