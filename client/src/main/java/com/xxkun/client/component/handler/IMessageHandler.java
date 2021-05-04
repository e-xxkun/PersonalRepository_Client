package com.xxkun.client.component.handler;

import com.xxkun.client.pojo.respone.BaseMessage;

public abstract class IMessageHandler {

    abstract void consume(BaseMessage message);
}
