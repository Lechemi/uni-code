import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            /*
            serverSocket non è una socket qualunque: il suo unico scopo è ricevere richieste di connessione al server.
            Ovviamente si occupa delle richieste rivolte alla porta specificata come argomento al costruttore.
            Se tale porta è 0, è il SO che la sceglie automaticamente tra quelle effimere.
            */
            System.out.println("Server " + serverSocket.getInetAddress() +
                    " riceve richieste di connessione sulla porta " + serverSocket.getLocalPort());
            /*
            Nota che serverSocket.getInetAddress() stampa l'indirizzo 0.0.0.0
            È l'indirizzo "any": il server accetta richieste su una qualsiasi delle sue interfacce, sulla porta
            specificata (o assegnata dal SO).
            */

            /*
            ServerSocket.accept() è il metodo sincrono (quindi bloccante) che riceve le richieste di connessione.
            Di fatto accept() mette in attesa la ServerSocket finché non arriva una richiesta lato client. A quel punto
            restituisce la Socket (toClient) del client che ha effettuato la richiesta di connessione.
            [ovviamente se non arrivano richieste non si arriverà mai alla println]
            */
            Socket toClient = serverSocket.accept();
            System.out.println("Client " + toClient.getInetAddress() +
                    " con porta " + toClient.getPort());
        }
    }
}
