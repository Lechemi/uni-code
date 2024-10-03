package EsJS2A;

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

            System.out.print("Inserisci l'operazione [+, -, *, /]: ");
            String op = br.readLine();

            System.out.print("Inserisci gli operandi, separati da uno spazio: ");
            String[] operands = br.readLine().split(" ");

            String toSend = op + ":" + operands[0] + ":" + operands[1];

            // Ottengo la stream su cui inviare i dati (ossia direzione client-server)
            OutputStream toServer = clientSide.getOutputStream();
            // Invio toSend, direttamente convertita in array di byte (senza prima creare buffer apposito, risparmio tempo)
            toServer.write(toSend.getBytes(), 0, toSend.length());

            // Creo buffer ricezione
            int bufferSize = 100;
            byte[] buffer = new byte[bufferSize];

            // Ottengo lo stream da cui provengono i dati (ossia direzione client-server)
            InputStream fromServer = clientSide.getInputStream();
            // Leggi documentazione read che fai prima (è easy)
            int numberOfBytesRead = fromServer.read(buffer);
            String received = new String(buffer, 0, numberOfBytesRead);
            System.out.println("Messaggio ricevuto: " + received);
        }
    }
}
