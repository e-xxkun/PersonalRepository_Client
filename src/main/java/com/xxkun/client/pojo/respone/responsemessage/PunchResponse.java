package com.xxkun.client.pojo.respone.responsemessage;

import com.xxkun.client.component.exception.MessageResolutionException;
import com.xxkun.client.dao.Peer;
import com.xxkun.client.pojo.respone.Response;
import com.xxkun.client.pojo.respone.ResponseMessage;

import java.nio.BufferUnderflowException;

public class PunchResponse extends ResponseMessage {

    private String token;

    private Peer[] peers;

    public PunchResponse(Response response) throws MessageResolutionException {
        super(response);
    }

    public Peer[] getUserInfos() {
        return peers;
    }

    @Override
    protected void decode(Response response) throws MessageResolutionException {
        Response.BodyBuffer buffer = response.getBodyBuffer();
        try {
            // skip the message type byte
            buffer.skip(Integer.BYTES);
            int count = buffer.getInt();
            peers = new Peer[count];

            // count|user_id (int)|(long) 2|0~9223372036854775807
            for (int i = 0;i < count;i ++) {
                long userIp = buffer.getLong();
                peers[i] = new Peer(userIp);
            }
        } catch (BufferUnderflowException | IllegalArgumentException e) {
            throw new MessageResolutionException();
        }
    }
}
