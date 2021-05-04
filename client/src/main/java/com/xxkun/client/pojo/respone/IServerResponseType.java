package com.xxkun.client.pojo.respone;

import com.xxkun.client.component.exception.MessageResolutionException;

public interface IServerResponseType extends IInnerMessageType {

    int getCode();

    BaseMessage createResponseMessage(BaseResponse response) throws MessageResolutionException;
}
