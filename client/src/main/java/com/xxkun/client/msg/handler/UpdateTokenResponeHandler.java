package com.xxkun.client.msg.handler;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xxkun.client.common.PersonalInfo;
import com.xxkun.client.msg.bean.ServerResponse;
import com.xxkun.client.msg.bean.ServerResponse.BaseServerResponse;

import java.net.InetSocketAddress;

public class UpdateTokenResponeHandler extends ServerResponseHandler.NextHandler {

    @Override
    protected BaseServerResponse.ResponseType getType() {
        return BaseServerResponse.ResponseType.UPDATE_TOKEN;
    }

    @Override
    public boolean consume(Any body, InetSocketAddress socketAddress) {
        if (!body.is(ServerResponse.UpdateTokenResponse.class)) {
            return false;
        }
        ServerResponse.UpdateTokenResponse updateTokenResponse;
        try {
            updateTokenResponse = body.unpack(ServerResponse.UpdateTokenResponse.class);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return false;
        }
        String token = updateTokenResponse.getToken();
        PersonalInfo.INSTANCE.setToken(token);
        return true;
    }
}
