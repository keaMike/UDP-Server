package client.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class PacketReader extends Thread {

    private DatagramSocket socket;
    private byte[] buf = new byte[65535];

    public PacketReader(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            String msg = readMsg();
            System.out.println(msg);
        }
    }

    private String readMsg() {
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            return new String(data(buf));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private StringBuilder data(byte[] bytes) {
        if (bytes == null)
            return null;
        StringBuilder msg = new StringBuilder();
        int i = 0;
        while (bytes[i] != 0)
        {
            msg.append((char) bytes[i]);
            i++;
        }
        System.out.println("MSG: " + msg);
        return msg;
    }
}
