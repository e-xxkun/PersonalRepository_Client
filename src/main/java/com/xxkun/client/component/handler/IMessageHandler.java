package com.xxkun.client.component.handler;

import com.xxkun.client.pojo.respone.ResponseMessage;

public interface IMessageHandler {

    void consume(ResponseMessage message );
}
