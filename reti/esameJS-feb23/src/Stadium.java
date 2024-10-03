import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Stadium {
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

                String msg;
                msg = "ID" + SEPARATOR + "Stadio";
                send(msg, dp, clientSocket);
                // Ricevo ack
                msg = receive(dp, clientSocket);
                if (msg.split(SEPARATOR)[0].equals("ERR")) {
                    // C'è già uno stadio registrato
                    System.out.println("Stadio già registrato");
                    return;
                }

                while (true) {
                    String format = "Squadra1" + SEPARATOR + "Squadra2" + SEPARATOR + "Mete1" + SEPARATOR + "Mete2";
                    System.out.print("Inserisci esito partita [" + format + "]: ");
                    msg = "REQ" + SEPARATOR + "Stadio" + SEPARATOR + reader.readLine();
                    send(msg, dp, clientSocket);

                    msg = receive(dp,clientSocket);
                    if (msg.split(SEPARATOR)[0].equals("OVER")) {
                        clientSocket.close();
                        break;
                    }
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
