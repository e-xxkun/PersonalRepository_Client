package com.xxkun.client.net;

import com.google.protobuf.Any;
import com.xxkun.client.connection.PeerConnectionPool;
import com.xxkun.client.connection.ServerConnection;
import com.xxkun.client.msg.bean.BasePacket.Packet;
import com.xxkun.client.msg.handler.BasePacketResponseHandler;

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

    public void send(Any body, InetSocketAddress socketAddress) throws IOException {
        Packet.Builder builder = Packet.newBuilder();
        builder.setBody(body);
        ProtocolHandler.sendBefor(builder, socketAddress, this);
        Packet packet = builder.build();
        byte[] data = packet.toByteArray();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, socketAddress);
        socket.send(datagramPacket);
        ProtocolHandler.sendAfter(packet, socketAddress, this);
    }

    public BasePacketExtend receive() throws IOException {
        DatagramPacket packet = pool.createPacket();
        while (true) {
            socket.receive(packet);
            InetSocketAddress socketAddress = (InetSocketAddress) packet.getSocketAddress();
            Packet basePacket = Packet.parseFrom(packet.getData());
            if (!BasePacketResponseHandler.INSTANCE.check(basePacket)) {
                continue;
            }
            if (basePacket.getType() == Packet.Type.SERVER) {
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
        void onPeerNotExist(Packet basePacket, InetSocketAddress socketAddress);
    }

    static class BasePacketExtend {
        Packet packet;
        InetSocketAddress socketAddress;

        BasePacketExtend(Packet packet, InetSocketAddress socketAddress) {
            this.packet = packet;
            this.socketAddress = socketAddress;
        }
    }
}
