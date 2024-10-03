package EsJS2B;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            int serverPort = Integer.parseInt(args[0]);
            InetAddress serverIP = InetAddress.getLocalHost();
            SocketAddress serverAddress = new InetSocketAddress(serverIP, serverPort);

            InputStreamReader keyboard = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(keyboard);

            while (true) {
                System.out.print("Insert operation [+, -, *, /, or .]: ");
                String op = br.readLine();

                DatagramPacket datagramPacket = new DatagramPacket(op.getBytes(), op.length());
                datagramPacket.setSocketAddress(serverAddress);

                if (op.equals(".")) {
                    clientSocket.send(datagramPacket);
                    clientSocket.close();
                    return;
                }

                System.out.print("Insert 2 operands, separated by a white space: ");
                String[] operands = br.readLine().split(" ");

                String toSend = op + ":" + operands[0] + ":" + operands[1];

                datagramPacket.setData(toSend.getBytes());
                clientSocket.send(datagramPacket);

                int bufferSize = 100;
                byte[] receiveBuffer = new byte[bufferSize];

                datagramPacket.setData(receiveBuffer);
                clientSocket.receive(datagramPacket);
                String received = new String(receiveBuffer, 0, datagramPacket.getLength());
                System.out.println("Server reply: " + received);
            }
        }
    }
}
