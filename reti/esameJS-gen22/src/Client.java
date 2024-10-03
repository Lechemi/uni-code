import java.io.*;
import java.net.*;

public class Client {
    private static final int BUFFER_SIZE = 100;

    public static void main(String[] args) {
        InputStreamReader keyboard = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(keyboard);

        try {
            int serverPort = Integer.parseInt(args[0]);
            InetAddress serverIP = InetAddress.getLocalHost();
            String nickName;

            try {
                String msg;
                Socket clientSide;
                do {
                    System.out.print("Inserisci il nickname: ");
                    nickName = reader.readLine();
                    clientSide = new Socket(serverIP, serverPort);
                    send(nickName, clientSide);
                    receive(clientSide);
                } while (isServerSideClosed(clientSide));

                while (true) {
                    // Numero di operandi (e nickName)
                    int numberOfOperands;
                    do {
                        System.out.print("Inserisci il numero di operandi (massimo 5): ");
                        numberOfOperands = Integer.parseInt(reader.readLine());
                    } while (numberOfOperands > 5);
                    msg = nickName + Server.SEPARATOR + numberOfOperands;
                    send(msg, clientSide);
                    receive(clientSide);

                    // Operazione
                    System.out.print("Inserisci l'operazione [+, *, o -]: ");
                    send(reader.readLine(), clientSide);
                    receive(clientSide);

                    // Operandi
                    System.out.print("Inserisci gli operandi separati da \"" + Server.SEPARATOR + "\": ");
                    send(reader.readLine(), clientSide);
                    receive(clientSide);

                    // Ricevo risultato
                    receive(clientSide);

                    System.out.print("Inserisci Q se vuoi terminare o un carattere qualsiasi se vuoi rimanere connesso: ");
                    msg = reader.readLine();
                    send(msg, clientSide);

                    if (msg.equals("Q")) {
                        receive(clientSide);
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

    private static boolean isServerSideClosed(Socket clientSide) throws IOException {
        clientSide.setSoTimeout(2000);
        try {
            receive(clientSide);
        } catch (SocketTimeoutException e) {
            clientSide.setSoTimeout(0);
            return false;
        } catch (Exception e) {
            clientSide.setSoTimeout(0);
            return true;
        }
        clientSide.setSoTimeout(0);
        return false;
    }

    private static void send(String toSend, Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(toSend.getBytes(), 0, toSend.length());
    }

    private static String receive(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] receiveBuffer = new byte[BUFFER_SIZE];
        int bytesRead = inputStream.read(receiveBuffer);

        if (bytesRead == -1)
            throw new IOException("Connessione chiusa lato server");

        String received = new String(receiveBuffer, 0, bytesRead);
        // Devo stampare ogni singolo messaggio ricevuto
        System.out.println("Risposta server: " + received);
        return received;
    }
}
