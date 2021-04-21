package com.xxkun.client.pojo.request;

public class MessageType {

    public enum GET implements IMessageType {
        PUNCH(0),
        HEARTBEAT(1);

        private final int code;

        GET(int code) {
            this.code = code;
        }
        @Override
        public int getCode() {
            return code;
        }
        @Override
        public Request.RequestType getRequestType() {
            return Request.RequestType.GET;
        }
    }

    public enum PUT implements IMessageType {
        ;

        private final int code;

        PUT(int code) {
            this.code = code;
        }
        @Override
        public int getCode() {
            return code;
        }
        @Override
        public Request.RequestType getRequestType() {
            return Request.RequestType.PUT;
        }
    }
}
