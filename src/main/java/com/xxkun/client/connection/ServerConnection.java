package com.xxkun.client.connection;

import com.xxkun.client.common.PersonalInfo;
import com.xxkun.client.common.ServerInfo;
import com.xxkun.client.dao.Peer;
import com.xxkun.client.pojo.request.HeartbeatRequest;
import com.xxkun.client.pojo.request.Request;

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

    class ServerHeartbeat implements HeartbeatKeeper.Heartbeat {
        private static final long HEARTBEAT_TIME = 5 * 1000;

        private final ServerInfo server;
        private long startTime;
        private HeartbeatRequest heartbeatRequest;

        ServerHeartbeat(ServerInfo server) {
            this.server = server;
            heartbeatRequest = new HeartbeatRequest(server.getSocketAddress());
        }

        @Override
        public long getUserId() {
            return server.getServerId();
        }

        @Override
        public Request getHeartbeatRequest() {
            heartbeatRequest.setToken(PersonalInfo.INSTANCE.getToken());
            return heartbeatRequest;
        }

        @Override
        public void setStartDate(long time) {
            startTime = time;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return HEARTBEAT_TIME;
        }

        @Override
        public int compareTo(Delayed o) {
            return (startTime - ((ServerHeartbeat) o).startTime
                    + getDelay(TimeUnit.MILLISECONDS) -  o.getDelay(TimeUnit.MILLISECONDS)) <= 0 ? -1 : 1;
        }
    }
}