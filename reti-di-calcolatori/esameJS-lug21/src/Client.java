import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static final int BUFFER_SIZE = 100;
    public static final String SEPARATOR = Server.SEPARATOR;

    public static void main(String[] args) {
        InputStreamReader keyboard = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(keyboard);

        try {
            int serverPort = Integer.parseInt(args[0]);
            InetAddress serverIP = InetAddress.getLocalHost();

            try (Socket clientSide = new Socket(serverIP, serverPort)) {
                String msg;

                while (true) {
                    // Ricevo elenco figure
                    receive(clientSide);
                    do {
                        System.out.print("Scegli la figura: ");
                        send(reader.readLine(), clientSide);
                        // Ottengo riscontro dal server
                        msg = receive(clientSide);
                    } while (msg.split(SEPARATOR)[0].equals("ERR"));

                    // Ricevo istruzioni su cosa inserire
                    receive(clientSide);
                    do {
                        // Ottengo dati e li invio
                        send(reader.readLine(), clientSide);
                        // Ottengo riscontro dal server
                        msg = receive(clientSide);
                    } while (msg.split(SEPARATOR)[0].equals("ERR"));

                    // Ricevo (e stampo) risultato finale calcolato dal server
                    receive(clientSide);

                    // Server mi chiede se voglio terminare o no
                    receive(clientSide);
                    msg = reader.readLine();
                    send(msg, clientSide);
                    if (msg.equals("Q")) {
                        clientSide.close();
                        return;
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static void send(String toSend, Socket socket) throws IOException {
        OutputStream toServer = socket.getOutputStream();
        toServer.write(toSend.getBytes(), 0, toSend.length());
    }

    private static String receive(Socket socket) throws IOException {
        byte[] receiveBuffer = new byte[BUFFER_SIZE];
        InputStream fromServer = socket.getInputStream();
        int bytesRead = fromServer.read(receiveBuffer);
        if (bytesRead == -1) throw new IOException();
        String received = new String(receiveBuffer, 0, bytesRead);
        System.out.println("Risposta server: " + received);
        return received;
    }
}
