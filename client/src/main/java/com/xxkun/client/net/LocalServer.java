package com.xxkun.client.net;

import com.xxkun.client.common.BaseThread;
import com.xxkun.client.pojo.request.BaseRequest;
import com.xxkun.client.pojo.respone.BasePeerResponse;
import com.xxkun.client.pojo.respone.BaseServerResponse;
import com.xxkun.client.pojo.respone.BaseCustomResponse;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum  LocalServer {
    INSTANCE;

    private static final BaseServer server;
    private static OnResponseReceive onResponseReceive;
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

    public static void setOnResponseReceive(OnResponseReceive onResponseReceive) {
        LocalServer.onResponseReceive = onResponseReceive;
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
                if (onResponseReceive == null) {
                    continue;
                }
                responseThreadPool.execute(() -> {
                    if (packet.getType().isCustom()) {
                        BaseCustomResponse response = BaseCustomResponse.decodeFromPacket(packet.getBuffer(), packet.getSocketAddress());
                        if (response != null) {
                            onResponseReceive.onCustomResponseReceive(response);
                        } else {
                            onResponseReceive.onCustomResponseResolutionException(response);
                        }
                    } else if (packet.getType().isPeer()) {
                        BasePeerResponse response = BasePeerResponse.decodeFromPacket(packet.getBuffer(), packet.getSocketAddress());
                        if (response != null) {
                            onResponseReceive.onPeerResponseReceive(response);
                        }
                    } else if (packet.getType().isServer()) {
                        BaseServerResponse response = BaseServerResponse.decodeFromPacket(packet.getBuffer(), packet.getSocketAddress());
                        if (response != null) {
                            onResponseReceive.onServerResponseReceive(response);
                        }
                    }
                });
            }
        }
    }

    public void close() {
        responseListener.close();
    }

    interface OnResponseReceive {

        void onCustomResponseReceive(BaseCustomResponse response);

        void onPeerResponseReceive(BasePeerResponse response);

        void onServerResponseReceive(BaseServerResponse response);

        void onCustomResponseResolutionException(BaseCustomResponse response);
    }
}
