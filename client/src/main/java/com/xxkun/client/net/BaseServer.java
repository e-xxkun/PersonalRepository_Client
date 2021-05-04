package com.xxkun.client.net;

import com.xxkun.client.connection.PeerConnectionPool;
import com.xxkun.client.connection.ServerConnection;

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

    public void send(BasePacket packet) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(packet.convertToByteArray(), packet.length(), packet.getSocketAddress());
        ProtocolHandler.sendBefor(packet, this);
        socket.send(datagramPacket);
        ProtocolHandler.sendAfter(packet, this);
    }

    public BasePacket receive() throws IOException {
        DatagramPacket packet = pool.createPacket();
        while (true) {
            socket.receive(packet);
            InetSocketAddress socketAddress = (InetSocketAddress) packet.getSocketAddress();
            BasePacket basePacket = BasePacket.decodeFromByteArray(packet.getData(), socketAddress);
            if (basePacket == null) {
                continue;
            }
            if (basePacket.getType().isServer()) {
                if (!serverConnection.contains(socketAddress)) {
                    continue;
                }
            } else if (!peerConnectionPool.contains(socketAddress)) {
                if (onPacketReceive != null) {
                    onPacketReceive.onPeerNotExist(basePacket);
                }
                continue;
            }
            if (!ProtocolHandler.receive(basePacket, this)) {
                continue;
            }
            return basePacket;
        }
    }

    public void setOnPacketReceive(OnExceptionPacketReceive onPacketReceive) {
        this.onPacketReceive = onPacketReceive;
    }

    interface OnExceptionPacketReceive {

        void onPeerNotExist(BasePacket packet);
    }
}
