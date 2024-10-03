import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    /*
        NB: Prima di eseguire questo main, devi eseguire il main di ServerSide, così che il server si possa mettere
        in attesa di ricevere richieste di connessione.
        Se non lo fai, ti becchi un'eccezione.
    */
    public static void main(String[] args) throws IOException {
        // devi assegnare a serverPort il numero di porta che stampa il main di ServerSide, scelto dal SO.
        int serverPort = 52935;

        /*
            In questo caso l'indirizzo IP del server è il mio indirizzo IP (LocalHost) perché sto facendo server
            e client sulla stessa macchina. NB: non è 127.0.0.1
        */
        InetAddress serverIPAddress = InetAddress.getLocalHost();

        InetSocketAddress serverSocketAddress = new InetSocketAddress(serverIPAddress, serverPort);

        try (Socket clientSocket = new Socket()) {
            /*
                Connetto clientSocket al server che si trova a serverSocketAddress (3-way handshake).
                Alternativamente potevo fare:
                Socket clientSocket = new Socket(serverIPAddress, serverPort)
                Questo costruttore effettua automaticamente anche la connect().
            */
            clientSocket.connect(serverSocketAddress);
            System.out.println("Porta locale (della mia socket): " + clientSocket.getLocalPort());
            System.out.println("Indirizzo IP server: " + clientSocket.getInetAddress()
                    + " in ascolto sulla porta " + clientSocket.getPort());
        }
    }
}
