import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Portale {
    private static final int BUFFER_SIZE = 100;
    public static final String SEPARATOR = ":";
    public static final String NO_SPOTS_LEFT_MSG = "Posti in presenza esauriti. Prenotazione a distanza completata";

    public static void main(String[] args) {
        Uni uni = new Uni();

        try (ServerSocket serverSocket = new ServerSocket(0)) {

            System.out.println("Server " + serverSocket.getInetAddress() +
                    " riceve richieste di connessione sulla porta " + serverSocket.getLocalPort());

            while (true) {

                Socket serverSide = serverSocket.accept();
                System.out.println("Client " + serverSide.getInetAddress() + ":" + serverSide.getPort());

                // Ricevo messaggio che identifica studente (S) oppure docente (D)
                byte[] receiveBuffer = new byte[BUFFER_SIZE];
                InputStream inputStream = serverSide.getInputStream();
                int bytesRead = inputStream.read(receiveBuffer);
                String received = new String(receiveBuffer, 0, bytesRead);
                System.out.println("Messaggio ricevuto: " + received);

                if (received.equals("S")) {
                    OutputStream toClient = serverSide.getOutputStream();
                    String msg = "Inserire mail e numero di matricola.";
                    toClient.write(msg.getBytes(), 0, msg.length());

                    receiveBuffer = new byte[BUFFER_SIZE];
                    bytesRead = inputStream.read(receiveBuffer);
                    received = new String(receiveBuffer, 0, bytesRead);
                    System.out.println("Messaggio ricevuto: " + received);

                    String[] data = received.split(Portale.SEPARATOR);
                    Studente student = new Studente(data[0], data[1]);

                    String subjects = "Selezionare insegnamento: " + uni.getSubjectList().toString();
                    toClient.write(subjects.getBytes(), 0, subjects.length());

                    receiveBuffer = new byte[BUFFER_SIZE];
                    bytesRead = inputStream.read(receiveBuffer);
                    received = new String(receiveBuffer, 0, bytesRead);
                    System.out.println("Messaggio ricevuto: " + received);

                    Subject subject = Subject.valueOf(received);

                    if (uni.freeSpots(subject) > 0) {
                        // Ci sono posti in aula
                        msg = "Selezionare prenotazione in presenza (1) o a distanza (2)";
                        toClient.write(msg.getBytes(), 0, msg.length());

                        receiveBuffer = new byte[BUFFER_SIZE];
                        bytesRead = inputStream.read(receiveBuffer);
                        received = new String(receiveBuffer, 0, bytesRead);
                        System.out.println("Messaggio ricevuto: " + received);

                        if (received.equals("1")) {
                            int seat = uni.bookLiveLesson(subject, student);
                            msg = "Prenotazione in presenza completata. Posto: " + seat;
                            toClient.write(msg.getBytes(), 0, msg.length());
                            serverSide.close();
                        } else if (received.equals("2")) {
                            uni.bookRemoteLesson(subject, student);
                            msg = "Prenotazione a distanza completata";
                            toClient.write(msg.getBytes(), 0, msg.length());
                            serverSide.close();
                        } else {
                            // Errore
                        }

                    } else {
                        // Non ci sono posti in aula
                        uni.bookRemoteLesson(subject, student);
                        msg = NO_SPOTS_LEFT_MSG;
                        toClient.write(msg.getBytes(), 0, msg.length());
                        serverSide.close();
                    }

                } else if (received.equals("D")) {
                    OutputStream toClient = serverSide.getOutputStream();
                    String msg = "Inserire mail";
                    toClient.write(msg.getBytes(), 0, msg.length());

                    receiveBuffer = new byte[BUFFER_SIZE];
                    bytesRead = inputStream.read(receiveBuffer);
                    received = new String(receiveBuffer, 0, bytesRead);
                    System.out.println("Messaggio ricevuto: " + received);

                    String subjects = "Selezionare insegnamento: " + uni.getSubjectList().toString();
                    toClient.write(subjects.getBytes(), 0, subjects.length());

                    receiveBuffer = new byte[BUFFER_SIZE];
                    bytesRead = inputStream.read(receiveBuffer);
                    received = new String(receiveBuffer, 0, bytesRead);
                    System.out.println("Messaggio ricevuto: " + received);

                    Subject subject = Subject.valueOf(received);

                    // Mando elenco studenti in presenza
                    msg = "Elenco studenti in presenza: " + uni.getBookedStudentsLive(subject).toString();
                    toClient.write(msg.getBytes(), 0, msg.length());

                    // Ricevo e stampo ack
                    receiveBuffer = new byte[BUFFER_SIZE];
                    bytesRead = inputStream.read(receiveBuffer);
                    received = new String(receiveBuffer, 0, bytesRead);
                    System.out.println("Messaggio ricevuto: " + received);

                    // Mando elenco studenti a distanza
                    msg = "Elenco studenti a distanza: " + uni.getBookedStudentsRemote(subject).toString();
                    toClient.write(msg.getBytes(), 0, msg.length());

                    // Ricevo e stampo ack
                    receiveBuffer = new byte[BUFFER_SIZE];
                    bytesRead = inputStream.read(receiveBuffer);
                    received = new String(receiveBuffer, 0, bytesRead);
                    System.out.println("Messaggio ricevuto: " + received);

                    serverSide.close();
                } else {
                    // Errore
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
