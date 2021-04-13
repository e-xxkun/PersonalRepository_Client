package com.xxkun.client.pojo.request;

import com.xxkun.client.component.exception.RequestConvertException;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public abstract class Request implements Delayed {

    public static final int UDP_MSG_MAX_LEN = 512;

    private static final int MAX_RESEND_TIME = 4;

    private static final int HEAD = 0xFEFDDFEB;

//    HEAD|sequence|clientVersion|cmdId|bodyLength  ->  int|long|int|int|int
    private static final int HEAD_LEN = 3 * Integer.BYTES + Long.BYTES;

    private long sequence;

    private final int clientVersion = 1;

    private Date sendDate;

    private InetSocketAddress socketAddress;

    private RequestType type;

    private final BodyBuffer bodyBuffer;

    private String id;

    private int resendTime = 0;

    public Request(InetSocketAddress socketAddress) {
        bodyBuffer = new BodyBuffer();
        bodyBuffer.writeInt(HEAD);
        setSocketAddress(socketAddress);
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public long getSendDate() {
        if (sendDate != null)
            return sendDate.getTime();
        else
            return 0;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
        refreshId();
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
        refreshId();
    }

    public int getClientVersion() {
        return clientVersion;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public void incResendTime() {
        ++ resendTime;
    }

    public boolean isResend() {
        return resendTime > 0;
    }

    public boolean isMaxResendTime() {
        return resendTime >= MAX_RESEND_TIME;
    }

    public byte[] convertToByteArray() throws RequestConvertException {
        overwriteToByteArray(bodyBuffer);
        int curIndex = bodyBuffer.position();
        bodyBuffer.byteBuffer.position(Integer.BYTES);
        bodyBuffer.byteBuffer.putLong(sequence);
        bodyBuffer.byteBuffer.putInt(clientVersion);
        bodyBuffer.byteBuffer.putInt(getType().getCmdId());
        bodyBuffer.byteBuffer.putInt(getBodyLength());
        bodyBuffer.position(curIndex);
        return bodyBuffer.byteBuffer.array();
    }

    private void refreshId() {
        id = "";
        if (socketAddress != null) {
            id += socketAddress.toString();
        }
        id += sequence;
    }

    public boolean hashNext() {
        return false;
    }

    public Request next() {
        return null;
    }

    public abstract int getBodyLength();

    public abstract RequestType getType();

    protected abstract void overwriteToByteArray(BodyBuffer bodyBuffer) throws RequestConvertException;

    @Override
    public long getDelay(TimeUnit unit) {
        return getSendDate();
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id.equals(request.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public class BodyBuffer {

        private final ByteBuffer byteBuffer;

        public BodyBuffer() {
            byteBuffer = ByteBuffer.allocate(UDP_MSG_MAX_LEN);
        }

        public void skip(int length) {
            position(position() + length);
        }

        public void writeInt(int value) {
            if (position() < 0) {
                position(0);
            }
            byteBuffer.putInt(value);
        }

        public void position(int index) {
            index = Math.max(index, 0);
            byteBuffer.position(HEAD_LEN + index);
        }

        public int position() {
            return byteBuffer.position() - HEAD_LEN;
        }

        public void writeString(String value) {
            for (int i = 0;i < value.length();i ++) {
                byteBuffer.putChar(value.charAt(i));
            }
        }

        public int limit() {
            return UDP_MSG_MAX_LEN - HEAD_LEN;
        }

        public void writeLong(long value) {
            if (position() < 0) {
                position(0);
            }
            byteBuffer.putLong(value);
        }
    }

    public enum RequestType {
        ACK(0),
        PUT(1),
        GET(2);

        private final int cmdId;

        RequestType(int cmdId) {
            this.cmdId = cmdId;
        }

        public int getCmdId() {
            return cmdId;
        }
    }
}
