package com.xxkun.client.common;

import com.xxkun.client.common.exception.MessageResolutionException;
import com.xxkun.client.pojo.respone.IServerResponseType;
import com.xxkun.client.pojo.respone.BaseResponse;
import com.xxkun.client.pojo.respone.BaseMessage;
import com.xxkun.client.pojo.respone.ResponseType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum  MessageFactory {
    ;

    private static EnumMap<ResponseType, Map<Integer, IServerResponseType>> typeMap;

    static {
        typeMap = new EnumMap<>(ResponseType.class);
        for (ResponseType type : ResponseType.values()) {
            typeMap.put(type, new HashMap<>());
        }
    }

    public static IServerResponseType fromTypeCode(ResponseType type, int code) {
        Map<Integer, IServerResponseType> map = typeMap.get(type);
        if (map != null) {
            return map.get(code);
        }
        return null;
    }

    private static void init(IServerResponseType[] types, ResponseType type) {
        Map<Integer, IServerResponseType> map = typeMap.get(type);
        for (IServerResponseType responseType : types) {
            map.put(responseType.getCode(), responseType);
        }
    }
    
    public enum ServerResponseType implements IServerResponseType {
        SUCCESS(1) {
            @Override
            public BaseMessage createResponseMessage(BaseResponse response) throws MessageResolutionException {
                return null;
            }
        },
        LOGIN_EXPIRE(2) {
            @Override
            public BaseMessage createResponseMessage(BaseResponse response) throws MessageResolutionException {
                return null;
            }
        },
        USER_EXCEPTION(8) {
            @Override
            public BaseMessage createResponseMessage(BaseResponse response) throws MessageResolutionException {
                return null;
            }
        },
        UPDATE_TOKEN(5) {
            @Override
            public BaseMessage createResponseMessage(BaseResponse response) throws MessageResolutionException {
                return null;
            }
        },
        PUNCH(6) {
            @Override
            public BaseMessage createResponseMessage(BaseResponse response) throws MessageResolutionException {
                return null;
            }
        },
        HEARTBEAT(7) {
            @Override
            public BaseMessage createResponseMessage(BaseResponse response) throws MessageResolutionException {
                return null;
            }
        };

        static {
            init(values(), ResponseType.SERVER);
        }

        final int code;
        ServerResponseType(int code) {
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
