package com.xxkun.client.component.transfer;

import com.xxkun.client.common.BaseThread;
import com.xxkun.client.component.exception.ResponseResolutionException;
import com.xxkun.client.pojo.request.Request;
import com.xxkun.client.pojo.respone.Response;
import com.xxkun.udptransfer.TransferPacket;
import com.xxkun.udptransfer.TransferServer;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum LocalServer implements Transfer {
    INSTANCE;

    private static final TransferServer server;

    private static OnResponse onResponse;
    private static ThreadPoolExecutor responseThreadPool;
    private static ResponseListener responseListener;

    static  {
        TransferServer transferServer;
        try {
            transferServer = new TransferServer();
        } catch (SocketException e) {
            e.printStackTrace();
            transferServer = null;
        }
        server = transferServer;
        responseThreadPool = new ThreadPoolExecutor(6, 15, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        responseListener = new ResponseListener();
        responseListener.start();
    }

    public static void setOnResponse(OnResponse onResponse) {
        LocalServer.onResponse = onResponse;
    }

    @Override
    public void send(Request request) {
        try {
            TransferPacket packet = new TransferPacket(request.getBodyBuffer(), request.getSocketAddress());
            server.send(packet);
            System.out.println("SEND: " + request.getSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        responseListener.close();
    }

    static class ResponseListener extends BaseThread {
        @Override
        public void run() {
            while (!stop) {
                System.out.println("LISTEN START");
                TransferPacket packet;
                try {
                    packet = server.receive();
                    System.out.println("RECEIVE: from" + packet.getSocketAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                responseThreadPool.execute(() -> {
                    try {
                        Response response = Response.decodeFromPacket(packet.getBuffer(), packet.getSocketAddress());
                        onResponse.onResponse(packet.getSocketAddress(), response);
                    } catch (ResponseResolutionException e) {
                        System.out.println("Invalid message from " + packet.getSocketAddress() + ":" + new String(packet.convertToByteArray()));
                    }
                });
            }
        }
    }
}
