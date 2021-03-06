package com.xxkun.client.pojo.request;

import com.xxkun.client.dao.Peer;
import com.xxkun.udptransfer.TransferPacket;

import java.net.InetSocketAddress;
import java.util.List;

public class PunchRequest extends Request {

    private List<Peer> userInfos;

    private int index = 0;

    private int bodyLength;

    public PunchRequest(InetSocketAddress socketAddress) {
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
    public Request next() {
        if (!hashNext()) {
            return null;
        }
        PunchRequest response = new PunchRequest(getSocketAddress());
        response.userInfos = userInfos;
        response.index = index;
        return super.next();
    }

    @Override
    public int getBodyLength() {
        return bodyLength;
    }

    @Override
    public IMessageType getMessageType() {
        return MessageType.GET.PUNCH;
    }

    @Override
    protected void overwrite(TransferPacket.BodyBuffer bodyBuffer) {
        bodyLength = Integer.BYTES;
        bodyBuffer.position(getHeadLength() + Integer.BYTES);
        int i = index;
        for (;i < userInfos.size() && bodyLength < bodyBuffer.limit();i ++) {
            Peer info = userInfos.get(i);
            bodyBuffer.putLong(info.getUserId());
            bodyLength += Long.BYTES;
        }
        bodyBuffer.position(getHeadLength());
        bodyBuffer.putInt(i - index);
        index = i;
    }
}
