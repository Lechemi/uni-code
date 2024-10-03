import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final int BUFFER_SIZE = 100;

    private record TrackRecord(int played, int won) {
        @Override
        public String toString() {
            return "Vinte " + won + " su " + played;
        }

        public TrackRecord addWin() {
            return new TrackRecord(played + 1, won + 1);
        }

        public TrackRecord addLoss() {
            return new TrackRecord(played + 1, won);
        }
    }

    public static void main(String[] args) {
        int cs = 0;
        int cw = 0;

        Map<SocketAddress, TrackRecord> client2Match = new HashMap<>();

        try (DatagramSocket serverSocket = new DatagramSocket(0)) {
            System.out.println("Server " + serverSocket.getLocalAddress()
                    + " riceve richieste su porta " + serverSocket.getLocalPort());

            DatagramPacket dp = new DatagramPacket(new byte[0], 0);

            while (true) {
                String msg = receive(dp, serverSocket);
                SocketAddress clientAddress = dp.getSocketAddress();

                switch (msg) {
                    case "p":
                        if (client2Match.containsKey(clientAddress)) {
                            // La partita è già aperta con questo client
                            send("e", dp, serverSocket, clientAddress);
                            break;
                        }
                        client2Match.put(clientAddress, new TrackRecord(0, 0));
                        send("k", dp, serverSocket, clientAddress);
                        System.out.println("Inizia partita con " + clientAddress);
                        cs++;
                        break;
                    case "f":
                    case "s":
                    case "c":
                        if (!client2Match.containsKey(clientAddress)) {
                            // Il client non ha avviato alcuna partita
                            send("e", dp, serverSocket, clientAddress);
                            break;
                        }
                        String move = "c"; // Valore estratto a caso
                        send(move, dp, serverSocket, clientAddress);
                        System.out.println("Mossa client: " + msg + ", mossa server: " + move);

                        // Aggiorno il track record
                        TrackRecord current = client2Match.get(clientAddress);
                        int res = result(msg, move);
                        if (res == 1) {
                            client2Match.put(clientAddress, current.addWin());
                        } else {
                            client2Match.put(clientAddress, current.addLoss());
                        }

                        break;
                    case "i":
                        if (!client2Match.containsKey(clientAddress)) {
                            // Il client non ha avviato alcuna partita
                            send("e", dp, serverSocket, clientAddress);
                            break;
                        }
                        send("b", dp, serverSocket, clientAddress);
                        System.out.println(client2Match.get(clientAddress).toString() + " con client " + clientAddress);
                        break;
                    case "y":
                        if (!client2Match.containsKey(clientAddress)) {
                            // Il client non ha avviato alcuna partita
                            send("e", dp, serverSocket, clientAddress);
                            break;
                        }
                        send("b", dp, serverSocket, clientAddress);
                        System.out.println(client2Match.get(clientAddress).toString() + " con client " + clientAddress);
                        cw++;
                        break;
                    default:
                        send("e", dp, serverSocket, clientAddress);
                        break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private static String receive(DatagramPacket dp, DatagramSocket datagramSocket) throws IOException {
        byte[] receiveBuffer = new byte[BUFFER_SIZE];
        dp.setData(receiveBuffer);
        datagramSocket.receive(dp);
        String received = new String(receiveBuffer, 0, dp.getLength());
        System.out.println("Messaggio ricevuto: " + received + " da client " + dp.getSocketAddress());
        return received;
    }

    private static void send(String msg, DatagramPacket dp, DatagramSocket datagramSocket, SocketAddress destination) throws IOException {
        dp.setData(msg.getBytes());
        dp.setSocketAddress(destination);
        datagramSocket.send(dp);
    }
}
