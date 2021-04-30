package com.xxkun.client.component.transfer;

import com.xxkun.client.pojo.request.Request;
import com.xxkun.client.pojo.respone.Response;

import java.net.SocketAddress;

public interface Transfer {

    void send(Request request);

    interface OnResponse {
        void onResponse(SocketAddress from, Response response);
    }

    void close();
}
