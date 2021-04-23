package com.xxkun.client.pojo.respone.responsemessage;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.pojo.respone.Response;
import com.xxkun.client.pojo.respone.ReplyResponseType;

import java.nio.BufferUnderflowException;

public class HeartbeatMessage extends ReplyMessage {

    private static final int MESSAGE_TOKEN_LEN = 8;
    private String token;

    public HeartbeatMessage(Response response) throws MessageResolutionException {
        super(response);
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public ReplyResponseType getType() {
        return ReplyResponseType.UPDATE_TOKEN;
    }

    @Override
    protected void decode(Response response) throws MessageResolutionException {
        Response.BodyBuffer buffer = response.getBodyBuffer();
        try {
            // skip the message type byte
            buffer.skip(Integer.BYTES);
            token = buffer.getString(MESSAGE_TOKEN_LEN);
        } catch (BufferUnderflowException | IllegalArgumentException e) {
            throw new MessageResolutionException();
        }
    }
}
