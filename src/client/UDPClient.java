package client;

import client.utils.PacketReader;
import client.utils.PacketSender;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address;

    public UDPClient() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
            System.out.println("Send message to establish connection..");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        PacketReader packetReader = new PacketReader(socket);
        PacketSender packetSender = new PacketSender(socket, address);

        Thread pR = new Thread(packetReader);
        Thread pS = new Thread(packetSender);

        pR.start();
        pS.start();
    }
}
