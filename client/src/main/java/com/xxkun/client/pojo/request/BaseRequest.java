package com.xxkun.client.pojo.request;

import com.xxkun.client.net.BasePacket;
import com.xxkun.client.net.BodyBuffer;
import com.xxkun.client.net.ProtocolType;

import java.net.InetSocketAddress;

public abstract class BaseRequest {
    private final BodyBuffer bodyBuffer;
    private InetSocketAddress socketAddress;

    public BaseRequest(InetSocketAddress socketAddress) {
        bodyBuffer = new BodyBuffer(length());
        setSocketAddress(socketAddress);
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public int length() {
        return getHeadLength() + getBodyLength();
    }

    public int getHeadLength() {
        return getProtocolType().getHeadLength();
    }

    private void write() {
        bodyBuffer.setMarkPosition(getHeadLength());
        overwrite(bodyBuffer);
    }

    public boolean hashNext() {
        return false;
    }

    public BaseRequest next() {
        return null;
    }

    public BodyBuffer getBodyBuffer() {
        write();
        return bodyBuffer;
    }

    abstract int getBodyLength();
    protected abstract void overwrite(BodyBuffer bodyBuffer);
    public abstract BasePacket.Type getType();
    public abstract ProtocolType getProtocolType();

}
