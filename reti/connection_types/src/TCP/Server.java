package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(0)) {

            System.out.println("Server in ascolto su porta " + serverSocket.getLocalPort());

            Socket serverSide = serverSocket.accept();
            System.out.println("Aperta connessione con client " + serverSide.getRemoteSocketAddress());

            InputStream fromClient = serverSide.getInputStream();
            byte[] receiveBuffer = new byte[100];
            int bytesRead = fromClient.read(receiveBuffer);
            String received = new String(receiveBuffer, 0, bytesRead);
            System.out.println("Messaggio ricevuto: " + received);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
