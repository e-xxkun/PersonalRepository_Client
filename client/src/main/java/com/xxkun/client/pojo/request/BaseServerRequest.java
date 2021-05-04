package com.xxkun.client.pojo.request;

import com.xxkun.client.common.PersonalInfo;
import com.xxkun.client.net.BasePacket;
import com.xxkun.client.net.BodyBuffer;

import java.net.InetSocketAddress;

public abstract class BaseServerRequest extends BaseRequest {

    private static final int TOKEN_LEN = 16;
    //    client_version|request_type|token|message_type  ->  int|int|char[16]|int
    private static final int HEAD_LEN = 3 * Integer.BYTES + TOKEN_LEN * Character.BYTES;

    private final int clientVersion = 1;
    private String token;

    public BaseServerRequest(InetSocketAddress socketAddress) {
        super(socketAddress);
        setSocketAddress(socketAddress);
        setToken(PersonalInfo.INSTANCE.getToken());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getHeadLength() {
        return super.getHeadLength() + HEAD_LEN;
    }

    @Override
    int getBodyLength() {
        return HEAD_LEN;
    }

    @Override
    protected void overwrite(BodyBuffer bodyBuffer) {
        bodyBuffer.putInt(clientVersion);
        bodyBuffer.putInt(getMessageType().getRequestType().getCode());
        bodyBuffer.putString(token);
        bodyBuffer.putInt(getMessageType().getCode());
        bodyBuffer.setMarkPosition(getHeadLength());
    }

    @Override
    public BasePacket.Type getType() {
        return BasePacket.Type.SERVER;
    }

    protected abstract ServerMessageType getMessageType();

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
