package EsJS1A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Client {
    public static void main(String[] args) {

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            // 192.168.1.159 <-- mi sono fatto stampare il mio indirizzo in rete, e passo questo come argomento al main

            // Prendo indirizzo IP e porta del server da linea di comando
            InetAddress address = InetAddress.getByName(args[0]);
            int port = Integer.parseInt(args[1]);

            InetSocketAddress serverAddress = new InetSocketAddress(address, port);

            System.out.print("Inserisci il numero: ");
            InputStreamReader keyboard = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(keyboard);
            String toSend = br.readLine();

            // Creo buffer di trasmissione e ci metto direttamente i byte di toSend
            byte[] buffer = toSend.getBytes();

            // Creo il DatagramPacket (veicolo di trasmissione per UDP) e ci metto come contenuto il buffer di trasmissione
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            // Imposto come indirizzo di destinazione quello del server
            datagramPacket.setSocketAddress(serverAddress);

            // Invio il packet
            clientSocket.send(datagramPacket);

            // Mi creo un buffer di ricezione opportunamente grande
            int bufferSize = 100;
            byte[] receiveBuffer = new byte[bufferSize];

            // Inserisco il buffer di ricezione (vuoto) nel DatagramPacket
            datagramPacket.setData(receiveBuffer);
            // Attendo la risposta del server, che verrà messa in datagramPacket
            clientSocket.receive(datagramPacket);

            // Stampo la risposta del server
            String received = new String(receiveBuffer, 0, datagramPacket.getLength());
            System.out.println("Risposta server: " + received);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
