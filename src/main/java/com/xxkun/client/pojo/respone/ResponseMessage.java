package com.xxkun.client.pojo.respone;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.udptransfer.TransferPacket;

public abstract class ResponseMessage {

    public static final int HEAD_LEN = Integer.BYTES;
    private final Response response;

    public ResponseMessage(Response response) throws MessageResolutionException {
        this.response = response;
        decode(response);
    }

    public abstract IResponseType getResponseType();
    protected abstract void decode(Response response) throws MessageResolutionException;

    public static ResponseMessage decodeFromResponse(Response response) {
        TransferPacket.BodyBuffer buffer = response.getBodyBuffer();
        buffer.position(response.getHeadLength());
        int type = buffer.getInt();
        IResponseType messageType = MessageFactory.fromTypeCode(ResponseType.SERVER, type);
        if (messageType == null)
            return null;
        ResponseMessage message = null;
        try {
            message = messageType.createResponseMessage(response);
        } catch (MessageResolutionException e) {
            e.printStackTrace();
        }
        return message;
    }

    public int getHeadLength() {
        return HEAD_LEN + response.getHeadLength();
    }

    public Response getResponse() {
        return response;
    }
}
