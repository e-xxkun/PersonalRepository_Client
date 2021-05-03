package com.xxkun.client.net;

import java.net.InetSocketAddress;

public class BasePacket {
    public final static int MAX_TRANSFER_LEN = 512;

    private static final int HEAD = 0xFE79;
    // HEAD|type|protocol_type|bodyLength -> int|byte|byte|int
    protected static final int HEAD_LEN = 2 * Integer.BYTES + 2 * Byte.BYTES;

    private InetSocketAddress socketAddress;
    private ProtocolType protocolType;
    private Type type;
    private BodyBuffer buffer;
    private int bodyLength;

    public BasePacket(InetSocketAddress socketAddress) {
        this(socketAddress, new byte[MAX_TRANSFER_LEN]);
    }

    public BasePacket(InetSocketAddress socketAddress, byte[] array) {
        this(socketAddress, new BodyBuffer(array, 0, array.length));
    }

    public BasePacket(InetSocketAddress socketAddress, BodyBuffer buffer) {
        this.buffer = buffer;
        this.socketAddress = socketAddress;
    }

    BasePacket(InetSocketAddress socketAddress, BodyBuffer buffer, Type type, ProtocolType protocolType, int bodyLength) {
        this.buffer = buffer;
        this.socketAddress = socketAddress;
        this.type = type;
        this.protocolType = protocolType;
        this.bodyLength = bodyLength;
    }

    protected Type getType() {
        return type;
    }

    protected int setBodyLength() {
        return bodyLength;
    }

    protected final byte[] toByteArray() {
        return buffer.array();
    }

    public int length() {
        return HEAD_LEN + bodyLength;
    }

    public void setSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    protected ProtocolType getProtocolType() {
        return protocolType;
    }

    public BodyBuffer getBuffer() {
        return buffer;
    }

    public final byte[] convertToByteArray() {
        buffer.position(0);
        buffer.putInt(HEAD);
        buffer.put(protocolType.getCode());
        buffer.putInt(length());
        return buffer.array();
    }

    public static BasePacket decodeFromByteArray(byte[] array, InetSocketAddress socketAddress) {
        if (array.length < HEAD_LEN) {
            return null;
        }
        BodyBuffer buffer = new BodyBuffer(array, 0, array.length);
        if (buffer.getInt() != HEAD) {
            return null;
        }
        Type type = Type.fromCode(buffer.get());
        if (type == null) {
            return null;
        }
        ProtocolType protocolType = ProtocolType.fromCode(buffer.get());
        if (protocolType == null) {
            return null;
        }
        int length = buffer.getInt();
        buffer.setMarkPosition(HEAD_LEN);
        buffer.setLength(HEAD_LEN + length);
        return new BasePacket(socketAddress, buffer, type, protocolType, length);
    }

    public enum Type {
        SERVER(0) {
            @Override
            public boolean isServer() {
                return true;
            }
        },
        PEER(1) {
            @Override
            public boolean isPeer() {
                return true;
            }
        },
        CUSTOM(2) {
            @Override
            public boolean isCustom() {
                return true;
            }
        };

        private final int code;
        Type(int code) {
            this.code = code;
        }

        static Type fromCode(int code) {
            if (code >= 0 && code < Type.values().length) {
                return Type.values()[code];
            }
            return null;
        }

        public boolean isServer() {
            return false;
        }

        public boolean isPeer() {
            return false;
        }

        public boolean isCustom() {
            return false;
        }
    }
}
