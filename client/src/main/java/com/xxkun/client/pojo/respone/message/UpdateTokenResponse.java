package com.xxkun.client.pojo.respone.message;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.pojo.respone.IServerResponseType;
import com.xxkun.client.common.MessageFactory;
import com.xxkun.client.pojo.respone.BaseResponse;
import com.xxkun.client.pojo.respone.BaseMessage;
import com.xxkun.udptransfer.TransferPacket;

import java.nio.BufferUnderflowException;

public class UpdateTokenResponse extends BaseMessage {

    private String token;
    public UpdateTokenResponse(BaseResponse response) throws MessageResolutionException {
        super(response);
    }

    public String getToken() {
        return token;
    }

    @Override
    public IServerResponseType getResponseType() {
        return MessageFactory.ServerResponseType.UPDATE_TOKEN;
    }

    @Override
    protected void decode(BaseResponse response) throws MessageResolutionException {
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
