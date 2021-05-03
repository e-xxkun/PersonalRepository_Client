package com.xxkun.client.pojo.request;

import com.xxkun.client.net.ProtocolType;

import java.net.InetSocketAddress;

public abstract class STRRequest extends BaseServerRequest {
    public STRRequest(InetSocketAddress socketAddress) {
        super(socketAddress);
    }

    @Override
    public ProtocolType getProtocolType() {
        return ProtocolType.TIMEOUT_RETRY;
    }
}
