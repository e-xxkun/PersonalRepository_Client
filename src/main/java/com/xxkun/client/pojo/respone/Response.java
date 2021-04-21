package com.xxkun.client.pojo.respone;

import com.xxkun.client.component.exception.ResponseResolutionException;

import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Date;

public final class Response {

    private static final int HEAD_LEN = 4 * Integer.BYTES + Long.BYTES;

    private final InetSocketAddress socketAddress;

    private final ResponseType type;

    private final BodyBuffer bodyBuffer;

    private Response(ByteBuffer buffer, Integer cmdId, Integer bodyLength, InetSocketAddress socketAddress) {
        this.bodyBuffer = new BodyBuffer(buffer, bodyLength);
        this.socketAddress = socketAddress;
        this.type = ResponseType.valueOf(cmdId);
    }
    public ResponseType getType() {
        return type;
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public static Response decodeFromByteArray(byte[] bytes, InetSocketAddress socketAddress) throws ResponseResolutionException {
        if (bytes.length < HEAD_LEN)
            throw new ResponseResolutionException();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        // HEAD|sequence|cmdId|bodyLength  ->  int|long|int|int
        if (buffer.getInt() != HEAD)
            throw new ResponseResolutionException();
        long seq = buffer.getLong();

        int cmdId = buffer.getInt();
        if (cmdId < 0 || cmdId >= ResponseType.values().length - 1)
            throw new ResponseResolutionException();

        int bodyLength = buffer.getInt();

        return new Response(buffer, seq, cmdId, bodyLength, socketAddress);
    }

    public BodyBuffer getBodyBuffer() {
        return bodyBuffer;
    }

    public class BodyBuffer {

        private int bodyLength;

        private ByteBuffer byteBuffer;

        public BodyBuffer(ByteBuffer byteBuffer, Integer bodyLength) {
            this.byteBuffer = byteBuffer;
            this.bodyLength = bodyLength;
        }

        public int getInt() {
            if (Integer.BYTES > remainLength()) {
                throw new BufferUnderflowException();
            }
            return byteBuffer.getInt();
        }

        public long getLong() {
            if (Long.BYTES > remainLength()) {
                throw new BufferUnderflowException();
            }
            return byteBuffer.getLong();
        }

        public void skip(int length) {
            if (length > remainLength()) {
                throw new IllegalArgumentException();
            }
            byteBuffer.position(byteBuffer.position() + length);
        }

        public void position(int index) {
            index = Math.max(index, 0);
            byteBuffer.position(HEAD_LEN + index);
        }

        public int position() {
            return byteBuffer.position() - HEAD_LEN;
        }

        private int remainLength() {
            return HEAD_LEN + bodyLength - byteBuffer.position();
        }

        public int getBodyLength() {
            return bodyLength;
        }

        public String getString(int length) {
            if (Character.BYTES * length > remainLength()) {
                throw new BufferUnderflowException();
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0;i < length;i ++) {
                builder.append(byteBuffer.getChar());
            }
            return builder.toString();
        }
    }

    public enum ResponseType {
        REPLY(1),
        PUNCH(2),
        UNKNOWN(3);

        private final int cmdId;

        ResponseType(int cmdId) {
            this.cmdId = cmdId;
        }

        public static ResponseType valueOf(int cmdId) {
            if (cmdId > -1 && cmdId < ResponseType.values().length) {
                return ResponseType.values()[cmdId];
            }
            return UNKNOWN;
        }

        public int getCmdId() {
            return cmdId;
        }
    }
}
