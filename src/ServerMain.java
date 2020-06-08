import server.UDPServer;

import java.net.SocketException;

public class ServerMain {
    public static void main(String[] args) throws SocketException {
        new UDPServer().run();
    }
}
