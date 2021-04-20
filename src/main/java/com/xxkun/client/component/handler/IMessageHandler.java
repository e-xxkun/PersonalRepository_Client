package com.xxkun.client.component.handler;

import com.xxkun.client.pojo.respone.ResponseMessage;

public abstract class IMessageHandler {

    abstract void consume(ResponseMessage message);
}
