package com.xxkun.client.component.transfer;

import com.xxkun.client.pojo.request.Request;
import com.xxkun.udptransfer.TransferPacket;
import com.xxkun.udptransfer.TransferServer;

import java.io.IOException;
import java.net.SocketException;

public enum  LocalServer implements Transfer {
    INSTANCE;

    private static final TransferServer server;

    static  {
        TransferServer transferServer;
        try {
            transferServer = new TransferServer();
        } catch (SocketException e) {
            e.printStackTrace();
            transferServer = null;
        }
        server = transferServer;
    }

    @Override
    public void send(Request request) {
        try {
            TransferPacket packet = new TransferPacket(request.getBodyBuffer(), request.getSocketAddress());
            server.send(packet);
            System.out.println("SEND: to " + request.getSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TransferPacket receive() {
        return null;
    }
}
