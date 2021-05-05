package com.xxkun.client.net;

import com.xxkun.client.connection.PeerConnectionPool;
import com.xxkun.client.connection.ServerConnection;
import com.xxkun.client.msg.bean.BasePacket;
import com.xxkun.client.msg.handler.BasePacketHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

class BaseServer {

    private final PeerConnectionPool peerConnectionPool;
    private final ServerConnection serverConnection;
    private final PacketPool pool;
    private final DatagramSocket socket;

    public BaseServer() throws SocketException {
        peerConnectionPool = PeerConnectionPool.INSTANCE;
        serverConnection = ServerConnection.INSTANCE;
        pool = PacketPool.INSTANCE;
        socket = new DatagramSocket();
    }

    private OnExceptionPacketReceive onPacketReceive;

    public void send(BasePacket.Packet packet, InetSocketAddress socketAddress) throws IOException {
        byte[] data = packet.toByteArray();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, socketAddress);
        ProtocolHandler.sendBefor(packet, socketAddress, this);
        socket.send(datagramPacket);
        ProtocolHandler.sendAfter(packet, socketAddress, this);
    }

    public BasePacketExtend receive() throws IOException {
        DatagramPacket packet = pool.createPacket();
        while (true) {
            socket.receive(packet);
            InetSocketAddress socketAddress = (InetSocketAddress) packet.getSocketAddress();
            BasePacket.Packet basePacket = BasePacket.Packet.parseFrom(packet.getData());
            if (!BasePacketHandler.INSTANCE.check(basePacket)) {
                continue;
            }
            if (basePacket.getType() == BasePacket.Packet.Type.SERVER) {
                if (!serverConnection.contains(socketAddress)) {
                    continue;
                }
            } else if (!peerConnectionPool.contains(socketAddress)) {
                if (onPacketReceive != null) {
                    onPacketReceive.onPeerNotExist(basePacket , socketAddress);
                }
                continue;
            }
            if (!ProtocolHandler.receive(basePacket, socketAddress, this)) {
                continue;
            }
            return new BasePacketExtend(basePacket, socketAddress);
        }
    }

    public void setOnExceptionPacketReceive(OnExceptionPacketReceive onPacketReceive) {
        this.onPacketReceive = onPacketReceive;
    }

    interface OnExceptionPacketReceive {
        void onPeerNotExist(BasePacket.Packet basePacket, InetSocketAddress socketAddress);
    }

    static class BasePacketExtend {
        BasePacket.Packet packet;
        InetSocketAddress socketAddress;

        BasePacketExtend(BasePacket.Packet packet, InetSocketAddress socketAddress) {
            this.packet = packet;
            this.socketAddress = socketAddress;
        }
    }
}
