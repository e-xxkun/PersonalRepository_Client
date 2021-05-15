package com.xxkun.client.msg.handler;

import com.google.protobuf.Any;
import com.xxkun.client.msg.bean.ServerResponse;

import java.net.InetSocketAddress;

public interface ResponseHandler<Type> {


    NextHandler getNextHandler(Type type);

    abstract class NextHandler<Type> {
//        NextHandler() {
//            map.put(getType(), this);
//        }
        abstract Type getType();
        public abstract boolean consume(Any body, InetSocketAddress socketAddress);
    }
}
