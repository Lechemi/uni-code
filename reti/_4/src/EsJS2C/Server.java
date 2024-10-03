package EsJS2C;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            System.out.println("Server " + serverSocket.getInetAddress() +
                    " riceve richieste di connessione sulla porta " + serverSocket.getLocalPort());

            // Non si esce mai da questo ciclo while
            while (true) {

                /*
                    Se arrivo qua sono appena entrato nel ciclo
                    oppure
                    ho terminato l'interazione col client precedente e passo ad ascoltare i successivi.

                    A quanto pare viene mantenuta automaticamente la coda delle richieste dei client.
                */

                Socket serverSide = serverSocket.accept();
                System.out.println("Client " + serverSide.getInetAddress() +
                        " con porta " + serverSide.getPort());

                final int bufferSize = 100;

                while (true) {
                    byte[] receiveBuffer = new byte[bufferSize];

                    // Ottengo la stream da cui provengono i dati (ossia direzione client-server)
                    InputStream fromClient = serverSide.getInputStream();
                    // Leggi documentazione read che fai prima (è easy)
                    int numberOfBytesRead = fromClient.read(receiveBuffer);
                    String received = new String(receiveBuffer, 0, numberOfBytesRead);
                    System.out.println("Messaggio ricevuto: " + received);

                    if (received.equals(".")) {
                        serverSide.close();
                        break;
                    }

                    String reply = getReply(received);
                    // Ottengo lo stream su cui mandare i dati (ossia direzione server-client)
                    OutputStream toClientStream = serverSide.getOutputStream();
                    toClientStream.write(reply.getBytes(), 0, reply.length());
                }
            }
        }
    }

    private static String getReply(String received) {
        String[] splitData = received.split(":");
        String operation = splitData[0];
        double firstOperand = Double.parseDouble(splitData[1]);
        double secondOperand = Double.parseDouble(splitData[2]);

        return switch (operation) {
            case "+" -> String.valueOf(firstOperand + secondOperand);
            case "-" -> String.valueOf(firstOperand - secondOperand);
            case "*" -> String.valueOf(firstOperand * secondOperand);
            case "/" -> String.valueOf(firstOperand / secondOperand);
            default -> "Operazione non valida";
        };
    }
}
