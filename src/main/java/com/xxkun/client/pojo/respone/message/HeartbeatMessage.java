package com.xxkun.client.pojo.respone.message;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.pojo.respone.IResponseType;
import com.xxkun.client.pojo.respone.MessageFactory;
import com.xxkun.client.pojo.respone.Response;
import com.xxkun.client.pojo.respone.ResponseMessage;

import java.nio.BufferUnderflowException;

public class HeartbeatMessage extends ResponseMessage {

    private static final int MESSAGE_TOKEN_LEN = 8;

    public HeartbeatMessage(Response response) throws MessageResolutionException {
        super(response);
    }

    @Override
    public IResponseType getResponseType() {
        return MessageFactory.ResponseType.HEARTBEAT;
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
