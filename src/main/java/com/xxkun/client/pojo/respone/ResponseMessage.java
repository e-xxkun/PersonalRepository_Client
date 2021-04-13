package com.xxkun.client.pojo.respone;

import com.xxkun.client.component.exception.MessageResolutionException;

public abstract class ResponseMessage {

    private final Response response;

    public ResponseMessage(Response response) throws MessageResolutionException {
        this.response = response;
        decode(response);
    }

    protected abstract void decode(Response response) throws MessageResolutionException;

    public static ResponseMessage decodeFromResponse(Response response) {
        Response.BodyBuffer buffer = response.getBodyBuffer();
        int type = buffer.getInt();
        IReplyResponseType messageType = IReplyResponseType.fromTypeCode(type);
        if (messageType == null)
            return null;
        ResponseMessage message = null;
        try {
            message = messageType.createReplyMessage(response);
        } catch (MessageResolutionException e) {
            e.printStackTrace();
        }
        return message;
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseMessage message = (ResponseMessage) o;
        return response.equals(message.response);
    }

    @Override
    public int hashCode() {
        return response.hashCode();
    }
}
