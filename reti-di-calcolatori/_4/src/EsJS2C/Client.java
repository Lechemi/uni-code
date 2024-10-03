package EsJS2C;
import java.io.*;

import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        /*
            In questo caso l'indirizzo IP del server è il mio indirizzo IP (LocalHost) perché sto facendo server
            e client sulla stessa macchina. NB: non è 127.0.0.1, è proprio il mio indirizzo in rete.
        */
        InetAddress serverIPAddress = InetAddress.getLocalHost();
        int serverPort = Integer.parseInt(args[0]);

        InputStreamReader keyboard = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(keyboard);

        // Il seguente costruttore effettua anche la connect() col server, quindi esegue il 3-way handshake
        try (Socket clientSide = new Socket(serverIPAddress, serverPort)) {

            // Qua dovrei ricevere il via dal server

            while (true) {
                System.out.print("Inserisci l'operazione [+, -, *, /, oppure .]: ");
                String op = br.readLine();

                OutputStream toServerStream = clientSide.getOutputStream();

                if (op.equals(".")) {
                    toServerStream.write(op.getBytes(), 0, op.length());
                    clientSide.close();
                    return;
                }

                System.out.print("Inserisci gli operandi, separati da uno spazio: ");
                String[] operands = br.readLine().split(" ");

                String toSend = op + ":" + operands[0] + ":" + operands[1];

                toServerStream.write(toSend.getBytes(), 0, toSend.length());

                int bufferSize = 100;
                byte[] receiveBuffer = new byte[bufferSize];

                // Ottengo lo stream da cui provengono i dati (ossia direzione client-server)
                InputStream fromServerStream = clientSide.getInputStream();
                // Leggi documentazione read che fai prima (è easy)
                int numberOfBytesRead = fromServerStream.read(receiveBuffer);
                String received = new String(receiveBuffer, 0, numberOfBytesRead);
                System.out.println("Messaggio ricevuto: " + received);
            }
        }
    }
}
