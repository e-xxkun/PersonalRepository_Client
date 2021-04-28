package com.xxkun.client.connection;

import com.xxkun.client.common.BaseThread;
import com.xxkun.client.component.transfer.LocalServer;
import com.xxkun.client.component.transfer.Transfer;
import com.xxkun.client.pojo.request.Request;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public enum  HeartbeatKeeper {
    INSTANCE;

    private final static DelayQueue<Heartbeat> userQueue = new DelayQueue<>();
    private final static Listener listener = new Listener();
    private static Transfer transfer;

    static {
        listener.start();
        transfer = LocalServer.INSTANCE;
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
                transfer.send(obj.getHeartbeatRequest());
                userQueue.add(obj);
            }
        }
    }

    public interface Heartbeat extends Delayed {
        Request getHeartbeatRequest();
    }
}
