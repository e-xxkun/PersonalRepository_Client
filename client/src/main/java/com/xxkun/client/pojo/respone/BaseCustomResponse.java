package com.xxkun.client.pojo.respone;

import com.xxkun.client.net.BodyBuffer;

import java.net.InetSocketAddress;

public class BaseCustomResponse extends BaseResponse {
    public BaseCustomResponse(BodyBuffer buffer, InetSocketAddress socketAddress) {
        super(buffer, socketAddress);
    }

    public static BaseCustomResponse decodeFromPacket(BodyBuffer buffer, InetSocketAddress socketAddress) {
        return new BaseCustomResponse(buffer, socketAddress);
    }
}
