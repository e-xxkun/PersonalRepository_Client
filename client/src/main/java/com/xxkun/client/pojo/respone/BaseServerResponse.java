package com.xxkun.client.pojo.respone;

import com.xxkun.client.component.exception.ResponseResolutionException;
import com.xxkun.client.net.BodyBuffer;

import java.net.InetSocketAddress;

public class BaseServerResponse extends BaseResponse{

    protected BaseServerResponse(BodyBuffer buffer, InetSocketAddress socketAddress) {
        super(buffer, socketAddress);
    }

    public static BaseServerResponse decodeFromPacket(BodyBuffer buffer, InetSocketAddress socketAddress) {
        return new BaseServerResponse(buffer, socketAddress);
    }
}
