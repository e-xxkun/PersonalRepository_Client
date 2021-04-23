package com.xxkun.client.pojo.respone.responsemessage;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.pojo.respone.Response;
import com.xxkun.client.pojo.respone.ResponseMessage;
import com.xxkun.client.pojo.respone.ReplyResponseType;

public abstract class ReplyMessage extends ResponseMessage {
    public ReplyMessage(Response response) throws MessageResolutionException {
        super(response);
    }

    public abstract ReplyResponseType getType();
}
