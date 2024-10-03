import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class InetSocketAddressTest {
    public static void main(String[] args) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();

            /*
            Ora creo un indirizzo di livello 4, composto da indirizzo IP (in qs caso il mio) e numero di porta.
            NB: 0 non è un numero di porta valido, ma è un'etichetta per dire al SO: "dammi una porta effimera".
            Comunque d'ora in poi SOLO porte effimere (niente well-known o che).
            */
            InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, 0);
        } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        }
    }
}