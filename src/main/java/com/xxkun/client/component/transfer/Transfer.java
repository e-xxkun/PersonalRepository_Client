package com.xxkun.client.component.transfer;

import com.xxkun.client.pojo.request.Request;
import com.xxkun.udptransfer.TransferPacket;

public interface Transfer {

    void send(Request request);

    TransferPacket receive();
}
