package com.xxkun.client.connection;

import com.xxkun.client.common.BaseThread;
import com.xxkun.client.component.transfer.LocalServer;
import com.xxkun.client.component.transfer.Transfer;
import com.xxkun.client.pojo.request.Request;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public enum  HeartbeatKeeper {
    INSTANCE;

    private final static DelayQueue<Heartbeat> userQueue = new DelayQueue<>();
    private final static Set<Long> userSet = ConcurrentHashMap.newKeySet();
    private final static Listener listener = new Listener();
    private static Transfer transfer;

    static {
        listener.start();
        transfer = LocalServer.INSTANCE;
    }

    public void add(Heartbeat heartbeat) {
        if (!userSet.contains(heartbeat.getUserId())) {
            userSet.add(heartbeat.getUserId());
            heartbeat.setStartDate(System.currentTimeMillis());
            userQueue.add(heartbeat);
        }
    }

    public boolean remove(long userId) {
        return userSet.remove(userId);
    }

    private static class Listener extends BaseThread {
        @Override
        public void run() {
            while (!stop) {
                Heartbeat obj;
                try {
                    obj = userQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    continue;
                }
                if (!userSet.contains(obj.getUserId())) {
                    continue;
                }
                transfer.send(obj.getHeartbeatRequest());
                obj.setStartDate(System.currentTimeMillis());
                userQueue.add(obj);
            }
        }
    }

    public interface Heartbeat extends Delayed {
        long getUserId();
        Request getHeartbeatRequest();
        void setStartDate(long time);
    }
}
