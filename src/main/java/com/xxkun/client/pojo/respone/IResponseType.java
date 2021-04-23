package com.xxkun.client.pojo.respone;

import com.xxkun.client.component.exception.MessageResolutionException;

public interface IResponseType extends IInnerMessageType {

    int getCode();

    ResponseMessage createResponseMessage(Response response) throws MessageResolutionException;
}
