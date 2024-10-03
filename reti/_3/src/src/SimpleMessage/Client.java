package SimpleMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        int port = 57441;
        InetAddress serverIPAddress = InetAddress.getLocalHost();
        try (Socket clientSocket = new Socket(serverIPAddress, port)) {
            System.out.print("Inserisci il messaggio: ");
            InputStreamReader keyboard = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(keyboard);
            String toSend = br.readLine();

            // Ottengo lo stream su cui scriverò i byte del messaggio che voglio inviare
            OutputStream outputStream = clientSocket.getOutputStream();

            // Invio i byte, tutti (dallo 0 alla lunghezza del messaggio).
            outputStream.write(toSend.getBytes(), 0, toSend.length());
        }
    }
}
