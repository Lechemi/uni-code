import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Client {
    private static final int BUFFER_SIZE = 100;

    public static void main(String[] args) {
        InputStreamReader keyboard = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(keyboard);

        try {
            int score = 0;
            int serverPort = Integer.parseInt(args[0]);
            InetAddress serverIP = InetAddress.getLocalHost();
            InetSocketAddress serverAddress = new InetSocketAddress(serverIP, serverPort);

            try (DatagramSocket clientSocket = new DatagramSocket()) {
                DatagramPacket dp = new DatagramPacket(new byte[0], 0);
                dp.setSocketAddress(serverAddress);

                // Mando p e mi aspetto di ricevere k
                send("p", dp, clientSocket);
                String msg = receive(dp, clientSocket);

                while (true) {
                    System.out.print("Inserisci comando/mossa: ");
                    msg = reader.readLine();

                    if (msg.equals(".")) {
                        if (score <= 0) {
                            send("y", dp, clientSocket);
                        } else {
                            send("i", dp, clientSocket);
                        }
                        receive(dp, clientSocket);
                        clientSocket.close();
                        return;
                    }

                    send(msg, dp, clientSocket);
                    String reply = receive(dp, clientSocket);
                    if (reply.equals("e")) continue;

                    int res = result(msg, reply);
                    switch (res) {
                        case 1:
                            System.out.println("Vince il server");
                            score--;
                            break;
                        case -1:
                            System.out.println("Vinco io");
                            score++;
                            break;
                        case 0:
                            System.out.println("0");
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

    private static void send(String msg, DatagramPacket dp, DatagramSocket datagramSocket) throws IOException {
        dp.setData(msg.getBytes());
        datagramSocket.send(dp);
    }

    private static int result(String clientMove, String serverMove) {
        // Restituisce 1 se vince il server, -1 se vince il client, 0 se pari

        if (clientMove.equals(serverMove)) return 0;

        switch (clientMove) {
            case "f":
                if (serverMove.equals("c")) {
                    return -1;
                } else {
                    return 1;
                }
            case "s":
                if (serverMove.equals("c")) {
                    return 1;
                } else {
                    return -1;
                }
            case "c":
                if (serverMove.equals("f")) {
                    return 1;
                } else {
                    return -1;
                }
        }

        return 0;
    }

}
