import java.net.InetAddress;
import java.net.UnknownHostException;

public class Indirizzi {

    public static String byteArrayAsString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')).append(".");
        return sb.deleteCharAt(sb.length()-1).toString();
    }

    public static void main(String[] args) {
        String name = "www.google.it";

        try {
            // Ricavo l'indirizzo numerico corrispondente al nome simbolico
            InetAddress address = InetAddress.getByName(name);

            // Se voglio ottenere l'indirizzo IP di questo host, basta fare address = InetAddress.getLocalHost()

            System.out.println("Indirizzo numerico come stringa: " + address.getHostAddress());

            // Ottengo i byte che compongono l'indirizzo (di fatto getAddress restituisce un array di byte)
            byte[] byteAddress = address.getAddress();

            // Stampo byteAddress
            // Se usassi Arrays.toString(byteAddress), i byte verrebbero automaticamente convertiti nei corrispondenti valori decimali
            System.out.println("Indirizzo numerico in byte: " + byteArrayAsString(byteAddress));

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}