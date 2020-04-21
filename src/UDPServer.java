import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class UDPServer extends Thread {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte [256];
    private Map<Integer, InetAddress> clients = new HashMap<>();

    public UDPServer() {
        try {
            socket = new DatagramSocket(4445);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        running = true;
        System.out.println("Waiting for clients to connect...");

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                clients.put(packet.getPort(), packet.getAddress());
                System.out.println("Client connected... Total clients: " + clients.size());

                for (Map.Entry<Integer, InetAddress> entry : clients.entrySet()) {
                    if (packet.getPort() != entry.getKey()) {
                        sendTo(entry.getKey(), entry.getValue());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    private void sendTo(int port, InetAddress address) throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Client: " + received);
        if (received.equals("end")) {
            running = false;
            return;
        }
        socket.send(packet);
    }
}
