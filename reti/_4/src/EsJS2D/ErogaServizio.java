package EsJS2D;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ErogaServizio extends Thread {
    private final Socket serverSide;

    public ErogaServizio(Socket serverSide) {
        this.serverSide = serverSide;
    }

    @Override
    public void run() {
        final int bufferSize = 100;

        try {
            while (true) {
                byte[] receiveBuffer = new byte[bufferSize];

                // Ottengo la stream da cui provengono i dati (ossia direzione client-server)
                InputStream fromClient = serverSide.getInputStream();
                // Leggi documentazione read che fai prima (è easy)
                int numberOfBytesRead = fromClient.read(receiveBuffer);
                String received = new String(receiveBuffer, 0, numberOfBytesRead);
                System.out.println("Messaggio ricevuto: " + received);

                if (received.equals(".")) {
                    serverSide.close();
                    break;
                }

                String reply = getReply(received);
                // Ottengo lo stream su cui mandare i dati (ossia direzione server-client)
                OutputStream toClientStream = serverSide.getOutputStream();
                toClientStream.write(reply.getBytes(), 0, reply.length());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getReply(String received) {
        String[] splitData = received.split(":");
        String operation = splitData[0];
        double firstOperand = Double.parseDouble(splitData[1]);
        double secondOperand = Double.parseDouble(splitData[2]);

        return switch (operation) {
            case "+" -> String.valueOf(firstOperand + secondOperand);
            case "-" -> String.valueOf(firstOperand - secondOperand);
            case "*" -> String.valueOf(firstOperand * secondOperand);
            case "/" -> String.valueOf(firstOperand / secondOperand);
            default -> "Operazione non valida";
        };
    }
}
