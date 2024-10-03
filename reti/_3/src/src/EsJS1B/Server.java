package EsJS1B;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Server {
    public static final String SEPARATOR = ":";

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket()) {
            System.out.println("Server " + serverSocket.getLocalAddress() +
                    " riceve richieste sulla porta " + serverSocket.getLocalPort());

            while (true) {
                // Mi creo un buffer di ricezione opportunamente grande
                int bufferSize = 100;
                byte[] receiveBuffer = new byte[bufferSize];

                // Creo l'oggetto col quale riceverò il messaggio
                DatagramPacket datagramPacket = new DatagramPacket(receiveBuffer, bufferSize);

                // Mi metto in ascolto (receive è bloccante); se ricevo qualcosa lo metto in datagramPacket
                serverSocket.receive(datagramPacket);

                String received = new String(receiveBuffer, 0, datagramPacket.getLength());
                System.out.println("Messaggio ricevuto: " + received);

                // datagramPacket acquisisce anche l'indirizzo del mittente (nel momento in cui riceve un msg)
                InetAddress clientIPAddress = datagramPacket.getAddress();
                int clientPort = datagramPacket.getPort();
                System.out.println("Da mittente: " + clientIPAddress.getHostAddress() + ":" + clientPort);

                // In alternativa si poteva fare System.out.println("Da mittente: " + datagramPacket.getSocketAddress());

                String[] fields = received.split(SEPARATOR);
                String type = fields[0];
                String content = fields[1];

                String reply = switch (type) {
                    case "N" -> {
                        double num = Math.pow(Double.parseDouble(content), 2);
                        yield content + " al quadrato: " + num;
                    }
                    case "S" -> content + " è lunga " + content.length();
                    default -> "Invalid message type";
                };

                // Inserisco la reply nel buffer di trasmissione
                byte[] sendBuffer = reply.getBytes();

                // Inserisco il contenuto del buffer di trasmissione nel DatagramPacket
                datagramPacket.setData(sendBuffer);

                // Imposto il destinatario, ossia il client da cui ho ricevuto la richiesta
                datagramPacket.setSocketAddress(new InetSocketAddress(clientIPAddress, clientPort));

                // Invio la risposta
                serverSocket.send(datagramPacket);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
