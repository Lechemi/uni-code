package EsJS2D;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            System.out.println("Server " + serverSocket.getInetAddress() +
                    " riceve richieste di connessione sulla porta " + serverSocket.getLocalPort());

            // Non si esce mai da questo ciclo while
            while (true) {
                Socket serverSide = serverSocket.accept();
                System.out.println("Client " + serverSide.getInetAddress() +
                        " con porta " + serverSide.getPort());

                Thread thread = new ErogaServizio(serverSide);
                thread.start();
            }
        }
    }
}
