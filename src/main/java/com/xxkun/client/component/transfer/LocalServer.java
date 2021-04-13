package com.xxkun.client.component.transfer;

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


}
