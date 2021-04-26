package com.xxkun.client.pojo.respone.message;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.dao.Peer;
import com.xxkun.client.pojo.respone.IResponseType;
import com.xxkun.client.common.MessageFactory;
import com.xxkun.client.pojo.respone.Response;
import com.xxkun.client.pojo.respone.ResponseMessage;
import com.xxkun.udptransfer.TransferPacket;

import java.nio.BufferUnderflowException;

public class PunchResponse extends ResponseMessage {

    private String token;

    private Peer[] peers;

    public PunchResponse(Response response) throws MessageResolutionException {
        super(response);
    }

    @Override
    public IResponseType getResponseType() {
        return MessageFactory.ServerResponseType.PUNCH;
    }

    public Peer[] getUserInfos() {
        return peers;
    }

    @Override
    protected void decode(Response response) throws MessageResolutionException {
        TransferPacket.BodyBuffer buffer = response.getBodyBuffer();
//        count|[user_id|len|name_url]   int|[long|byte|char[len]]
        try {
            buffer.position(getHeadLength());
            int count = buffer.getInt();
            peers = new Peer[count];
            for (int i = 0;i < count;i ++) {
                long userIp = buffer.getLong();
                peers[i] = new Peer(userIp);
                byte len = buffer.get();
                String nameUrl = buffer.getString(len);
                peers[i].setNameUrl(nameUrl);
            }
        } catch (BufferUnderflowException | IllegalArgumentException e) {
            throw new MessageResolutionException();
        }
    }
}
