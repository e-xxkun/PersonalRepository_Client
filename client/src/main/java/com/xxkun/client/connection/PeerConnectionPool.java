package com.xxkun.client.connection;

import com.google.protobuf.Any;
import com.xxkun.client.dao.Peer;
import com.xxkun.client.pojo.request.BaseServerRequest;

import java.net.InetSocketAddress;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public enum PeerConnectionPool {
    INSTANCE;

    private static final HeartbeatKeeper heartbeatKeeper = HeartbeatKeeper.INSTANCE;

    public boolean contains(InetSocketAddress socketAddress) {
        return true;
    }

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
        public InetSocketAddress getInetSocketAddress() {
            return null;
        }

        @Override
        public Any getHeartbeatRequest() {
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
