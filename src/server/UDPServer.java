package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class UDPServer extends Thread {
    private DatagramSocket socket = new DatagramSocket(3000);
    private byte[] buf = new byte [65535];

    private boolean running;
    private Map<Integer, InetAddress> clients = new HashMap<>();

    public UDPServer() throws SocketException {
    }

    public void run() {
        running = true;
        System.out.println("Waiting for clients to connect...");

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                socket.receive(packet);

                String msg = new String(data(buf));

                System.out.println("Client: " + msg);

                clients.put(packet.getPort(), packet.getAddress());
                System.out.println("Client connected... Total clients: " + clients.size());

                for (Map.Entry<Integer, InetAddress> entry : clients.entrySet()) {
                    if (packet.getPort() != entry.getKey()) {
                        sendTo(msg, entry.getKey(), entry.getValue());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    private void sendTo(String msg, int port, InetAddress address) throws IOException {
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, port);
        socket.send(packet);
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
        return msg;
    }
}
