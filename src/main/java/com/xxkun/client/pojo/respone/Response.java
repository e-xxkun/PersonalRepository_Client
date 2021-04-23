package com.xxkun.client.pojo.respone;

import com.xxkun.client.component.exception.ResponseResolutionException;
import com.xxkun.udptransfer.TransferPacket;

import java.net.InetSocketAddress;
import java.util.HashMap;

public final class Response {

    private static final int HEAD_LEN = 0;
    private final InetSocketAddress socketAddress;
    private final TransferPacket.BodyBuffer bodyBuffer;

    private Response(TransferPacket.BodyBuffer buffer, InetSocketAddress socketAddress) {
        this.bodyBuffer = buffer;
        this.socketAddress = socketAddress;
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public static Response decodeFromPacket(TransferPacket.BodyBuffer buffer, InetSocketAddress socketAddress) throws ResponseResolutionException {
        if (buffer.getBodyLength() < HEAD_LEN)
            throw new ResponseResolutionException();
        return new Response(buffer, socketAddress);
    }

    public TransferPacket.BodyBuffer getBodyBuffer() {
        return bodyBuffer;
    }

    public int getHeadLength() {
        return HEAD_LEN;
    }
}
