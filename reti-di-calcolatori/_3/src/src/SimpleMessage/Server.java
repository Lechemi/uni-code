package SimpleMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            System.out.println("Server " + serverSocket.getInetAddress() +
                    " riceve richieste di connessione sulla porta " + serverSocket.getLocalPort());

            while (true) {
                Socket toClient = serverSocket.accept();

                // Mi creo un buffer di ricezione opportunamente grande
                int bufferSize = 100;
                byte[] buffer = new byte[bufferSize];

                // Ottengo lo stream su cui riceverò i byte del client
                InputStream fromClient = toClient.getInputStream();

                /*
                    Metto ciò che ottengo da read nel buffer di ricezione.
                    Leggi la doc di read che fai prima.
                */
                int bytesRead = fromClient.read(buffer);

                if (bytesRead > 0) {
                    /*
                        Creo una stringa col contenuto di buffer, partendo dal primo byte (offset = 0) e
                        arrivando al bytesRead-esimo.
                    */
                    String msg = new String(buffer, 0, bytesRead);

                    System.out.println("Messaggio ricevuto: " + msg);
                    System.out.println("Lunghezza: " + bytesRead + " byte");

                    if (msg.equals("end")) break;
                }
                toClient.close();
            }
        }
    }
}
