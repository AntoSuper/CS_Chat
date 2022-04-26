import java.net.*;

public class TestServer {

    public static void main(String[] args) throws java.io.IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
