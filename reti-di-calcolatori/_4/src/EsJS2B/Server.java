package EsJS2B;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

public class Server {
    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket()) {
            System.out.println("Server " + serverSocket.getLocalAddress() +
                    " receives requests on port " + serverSocket.getLocalPort());

            final int bufferSize = 100;

            while (true) {
                byte[] receiveBuffer = new byte[bufferSize];

                DatagramPacket datagramPacket = new DatagramPacket(receiveBuffer, bufferSize);
                serverSocket.receive(datagramPacket);

                String received = new String(receiveBuffer, 0, datagramPacket.getLength());
                SocketAddress clientAddress = datagramPacket.getSocketAddress();
                System.out.println("Request: " + received + " from client " + clientAddress);

                if (received.equals(".")) {
                    serverSocket.close();
                    return;
                }

                String reply = getReply(received);

                datagramPacket.setData(reply.getBytes());
                datagramPacket.setSocketAddress(clientAddress);
                serverSocket.send(datagramPacket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
