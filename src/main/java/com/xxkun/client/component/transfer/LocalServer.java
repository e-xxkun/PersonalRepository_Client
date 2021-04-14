package com.xxkun.client.component.transfer;

import com.xxkun.client.component.exception.RequestConvertException;
import com.xxkun.client.pojo.request.Request;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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

    private final DatagramSocket server;

    private LocalServer() throws SocketException {
        server = new DatagramSocket();
    }


    @Override
    public void send(Request request) {
        try {
            byte[] data = request.convertToByteArray();
            DatagramPacket packet = new DatagramPacket(data, 0, data.length, request.getSocketAddress());
            server.send(packet);
            System.out.println("SEND: to " + request.getSocketAddress());
        } catch (RequestConvertException | IOException e) {
            e.printStackTrace();
        }
    }
}
