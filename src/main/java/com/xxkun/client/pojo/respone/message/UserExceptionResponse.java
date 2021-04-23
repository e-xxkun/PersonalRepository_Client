package com.xxkun.client.pojo.respone.message;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.pojo.respone.IResponseType;
import com.xxkun.client.pojo.respone.MessageFactory;
import com.xxkun.client.pojo.respone.Response;
import com.xxkun.client.pojo.respone.ResponseMessage;

public class UserExceptionResponse extends ResponseMessage {

    public UserExceptionResponse(Response response) throws MessageResolutionException {
        super(response);
    }

    @Override
    public IResponseType getResponseType() {
        return MessageFactory.ServerResponseType.USER_EXCEPTION;
    }

    @Override
    protected void decode(Response response) throws MessageResolutionException {

    }

}
