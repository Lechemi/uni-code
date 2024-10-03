import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetAllByName {
    public static void main(String[] args) {
        String name = "www.youtube.com";

        try {
            // Ottengo tutti gli indirizzi associati al nome simbolico name
            InetAddress[] addresses = InetAddress.getAllByName(name);

            for (InetAddress address : addresses)
                System.out.println("Indirizzo: " + address.getHostName() + " : " + address.getHostAddress());

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
