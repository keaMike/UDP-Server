package client.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class PacketSender extends Thread {

    private Scanner sc = new Scanner(System.in);
    private DatagramSocket socket;
    private InetAddress address;
    private byte[] buf = null;

    public PacketSender(DatagramSocket socket, InetAddress address) {
        this.socket = socket;
        this.address = address;
    }
    @Override
    public void run() {
        while (true) {
            try {
                String msg = sc.nextLine();

                buf = msg.getBytes();

                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 3000);

                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
