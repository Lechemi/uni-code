import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Docente {
    public final String mail;

    private static final int BUFFER_SIZE = 100;

    public Docente(String mail) {
        this.mail = mail;
    }

    public static void main(String[] args) {
        try {
            InetAddress serverIPAddress = InetAddress.getLocalHost();
            int serverPort = Integer.parseInt(args[0]);

            InputStreamReader keyboard = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(keyboard);

            try (Socket clientSide = new Socket(serverIPAddress, serverPort)) {
                // Invio "D" al server per identificarmi come studente
                OutputStream toServer = clientSide.getOutputStream();
                toServer.write("D".getBytes(), 0, 1);

                byte[] receiveBuffer = new byte[BUFFER_SIZE];
                InputStream fromServer = clientSide.getInputStream();
                int bytesRead = fromServer.read(receiveBuffer);
                String received = new String(receiveBuffer, 0, bytesRead);
                System.out.println("Risposta server: " + received);

                System.out.print("Mail: ");
                String mail = br.readLine();

                toServer.write(mail.getBytes(), 0, mail.length());

                receiveBuffer = new byte[BUFFER_SIZE];
                bytesRead = fromServer.read(receiveBuffer);
                received = new String(receiveBuffer, 0, bytesRead);
                System.out.println("Risposta server: " + received);

                System.out.print("ID insegnamento: ");
                String subject = br.readLine();
                toServer.write(subject.getBytes(), 0, subject.length());

                // Ricevo elenco studenti in presenza
                receiveBuffer = new byte[BUFFER_SIZE];
                bytesRead = fromServer.read(receiveBuffer);
                received = new String(receiveBuffer, 0, bytesRead);
                System.out.println("Risposta server: " + received);

                String msg = "Elenco ricevuto";
                toServer.write(msg.getBytes(), 0, msg.length());

                // Ricevo elenco studenti a distanza
                receiveBuffer = new byte[BUFFER_SIZE];
                bytesRead = fromServer.read(receiveBuffer);
                received = new String(receiveBuffer, 0, bytesRead);
                System.out.println("Risposta server: " + received);

                toServer.write(msg.getBytes(), 0, msg.length());
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
