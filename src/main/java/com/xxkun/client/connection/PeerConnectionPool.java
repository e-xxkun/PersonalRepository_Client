package com.xxkun.client.connection;

import com.xxkun.client.dao.Peer;
import com.xxkun.client.pojo.request.Request;
import com.xxkun.udptransfer.TransferPacket;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public enum PeerConnectionPool {
    INSTANCE;

    private static final HeartbeatKeeper heartbeatKeeper = HeartbeatKeeper.INSTANCE;

    class PeerHeartbeat implements HeartbeatKeeper.Heartbeat {
        private final static long HEARTBEAT_TIME = 5 * 1000;

        private Peer peer;
        private long startTime;

        public PeerHeartbeat(Peer peer) {
            this.peer = peer;
        }

        @Override
        public long getUserId() {
            return peer.getUserId();
        }

        @Override
        public Request getHeartbeatRequest() {
            return null;
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
