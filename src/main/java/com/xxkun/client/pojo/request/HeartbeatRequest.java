package com.xxkun.client.pojo.request;

import com.xxkun.client.component.exception.RequestConvertException;

import java.net.InetSocketAddress;

public class HeartbeatRequest extends Request implements Message {

    public HeartbeatRequest(InetSocketAddress socketAddress) {
        super(socketAddress);
    }

    @Override
    public int getBodyLength() {
        return 0;
    }

    @Override
    public RequestType getType() {
        return RequestType.PUT;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.HEARTBEAT;
    }

    @Override
    protected void overwriteToByteArray(BodyBuffer bodyBuffer) {
        bodyBuffer.writeInt(getMessageType().getCode());
    }

}
