package com.xxkun.client.common;

public class BaseThread extends Thread {

    protected boolean stop = false;

    public void close() {
        stop = true;
    }
}
