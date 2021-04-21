package com.xxkun.client.pojo.request;

import com.xxkun.client.common.PersonalInfo;
import com.xxkun.udptransfer.TransferPacket;

import java.net.InetSocketAddress;

public abstract class Request {

    private static final int TOKEN_LEN = 16;
    //    client_version|request_type|token|message_type  ->  int|int|char[16]|int
    private static final int HEAD_LEN = 2 * Integer.BYTES + TOKEN_LEN * Character.BYTES;

    private final int clientVersion = 1;
    private final TransferPacket.BodyBuffer bodyBuffer;
    private InetSocketAddress socketAddress;
    private String token;

    public Request(InetSocketAddress socketAddress) {
        bodyBuffer = new TransferPacket.BodyBuffer(length());
        setSocketAddress(socketAddress);
        setToken(PersonalInfo.INSTANCE.getToken());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public int getHeadLength() {
        return HEAD_LEN;
    }

    public TransferPacket.BodyBuffer getBodyBuffer() {
        bodyBuffer.position(Integer.BYTES);
        overwrite(bodyBuffer);
        bodyBuffer.position(0);
        bodyBuffer.putInt(clientVersion);
        bodyBuffer.putInt(getMessageType().getRequestType().getCode());
        bodyBuffer.putString(token);
        bodyBuffer.putInt(getMessageType().getCode());
        return bodyBuffer;
    }

    public boolean hashNext() {
        return false;
    }

    public Request next() {
        return null;
    }

    public abstract int getBodyLength();
    abstract IMessageType getMessageType();
    protected abstract void overwrite(TransferPacket.BodyBuffer bodyBuffer);

    public enum RequestType {
        PUT(1),
        GET(0);

        private final int code;

        RequestType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
