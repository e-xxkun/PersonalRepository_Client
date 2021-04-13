package com.xxkun.client.pojo.respone;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.component.handler.IMessageHandler;

public enum ReplyResponseType implements IReplyResponseType {

    UNKNOWN(0),
    SUCCESS(1),
    LOGIN_EXPIRE(2),

    USER_NOT_EXIST(3),
    USER_OFFLINE(4),

    UPDATE_TOKEN(5);

    static {
        for (ReplyResponseType type : ReplyResponseType.values()) {
            typeMap.put(type.code, type);
        }
    }

    final int code;

    ReplyResponseType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public ResponseMessage createReplyMessage(Response response) throws MessageResolutionException {
        return null;
    }

    @Override
    public IMessageHandler getReplyMessageHandler() {
        return null;
    }
}
