package com.xxkun.client.pojo.respone;

import com.xxkun.client.net.BodyBuffer;

import java.net.InetSocketAddress;

public class BasePeerResponse extends BaseResponse{
    protected BasePeerResponse(BodyBuffer buffer, InetSocketAddress socketAddress) {
        super(buffer, socketAddress);
    }

    public static BasePeerResponse decodeFromPacket(BodyBuffer buffer, InetSocketAddress socketAddress) {
        return new BasePeerResponse(buffer, socketAddress);
    }
}
