package com.xxkun.client.msg.handler;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xxkun.client.msg.bean.BasePacket;
import com.xxkun.client.msg.bean.ServerResponse;
import com.xxkun.client.msg.bean.ServerResponse.*;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ServerResponseHandler extends BasePacketResponseHandler.NextHandler {

    private static Map<ServerResponse.BaseServerResponse.ResponseType, ServerResponseHandler.NextHandler> map = new HashMap<>();

    public static NextHandler getNextHandler(ServerResponse.BaseServerResponse.ResponseType type) {
        return map.get(type);
    }

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
//        return getNextHandler(baseServerResponse.getResponseType()).consume();
        return true;
    }

    public abstract static class NextHandler {
        NextHandler() {
            map.put(getType(), this);
        }
        abstract BaseServerResponse.ResponseType getType();
        public abstract boolean consume(Any body, InetSocketAddress socketAddress);
    }
}
