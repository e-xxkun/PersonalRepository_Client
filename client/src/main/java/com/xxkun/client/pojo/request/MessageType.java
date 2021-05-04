package com.xxkun.client.pojo.request;

public class MessageType {

    public enum GET implements ServerMessageType {
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
        public BaseServerRequest.RequestType getRequestType() {
            return BaseServerRequest.RequestType.GET;
        }
    }

    public enum PUT implements ServerMessageType {
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
        public BaseServerRequest.RequestType getRequestType() {
            return BaseServerRequest.RequestType.PUT;
        }
    }
}
