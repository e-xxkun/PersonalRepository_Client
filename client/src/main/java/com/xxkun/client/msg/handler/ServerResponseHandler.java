package com.xxkun.client.msg.handler;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xxkun.client.msg.bean.BasePacket;
import com.xxkun.client.msg.bean.ServerResponse;
import com.xxkun.client.msg.bean.ServerResponse.*;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

class ServerResponseHandler extends BasePacketResponseHandler.NextHandler {

    private static Map<ServerResponse.BaseServerResponse.ResponseType, ServerResponseHandler.NextHandler> map = new HashMap<>();

    protected NextHandler getNextHandler(ServerResponse.BaseServerResponse.ResponseType type) {
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
        BaseServerResponse.ResponseType type = baseServerResponse.getResponseType();
        if (type == BaseServerResponse.ResponseType.UNRECOGNIZED) {
            return false;
        }
        return getNextHandler(type).consume(baseServerResponse.getBody(), socketAddress);
    }

    public abstract static class NextHandler {
        NextHandler() {
            map.put(getType(), this);
        }
        protected abstract BaseServerResponse.ResponseType getType();
        public abstract boolean consume(Any body, InetSocketAddress socketAddress);
    }
}
