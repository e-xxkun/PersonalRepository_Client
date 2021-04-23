package com.xxkun.client.pojo.respone;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.component.handler.IMessageHandler;

public interface IResponseType {

    int getCode();

    ResponseMessage createResponseMessage(Response response) throws MessageResolutionException;
}
