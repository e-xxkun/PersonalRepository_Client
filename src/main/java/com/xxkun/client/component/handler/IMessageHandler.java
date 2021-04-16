package com.xxkun.client.component.handler;

import com.xxkun.client.pojo.respone.ResponseMessage;

public abstract class IMessageHandler {

    IMessageHandler[] handler = new IMessageHandler[1];

    abstract void consume(ResponseMessage message);
}
