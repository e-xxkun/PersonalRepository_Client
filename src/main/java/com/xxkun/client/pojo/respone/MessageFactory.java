package com.xxkun.client.pojo.respone;

import com.xxkun.client.component.exception.MessageResolutionException;

import java.util.Map;

public class MessageFactory {

    private static Map<Integer, IInnerMessageType> typeMap;

    public static IInnerMessageType fromTypeCode(int code) {
        return typeMap.get(code);
    }
    
    public enum ResponseType implements IResponseType {
        SUCCESS(1) {
            @Override
            public ResponseMessage createResponseMessage(Response response) throws MessageResolutionException {
                return null;
            }
        },
        LOGIN_EXPIRE(2) {
            @Override
            public ResponseMessage createResponseMessage(Response response) throws MessageResolutionException {
                return null;
            }
        },
        USER_EXCEPTION(8) {
            @Override
            public ResponseMessage createResponseMessage(Response response) throws MessageResolutionException {
                return null;
            }
        },
        UPDATE_TOKEN(5) {
            @Override
            public ResponseMessage createResponseMessage(Response response) throws MessageResolutionException {
                return null;
            }
        },
        PUNCH(6) {
            @Override
            public ResponseMessage createResponseMessage(Response response) throws MessageResolutionException {
                return null;
            }
        },
        HEARTBEAT(7) {
            @Override
            public ResponseMessage createResponseMessage(Response response) throws MessageResolutionException {
                return null;
            }
        };

        static {
            for (IResponseType responseType : values()) {
                typeMap.put(responseType.getCode(), responseType);
            }
        }

        final int code;
        ResponseType(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }

        public enum UserExceptionType {
            USER_NOT_EXIST(3),
            USER_OFFLINE(4);

            final int code;
            UserExceptionType(int code) {
                this.code = code;
            }
            public int getCode() {
                return code;
            }
        }
    }
}
