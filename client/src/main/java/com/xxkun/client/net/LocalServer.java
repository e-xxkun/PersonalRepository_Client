package com.xxkun.client.net;

import com.xxkun.client.common.BaseThread;
import com.xxkun.client.component.exception.ResponseResolutionException;
import com.xxkun.client.pojo.request.BaseRequest;
import com.xxkun.client.pojo.respone.BaseResponse;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum  LocalServer {
    INSTANCE;

    private static final BaseServer server;
    private static OnResponse onResponse;
    private static ThreadPoolExecutor responseThreadPool;
    private static ResponseListener responseListener;

    static  {
        BaseServer baseServer;
        try {
            baseServer = new BaseServer();
        } catch (SocketException e) {
            e.printStackTrace();
            baseServer = null;
        }
        server = baseServer;
        responseThreadPool = new ThreadPoolExecutor(6, 15, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        responseListener = new ResponseListener();
        responseListener.start();
    }

    public static void setOnResponse(OnResponse onResponse) {
        LocalServer.onResponse = onResponse;
    }

    public void send(BaseRequest request) {
        try {
            BasePacket packet = new BasePacket(request.getSocketAddress(), request.getBodyBuffer(), request.getType(), request.getProtocolType(), request.length() - BasePacket.HEAD_LEN);
            server.send(packet);
            while (request.hashNext()) {
                request = request.next();
                BasePacket packetNext = new BasePacket(request.getSocketAddress(), request.getBodyBuffer(), request.getType(), request.getProtocolType(), request.length() - BasePacket.HEAD_LEN);
                server.send(packetNext);
            }
            System.out.println("SEND: to " + request.getSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ResponseListener extends BaseThread {
        @Override
        public void run() {
            while (!stop) {
                System.out.println("LISTEN START");
                BasePacket packet;
                try {
                    packet = server.receive();
                    System.out.println("RECEIVE: from" + packet.getSocketAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                responseThreadPool.execute(() -> {
                    try {
                        BaseResponse response = BaseResponse.decodeFromPacket(packet.getBuffer(), packet.getSocketAddress());
                        onResponse.onResponse(packet.getSocketAddress(), response);
                    } catch (ResponseResolutionException e) {
                        System.out.println("Invalid message from " + packet.getSocketAddress() + ":" + new String(packet.convertToByteArray()));
                    }
                });
            }
        }
    }

    public void close() {
        responseListener.close();
    }

    interface OnResponse {
        void onResponse(SocketAddress from, BaseResponse response);
    }
}
