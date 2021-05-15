package com.xxkun.client.msg.handler;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xxkun.client.msg.bean.BasePacket;
import com.xxkun.client.msg.bean.ServerResponse.*;

import java.net.InetSocketAddress;

public class ServerResponseHandler extends BasePacketResponseHandler.NextHandler {
    @Override
    BasePacket.Packet.Type getType() {
        return BasePacket.Packet.Type.SERVER;
    }

    @Override
    public boolean consume(Any body, InetSocketAddress socketAddress) {
        if (!body.is(BaseServerResponse.class)) {
            return false;
        }
        BaseServerResponse baseServerResponse;
        try {
            baseServerResponse = body.unpack(BaseServerResponse.class);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return false;
        }
        if (baseServerResponse.getResponseType() == BaseServerResponse.ResponseType.UNRECOGNIZED) {
            return false;
        }
        return true;
    }
}
