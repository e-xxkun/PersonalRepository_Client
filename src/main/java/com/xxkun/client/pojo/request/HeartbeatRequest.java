package com.xxkun.client.pojo.request;

import com.xxkun.client.common.PersonalInfo;
import com.xxkun.client.component.exception.RequestConvertException;
import com.xxkun.udptransfer.TransferPacket;

import java.net.InetSocketAddress;

public class HeartbeatRequest extends Request {

    public HeartbeatRequest(InetSocketAddress socketAddress) {
        super(socketAddress);
    }

    @Override
    public int getBodyLength() {
        return 0;
    }

    @Override
    public IMessageType getMessageType() {
        return MessageType.GET.HEARTBEAT;
    }

    @Override
    protected void overwrite(TransferPacket.BodyBuffer bodyBuffer) {
    }

}
