package com.xxkun.client.pojo.request;

import com.xxkun.client.common.PersonalInfo;
import com.xxkun.client.component.exception.RequestConvertException;

import java.net.InetSocketAddress;

public class HeartbeatRequest extends Request implements Message {

    private String token;

    public HeartbeatRequest(InetSocketAddress socketAddress) {
        super(socketAddress);
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int getBodyLength() {
        return Integer.BYTES + token.length() * Character.BYTES;
    }

    @Override
    public RequestType getType() {
        return RequestType.GET;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.HEARTBEAT;
    }

    @Override
    protected void overwriteToByteArray(BodyBuffer bodyBuffer) throws RequestConvertException {
        bodyBuffer.position(0);
        bodyBuffer.writeInt(getMessageType().getCode());
        if (token.length() < PersonalInfo.TOKEN_LEN) {
            throw new RequestConvertException();
        }
        bodyBuffer.writeString(token, PersonalInfo.TOKEN_LEN);
    }

}
