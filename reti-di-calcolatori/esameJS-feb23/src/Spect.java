import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Spect {
    private static final int BUFFER_SIZE = 100;
    public static final String SEPARATOR = Server.SEPARATOR;

    public static void main(String[] args) {
        InputStreamReader keyboard = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(keyboard);

        try {
            int serverPort = Integer.parseInt(args[0]);
            InetAddress serverIP = InetAddress.getLocalHost();
            InetSocketAddress serverAddress = new InetSocketAddress(serverIP, serverPort);

            try (DatagramSocket clientSocket = new DatagramSocket()) {
                DatagramPacket dp = new DatagramPacket(new byte[0], 0);
                dp.setSocketAddress(serverAddress);

                // Mi identifico con l'username
                String status;
                String msg;
                String userName;
                do {
                    System.out.print("Inserisci username: ");
                    userName = reader.readLine();
                    msg = "ID" + SEPARATOR + userName;
                    send(msg, dp, clientSocket);
                    // Ricevo ack
                    msg = receive(dp, clientSocket);
                    status = msg.split(SEPARATOR)[0];
                } while (status.equals("ERR"));

                while (true) {
                    System.out.print("Inserisci squadra: ");
                    msg = "REQ" + SEPARATOR + userName + SEPARATOR + reader.readLine();
                    send(msg, dp, clientSocket);

                    receive(dp, clientSocket);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static String receive(DatagramPacket dp, DatagramSocket datagramSocket) throws IOException {
        byte[] receiveBuffer = new byte[BUFFER_SIZE];
        dp.setData(receiveBuffer);
        datagramSocket.receive(dp);
        String received = new String(receiveBuffer, 0, dp.getLength());
        System.out.println("Risposta server: " + received);
        return received;
    }

    private static void send(String toSend, DatagramPacket dp, DatagramSocket datagramSocket) throws IOException {
        dp.setData(toSend.getBytes(), 0, toSend.length());
        datagramSocket.send(dp);
    }
}
