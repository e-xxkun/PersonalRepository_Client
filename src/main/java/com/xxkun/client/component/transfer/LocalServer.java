package com.xxkun.client.component.transfer;

import com.xxkun.client.component.exception.RequestConvertException;
import com.xxkun.client.pojo.request.Request;
import com.xxkun.udptransfer.PacketPool;
import com.xxkun.udptransfer.TransferPacket;
import com.xxkun.udptransfer.TransferServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

public class LocalServer implements Transfer {

    private static volatile LocalServer INSTANCE;

    public static Transfer getTransfer() throws SocketException {
        if (INSTANCE == null) {
            synchronized (LocalServer.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalServer();
                }
            }
        }
        return INSTANCE;
    }

    private final TransferServer server;

    private LocalServer() throws SocketException {
        server = new TransferServer();
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
}
