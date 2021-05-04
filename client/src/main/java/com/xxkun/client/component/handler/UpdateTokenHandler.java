package com.xxkun.client.component.handler;


import com.xxkun.client.common.PersonalInfo;
import com.xxkun.client.pojo.respone.BaseMessage;
import com.xxkun.client.pojo.respone.message.UpdateTokenResponse;

public class UpdateTokenHandler extends IMessageHandler {

    @Override
    public void consume(BaseMessage message) {
        String token = ((UpdateTokenResponse) message).getToken();
        PersonalInfo.INSTANCE.setToken(token);
    }
}
