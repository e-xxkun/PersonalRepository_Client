package com.xxkun.client.net;

import com.google.protobuf.Any;
import com.xxkun.client.common.BaseThread;
import com.xxkun.client.msg.bean.BasePacket;
import com.xxkun.client.msg.handler.BasePacketResponseHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum  LocalServer {
    INSTANCE;

    private static final BaseServer server;
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

    public void send(Any body, InetSocketAddress socketAddress) {
        try {
            server.send(body, socketAddress);
            System.out.println("SEND: to " + socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ResponseListener extends BaseThread {
        @Override
        public void run() {
            while (!stop) {
                System.out.println("LISTEN START");
                BaseServer.BasePacketExtend packetExtend;
                try {
                    packetExtend = server.receive();
                    System.out.println("RECEIVE: from" + packetExtend.socketAddress);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                responseThreadPool.execute(() -> {
                    Any any = packetExtend.packet.getBody();
                    BasePacketResponseHandler.NextHandler nextHandler = BasePacketResponseHandler.getNextHandler(packetExtend.packet.getType());
                    if (!nextHandler.consume(any, packetExtend.socketAddress)) {
                        System.out.println("FAIL: " + packetExtend.socketAddress);
                    }
                });
            }
        }
    }

    public void close() {
        responseListener.close();
    }
}
