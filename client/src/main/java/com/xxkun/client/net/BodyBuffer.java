package com.xxkun.client.net;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

public final class BodyBuffer  {

    private final ByteBuffer byteBuffer;
    private int markPosition;

    public BodyBuffer(int length) {
        this(new byte[length], 0, length);
    }

    public BodyBuffer(byte[] data, int start, int length) {
        this(ByteBuffer.wrap(data), start, length);
    }

    private BodyBuffer(ByteBuffer buffer, int markPosition, int length) {
        this.markPosition = markPosition;
        byteBuffer = buffer;
        byteBuffer.limit(length);
        byteBuffer.position(markPosition);
        byteBuffer.mark();
    }

    public void put(byte value) {
        byteBuffer.put(value);
    }

    public void putInt(int value) {
        byteBuffer.putInt(value);
    }

    public void putLong(long value) {
        byteBuffer.putLong(value);
    }

    public void putChar(char value) {
        byteBuffer.putChar(value);
    }

    public void putString(String value) {
        putString(value, value.length());
    }

    public void putString(String value, int length) {
        if (length > value.length()) {
            throw new BufferOverflowException();
        }
        for (int i = 0;i < length;i ++) {
            byteBuffer.putChar(value.charAt(i));
        }
    }

    public byte get() {
        return byteBuffer.get();
    }

    public int getInt() {
        return byteBuffer.getInt();
    }

    public long getLong() {
        return byteBuffer.getLong();
    }

    public char getChar() {
        return byteBuffer.getChar();
    }

    public String getString(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0;i < length;i ++) {
            builder.append(byteBuffer.getChar());
        }
        return builder.toString();
    }

    public void position(int index) {
        byteBuffer.position(index + markPosition);
    }

    public int position() {
        return byteBuffer.position() - markPosition;
    }

    public int remaining() {
        return byteBuffer.remaining();
    }

    protected void setLength(int length) {
        byteBuffer.limit(length);
    }

    public byte[] array() {
        return byteBuffer.array();
    }

    public void setMarkPosition(int markPosition) {
        this.markPosition = markPosition;
        byteBuffer.position(markPosition);
        byteBuffer.mark();
    }

    public int getMarkPosition() {
        return markPosition;
    }

    public void reset() {
        byteBuffer.reset();
    }

    public int length() {
        return byteBuffer.limit();
    }
}
