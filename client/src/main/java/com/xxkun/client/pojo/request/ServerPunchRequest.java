package com.xxkun.client.pojo.request;

import com.xxkun.client.dao.Peer;
import com.xxkun.client.net.BodyBuffer;

import java.net.InetSocketAddress;
import java.util.List;

public class ServerPunchRequest extends STRRequest {

    private List<Peer> userInfos;

    private int index = 0;

    private int bodyLength;

    public ServerPunchRequest(InetSocketAddress socketAddress) {
        super(socketAddress);
    }

    public List<Peer> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<Peer> userInfos) {
        this.userInfos = userInfos;
    }

    @Override
    public boolean hashNext() {
        return index < userInfos.size();
    }

    @Override
    public BaseServerRequest next() {
        if (!hashNext()) {
            return null;
        }
        ServerPunchRequest response = new ServerPunchRequest(getSocketAddress());
        response.userInfos = userInfos;
        response.index = index;
        return response;
    }

    @Override
    public int getBodyLength() {
        return super.getBodyLength() + bodyLength;
    }

    @Override
    public ServerMessageType getMessageType() {
        return MessageType.GET.PUNCH;
    }

    @Override
    protected void overwrite(BodyBuffer bodyBuffer) {
        super.overwrite(bodyBuffer);
        bodyLength = Integer.BYTES;
        bodyBuffer.position(Integer.BYTES);
        int i = index;
        for (;i < userInfos.size();i ++) {
            if (bodyBuffer.remaining() < Long.BYTES) {
                break;
            }
            Peer info = userInfos.get(i);
            bodyBuffer.putLong(info.getUserId());
            bodyLength += Long.BYTES;
        }
        bodyBuffer.reset();
        bodyBuffer.putInt(i - index);
        index = i;
    }
}
