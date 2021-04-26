package com.xxkun.client.pojo.respone.message;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.pojo.respone.IResponseType;
import com.xxkun.client.common.MessageFactory;
import com.xxkun.client.pojo.respone.Response;
import com.xxkun.client.pojo.respone.ResponseMessage;
import com.xxkun.udptransfer.TransferPacket;

import java.nio.BufferUnderflowException;

public class UpdateTokenResponse extends ResponseMessage {

    private String token;
    public UpdateTokenResponse(Response response) throws MessageResolutionException {
        super(response);
    }

    public String getToken() {
        return token;
    }

    @Override
    public IResponseType getResponseType() {
        return MessageFactory.ServerResponseType.UPDATE_TOKEN;
    }

    @Override
    protected void decode(Response response) throws MessageResolutionException {
        TransferPacket.BodyBuffer buffer = response.getBodyBuffer();
        try {
            buffer.position(getHeadLength());
            int len = buffer.get();
            token = buffer.getString(len);
        } catch (BufferUnderflowException e) {
            throw new MessageResolutionException();
        }
    }
}
