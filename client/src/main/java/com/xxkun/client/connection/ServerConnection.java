package com.xxkun.client.connection;

import com.google.protobuf.Any;
import com.xxkun.client.common.PersonalInfo;
import com.xxkun.client.common.ServerInfo;
import com.xxkun.client.msg.bean.ServerRequest;
import com.xxkun.client.net.request.server.ServerRequsetFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public enum  ServerConnection {
    INSTANCE;

    private static final HeartbeatKeeper heartbeatKeeper = HeartbeatKeeper.INSTANCE;

    public void connect() {
        heartbeatKeeper.add(new ServerHeartbeat(ServerInfo.SERVER_1));
        new Thread(() -> {
            try {
                Thread.sleep(ServerHeartbeat.HEARTBEAT_TIME / 2);
                heartbeatKeeper.add(new ServerHeartbeat(ServerInfo.SERVER_2));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public boolean contains(InetSocketAddress socketAddress) {
        return true;
    }

    class ServerHeartbeat implements HeartbeatKeeper.Heartbeat {
        private static final long HEARTBEAT_TIME = 10 * 1000;

        private final ServerInfo server;
        private long startTime;
        private ServerRequest.BaseServerRequest.Builder heartbeatRequest;

        ServerHeartbeat(ServerInfo server) {
            this.server = server;
            heartbeatRequest = ServerRequsetFactory.getHeartbeatRequestBuilder();
        }

        @Override
        public long getUserId() {
            return server.getServerId();
        }

        @Override
        public InetSocketAddress getInetSocketAddress() {
            return server.getSocketAddress();
        }

        @Override
        public Any getHeartbeatRequest() {
            heartbeatRequest.setToken(PersonalInfo.INSTANCE.getToken());
            return Any.pack(heartbeatRequest.getMessage());
        }

        @Override
        public void setStartDate(long time) {
            startTime = time;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(startTime + HEARTBEAT_TIME - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (getDelay(TimeUnit.MILLISECONDS) -  o.getDelay(TimeUnit.MILLISECONDS));
        }
    }
}