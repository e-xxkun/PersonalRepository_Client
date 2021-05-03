package com.xxkun.client.pojo.request;

import com.xxkun.client.net.ProtocolType;

import java.net.InetSocketAddress;

public abstract class SRequest extends BaseServerRequest {

    public SRequest(InetSocketAddress socketAddress) {
        super(socketAddress);
    }

    @Override
    public ProtocolType getProtocolType() {
        return ProtocolType.NONE;
    }
}
