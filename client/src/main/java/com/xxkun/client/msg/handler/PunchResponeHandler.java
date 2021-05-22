package com.xxkun.client.msg.handler;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xxkun.client.msg.bean.ServerResponse;
import com.xxkun.client.msg.bean.ServerResponse.BaseServerResponse;

import java.net.InetSocketAddress;
import java.util.List;

public class PunchResponeHandler extends ServerResponseHandler.NextHandler {

    @Override
    protected BaseServerResponse.ResponseType getType() {
        return BaseServerResponse.ResponseType.PUNCH;
    }

    @Override
    public boolean consume(Any body, InetSocketAddress socketAddress) {
        if (!body.is(ServerResponse.PunchResponse.class)) {
            return false;
        }
        ServerResponse.PunchResponse punchResponse;
        try {
            punchResponse = body.unpack(ServerResponse.PunchResponse.class);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return false;
        }
        List<ServerResponse.PunchResponse.PeerInfo> peerInfos = punchResponse.getPeerInfoList();
        return true;
    }
}
