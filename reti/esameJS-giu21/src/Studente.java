import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Studente {
    public final String mail;
    public final String matricola;

    private static final int BUFFER_SIZE = 100;

    @Override
    public String toString() {
        return "Mail: " + mail + " ; matricola: " + matricola;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Studente other) {
            return other.matricola.equals(matricola) && other.mail.equals(mail);
        }
        return false;
    }

    public Studente(String mail, String matricola) {
        this.mail = mail;
        this.matricola = matricola;
    }

    public static void main(String[] args) {
        try {
            InetAddress serverIPAddress = InetAddress.getLocalHost();
            int serverPort = Integer.parseInt(args[0]);

            InputStreamReader keyboard = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(keyboard);

            try (Socket clientSide = new Socket(serverIPAddress, serverPort)) {
                // Invio "S" al server per identificarmi come studente
                OutputStream toServer = clientSide.getOutputStream();
                toServer.write("S".getBytes(), 0, 1);

                byte[] receiveBuffer = new byte[BUFFER_SIZE];
                InputStream fromServer = clientSide.getInputStream();
                int bytesRead = fromServer.read(receiveBuffer);
                String received = new String(receiveBuffer, 0, bytesRead);
                System.out.println("Risposta server: " + received);

                System.out.print("Mail: ");
                String mail = br.readLine();
                System.out.print("Matricola: ");
                String matricola = br.readLine();

                String msg = mail + Portale.SEPARATOR + matricola;
                toServer.write(msg.getBytes(), 0, msg.length());

                receiveBuffer = new byte[BUFFER_SIZE];
                bytesRead = fromServer.read(receiveBuffer);
                received = new String(receiveBuffer, 0, bytesRead);
                System.out.println("Risposta server: " + received);

                System.out.print("ID insegnamento: ");
                String subject = br.readLine();
                toServer.write(subject.getBytes(), 0, subject.length());

                receiveBuffer = new byte[BUFFER_SIZE];
                bytesRead = fromServer.read(receiveBuffer);
                received = new String(receiveBuffer, 0, bytesRead);
                System.out.println("Risposta server: " + received);

                // Modo elegante di capire se il server ha chiuso il suo lato della connessione?
                // Per ora mi viene in mente solo questo.
                if (received.equals(Portale.NO_SPOTS_LEFT_MSG)) {
                    clientSide.close();
                    return;
                }

                System.out.print("Seleziona 1 o 2: ");
                String sel = br.readLine();
                toServer.write(sel.getBytes(), 0, sel.length());

                receiveBuffer = new byte[BUFFER_SIZE];
                bytesRead = fromServer.read(receiveBuffer);
                received = new String(receiveBuffer, 0, bytesRead);
                System.out.println("Risposta server: " + received);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
