package com.xxkun.client.net;

import com.xxkun.udptransfer.TransferPacket;
import com.xxkun.udptransfer.TransferServer;

import java.net.DatagramPacket;

public enum  PacketPool {
    INSTANCE;

    public DatagramPacket createPacket() {
        byte[] data = new byte[TransferServer.MAX_TRANSFER_LEN];
        return new DatagramPacket(data, data.length);
    }

    public TransferPacket createACKPacket(TransferPacket packet) {
        TransferPacket.BodyBuffer bodyBuffer = new TransferPacket.BodyBuffer(0);
        TransferPacket ackPacket = new TransferPacket(bodyBuffer, packet.getSocketAddress(), TransferPacket.Type.ACK);
        ackPacket.setSequence(packet.getSequence());
        return ackPacket;
    }
}
