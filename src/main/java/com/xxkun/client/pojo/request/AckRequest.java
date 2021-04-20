package com.xxkun.client.pojo.request;

import com.xxkun.udptransfer.TransferPacket;

import java.net.InetSocketAddress;

public class AckRequest extends Request {

    public AckRequest(InetSocketAddress socketAddress) {
        super(socketAddress);
    }

    @Override
    public int getBodyLength() {
        return 0;
    }

    @Override
    public RequestType getType() {
        return RequestType.ACK;
    }

    @Override
    protected void overwrite(TransferPacket.BodyBuffer bodyBuffer) {
    }
}
