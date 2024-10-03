package TCP;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        int serverPort = Integer.parseInt(args[0]);
        try {
            InetAddress serverIP = InetAddress.getLocalHost();

            try (Socket clientSide = new Socket(serverIP, serverPort)) {

                String msg = "Hello world";
                OutputStream toServer = clientSide.getOutputStream();
                toServer.write(msg.getBytes(), 0, msg.length());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
