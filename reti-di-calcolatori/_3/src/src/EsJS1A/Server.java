package EsJS1A;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket()) {
            System.out.println("Server " + serverSocket.getLocalAddress() +
                    " riceve richieste di connessione sulla porta " + serverSocket.getLocalPort());

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

                if (received.equals("stop")) break;

                // Calcolo quadrato e inserisco risultato nel buffer di trasmissione
                double num = Math.pow(Double.parseDouble(received), 2);
                String reply = String.valueOf(num);
                byte[] sendBuffer = reply.getBytes();

                // Inserisco la risposta nel DatagramPacket
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
