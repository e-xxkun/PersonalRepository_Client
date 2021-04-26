package com.xxkun.client.component.handler;


import com.xxkun.client.common.PersonalInfo;
import com.xxkun.client.pojo.respone.ResponseMessage;
import com.xxkun.client.pojo.respone.message.UpdateTokenResponse;

public class UpdateTokenHandler extends IMessageHandler {

    @Override
    public void consume(ResponseMessage message) {
        String token = ((UpdateTokenResponse) message).getToken();
        PersonalInfo.INSTANCE.setToken(token);
    }
}
