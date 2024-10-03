import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Server {
    private static final int BUFFER_SIZE = 100;
    public static final String SEPARATOR = ":";

    public enum Team {
        Italy,
        Wales,
        France,
        England,
        Scotland,
        Ireland;
    }

    private static class RugbyTournament {
        private record Game(Team t1, Team t2) {
        }

        public RugbyTournament() {
            for (Team team : Team.values()) {
                totalScores.put(team, 0);
            }
        }

        private final Set<Game> gamesPlayed = new HashSet<>();
        private final Map<Team, Integer> totalScores = new HashMap<>();

        boolean isGameOver(Team firstTeam, Team secondTeam) {
            return gamesPlayed.contains(new Game(firstTeam, secondTeam));
        }

        int getTotalScore(Team t) {
            return totalScores.get(t);
        }

        boolean isOver() {
            return gamesPlayed.size() == 15;
        }

        String getRankings() {
            return totalScores.toString();
        }

        void insertGame(Team firstTeam, Team secondTeam, int firstScore, int secondScore) {
            if (isGameOver(firstTeam, secondTeam)) throw new IllegalArgumentException();

            gamesPlayed.add(new Game(firstTeam, secondTeam));
            int currFirstScore = totalScores.get(firstTeam);
            int currSecondScore = totalScores.get(secondTeam);

            int firstPoints = 0;
            int secondPoints = 0;
            if (firstScore > secondScore) {
                firstPoints += 4;
                if (firstScore - secondScore <= 7) secondPoints += 1;
            } else if (secondScore > firstScore) {
                secondPoints += 4;
                if (secondScore - firstScore <= 7) firstPoints += 1;
            } else {
                firstPoints += 2;
                secondPoints += 2;
            }

            if (firstScore >= 4) firstPoints += 1;
            if (secondScore >= 4) secondPoints += 1;

            totalScores.put(firstTeam, currFirstScore + firstPoints);
            totalScores.put(secondTeam, currSecondScore + secondPoints);
        }

    }

    public static void main(String[] args) {
        final Map<String, SocketAddress> name2Address = new HashMap<>();
        final RugbyTournament tournament = new RugbyTournament();

        try (DatagramSocket serverSocket = new DatagramSocket(0)) {
            System.out.println("Server " + serverSocket.getLocalAddress()
                    + " riceve richieste su porta " + serverSocket.getLocalPort());

            DatagramPacket dp = new DatagramPacket(new byte[0], 0);

            while (true) {
                String msg = receive(dp, serverSocket);
                SocketAddress clientAddress = dp.getSocketAddress();

                String[] data = msg.split(SEPARATOR);
                String messageType = data[0];
                String usrName = data[1];

                switch (messageType) {
                    case "ID":
                        if (name2Address.containsKey(usrName)) {
                            send("ERR" + SEPARATOR + "Nome utente già esistente", dp, serverSocket, clientAddress);
                            break;
                        }
                        name2Address.put(usrName, clientAddress);
                        send("OK" + SEPARATOR + "Registrazione completata per " + usrName, dp, serverSocket, clientAddress);
                        break;
                    case "REQ":
                        if (!name2Address.containsKey(usrName) || !clientAddress.equals(name2Address.get(usrName))) {
                            send("Identificazione non riuscita", dp, serverSocket, clientAddress);
                            break;
                        }
                        if (usrName.equals("Stadio")) {
                            // Richiesta dello stadio
                            Team t1 = Team.valueOf(data[2]);
                            Team t2 = Team.valueOf(data[3]);
                            if (tournament.isGameOver(t1, t2)) {
                                send("Incontro già giocato", dp, serverSocket, clientAddress);
                                break;
                            }
                            int m1 = Integer.parseInt(data[4]);
                            int m2 = Integer.parseInt(data[5]);
                            tournament.insertGame(t1, t2, m1, m2);
                            if (tournament.isOver()) {
                                send("OVER" + SEPARATOR + tournament.getRankings(), dp, serverSocket, clientAddress);
                            } else {
                                msg = t1 + ": " + tournament.getTotalScore(t1) + " ; " + t2 + ": " + tournament.getTotalScore(t2);
                                send("GOING" + SEPARATOR + msg, dp, serverSocket, clientAddress);
                            }
                        } else {
                            // Richiesta di uno spettatore
                            if (!name2Address.containsKey(usrName) || !clientAddress.equals(name2Address.get(usrName))) {
                                send("Identificazione non riuscita", dp, serverSocket, clientAddress);
                                break;
                            }
                            Team team = Team.valueOf(data[2]);
                            send("Punteggio: " + String.valueOf(tournament.getTotalScore(team)), dp, serverSocket, clientAddress);
                        }
                        break;
                    default:
                        send("Tipo di messaggio non riconosciuto", dp, serverSocket, clientAddress);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String receive(DatagramPacket dp, DatagramSocket datagramSocket) throws IOException {
        byte[] receiveBuffer = new byte[BUFFER_SIZE];
        dp.setData(receiveBuffer);
        datagramSocket.receive(dp);
        String received = new String(receiveBuffer, 0, dp.getLength());
        System.out.println("Richiesta ricevuta " + received + " da client " + dp.getSocketAddress());
        return received;
    }

    private static void send(String toSend, DatagramPacket dp, DatagramSocket datagramSocket, SocketAddress dest) throws IOException {
        dp.setSocketAddress(dest);
        dp.setData(toSend.getBytes(), 0, toSend.length());
        datagramSocket.send(dp);
    }
}
