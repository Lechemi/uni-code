package EsJS2A;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            System.out.println("Server " + serverSocket.getInetAddress() +
                    " riceve richieste di connessione sulla porta " + serverSocket.getLocalPort());

            // serverSide è la parte del canale client-server lato server (vedi Appunti.txt)
            Socket serverSide = serverSocket.accept();
            System.out.println("Client " + serverSide.getInetAddress() +
                    " con porta " + serverSide.getPort());

            // Creo buffer ricezione
            int bufferSize = 100;
            byte[] buffer = new byte[bufferSize];

            // Ottengo la stream da cui provengono i dati (ossia direzione client-server)
            InputStream fromClient = serverSide.getInputStream();
            // Leggi documentazione read che fai prima (è easy)
            int numberOfBytesRead = fromClient.read(buffer);
            String received = new String(buffer, 0, numberOfBytesRead);
            System.out.println("Messaggio ricevuto: " + received);

            String[] splitData = received.split(":");
            String operation = splitData[0];
            double firstOperand = Double.parseDouble(splitData[1]);
            double secondOperand = Double.parseDouble(splitData[2]);

            String reply = switch (operation) {
                case "+" -> String.valueOf(firstOperand + secondOperand);
                case "-" -> String.valueOf(firstOperand - secondOperand);
                case "*" -> String.valueOf(firstOperand * secondOperand);
                case "/" -> String.valueOf(firstOperand / secondOperand);
                default -> "Operazione non valida";
            };

            // Ottengo lo stream su cui mandare i dati (ossia direzione server-client)
            OutputStream toClient = serverSide.getOutputStream();

            toClient.write(reply.getBytes(), 0, reply.length());

            // Chiudo la socket relativa al client appena servito
            serverSide.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
