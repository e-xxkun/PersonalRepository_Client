package com.xxkun.client.net;

import com.xxkun.udptransfer.TransferServer;

import java.net.DatagramPacket;

public enum  PacketPool {
    INSTANCE;

    public DatagramPacket createPacket() {
        byte[] data = new byte[TransferServer.MAX_TRANSFER_LEN];
        return new DatagramPacket(data, data.length);
    }

}
