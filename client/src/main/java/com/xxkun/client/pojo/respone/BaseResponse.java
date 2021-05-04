package com.xxkun.client.pojo.respone;

import com.xxkun.client.component.exception.ResponseResolutionException;
import com.xxkun.client.net.BodyBuffer;

import java.net.InetSocketAddress;

public class BaseResponse {

    private final InetSocketAddress socketAddress;
    private final BodyBuffer bodyBuffer;

    protected BaseResponse(BodyBuffer buffer, InetSocketAddress socketAddress) {
        this.bodyBuffer = buffer;
        this.socketAddress = socketAddress;
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public static BaseResponse decodeFromPacket(BodyBuffer buffer, InetSocketAddress socketAddress) throws ResponseResolutionException {
        return null;
    }

    public BodyBuffer getBodyBuffer() {
        return bodyBuffer;
    }
}
