package com.xxkun.client.pojo.request;

import com.xxkun.client.component.exception.RequestConvertException;
import com.xxkun.udptransfer.TransferPacket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public abstract class Request {

//    clientVersion|type  ->  int|int
    private static final int HEAD_LEN = 4 * Integer.BYTES + Long.BYTES;

    private final int clientVersion = 1;
    private final TransferPacket.BodyBuffer bodyBuffer;
    private InetSocketAddress socketAddress;
    private RequestType type;

    public Request(InetSocketAddress socketAddress) {
        setSocketAddress(socketAddress);
        bodyBuffer = new TransferPacket.BodyBuffer(length());
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    private int length() {
        return HEAD_LEN + getBodyLength();
    }

    public TransferPacket.BodyBuffer getBodyBuffer() {
        bodyBuffer.position(Integer.BYTES);
        overwrite(bodyBuffer);
        bodyBuffer.position(0);
        bodyBuffer.putInt(clientVersion);
        bodyBuffer.putInt(getType().getCode());
        return bodyBuffer;
    }

    public boolean hashNext() {
        return false;
    }

    public Request next() {
        return null;
    }

    public abstract int getBodyLength();

    public abstract RequestType getType();

    protected abstract void overwrite(TransferPacket.BodyBuffer bodyBuffer);

    public enum RequestType {
        ACK(0),
        PUT(1),
        GET(2);

        private final int cmdId;

        RequestType(int cmdId) {
            this.cmdId = cmdId;
        }

        public int getCode() {
            return cmdId;
        }
    }
}
