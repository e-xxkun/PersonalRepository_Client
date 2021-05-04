package com.xxkun.client.pojo.respone.message;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.pojo.respone.IServerResponseType;
import com.xxkun.client.common.MessageFactory;
import com.xxkun.client.pojo.respone.BaseResponse;
import com.xxkun.client.pojo.respone.BaseMessage;

public class UserExceptionResponse extends BaseMessage {

    public UserExceptionResponse(BaseResponse response) throws MessageResolutionException {
        super(response);
    }

    @Override
    public IServerResponseType getResponseType() {
        return MessageFactory.ServerResponseType.USER_EXCEPTION;
    }

    @Override
    protected void decode(BaseResponse response) throws MessageResolutionException {

    }

}
