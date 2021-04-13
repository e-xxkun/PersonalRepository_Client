package com.xxkun.client.pojo.respone;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.component.handler.IMessageHandler;

import java.util.HashMap;

public interface IReplyResponseType {

    HashMap<Integer, IReplyResponseType> typeMap = new HashMap<>();

    static IReplyResponseType fromTypeCode(int type) {
        return typeMap.get(type);
    }

    int getCode();

    ResponseMessage createReplyMessage(Response response) throws MessageResolutionException;

    IMessageHandler getReplyMessageHandler();
}
