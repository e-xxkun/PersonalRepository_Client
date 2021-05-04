package com.xxkun.client.pojo.respone;

import com.xxkun.client.common.MessageFactory;
import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.net.BodyBuffer;

public abstract class BaseMessage {

    public static final int HEAD_LEN = Integer.BYTES;
    private final BaseResponse response;

    public BaseMessage(BaseResponse response) throws MessageResolutionException {
        this.response = response;
        decode(response);
    }

    public abstract IServerResponseType getResponseType();
    protected abstract void decode(BaseResponse response) throws MessageResolutionException;

    public static BaseMessage decodeFromResponse(BaseResponse response) {
        BodyBuffer buffer = response.getBodyBuffer();
        int type = buffer.getInt();
        IServerResponseType messageType = MessageFactory.fromTypeCode(ResponseType.SERVER, type);
        if (messageType == null)
            return null;
        BaseMessage message = null;
        try {
            message = messageType.createResponseMessage(response);
        } catch (MessageResolutionException e) {
            e.printStackTrace();
        }
        return message;
    }

    public int getHeadLength() {
        return HEAD_LEN ;
    }

    public BaseResponse getResponse() {
        return response;
    }
}
