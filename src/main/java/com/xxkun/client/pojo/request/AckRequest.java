package com.xxkun.client.pojo.request;

import java.net.InetSocketAddress;

public class AckRequest extends Request {

    public AckRequest(InetSocketAddress socketAddress, long sequence) {
        super(socketAddress);
        setSequence(sequence);
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
    protected void overwriteToByteArray(BodyBuffer bodyBuffer) {
    }
}
