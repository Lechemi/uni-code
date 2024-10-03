import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Server {
    private static final int BUFFER_SIZE = 100;
    public static final String SEPARATOR = ";";

    enum Figure {
        SQUARE {
            @Override
            void handle(Socket serverSide) throws IOException {
                send("Inserisci la misura del lato: ", serverSide);
                double l = 0;
                while (true) {
                    try {
                        l = Double.parseDouble(receive(serverSide));
                        send("OK" + SEPARATOR + "Input acquisito. Calcolo...", serverSide);
                        break;
                    } catch (NumberFormatException e) {
                        send("ERR" + SEPARATOR + "Input non valido. Inserisci di nuovo:", serverSide);
                    }
                }

                double perimeter = l * 4;
                double area = l * l;

                send("Perimetro = " + perimeter + " ; area = " + area, serverSide);
            }
        },
        RECTANGLE {
            @Override
            void handle(Socket serverSide) throws IOException {
                send("Inserisci la misura dei due lati [separati da " + SEPARATOR + "]: ", serverSide);
                double l1 = 0;
                double l2 = 0;
                while (true) {
                    try {
                        String[] data = receive(serverSide).split(SEPARATOR);
                        if (data.length != 2) throw new IllegalArgumentException();
                        l1 = Double.parseDouble(data[0]);
                        l2 = Double.parseDouble(data[1]);
                        send("OK" + SEPARATOR + "Input acquisito. Calcolo...", serverSide);
                        break;
                    } catch (IllegalArgumentException e) {
                        send("ERR" + SEPARATOR + "Input non valido. Inserisci di nuovo:", serverSide);
                    }
                }

                double perimeter = (l1 + l2) * 2;
                double area = l1 * l2;

                send("Perimetro = " + perimeter + " ; area = " + area, serverSide);
            }
        },
        CIRCLE {
            @Override
            void handle(Socket serverSide) throws IOException {
                send("Inserisci la misura del raggio: ", serverSide);
                double r = 0;
                while (true) {
                    try {
                        r = Double.parseDouble(receive(serverSide));
                        send("OK" + SEPARATOR + "Input acquisito. Calcolo...", serverSide);
                        break;
                    } catch (NumberFormatException e) {
                        send("ERR" + SEPARATOR + "Input non valido. Inserisci di nuovo:", serverSide);
                    }
                }

                double perimeter = 2 * PI * r;
                double area = r * r * PI;

                send("Perimetro = " + perimeter + " ; area = " + area, serverSide);
            }
        },
        TRIANGLE {
            @Override
            void handle(Socket serverSide) throws IOException {
                send("Inserisci la misura dei due cateti [separati da " + SEPARATOR + "]: ", serverSide);
                double l1 = 0;
                double l2 = 0;
                while (true) {
                    try {
                        String[] data = receive(serverSide).split(SEPARATOR);
                        if (data.length != 2) throw new IllegalArgumentException();
                        l1 = Double.parseDouble(data[0]);
                        l2 = Double.parseDouble(data[1]);
                        send("OK" + SEPARATOR + "Input acquisito. Calcolo...", serverSide);
                        break;
                    } catch (IllegalArgumentException e) {
                        send("ERR" + SEPARATOR + "Input non valido. Inserisci di nuovo:", serverSide);
                    }
                }

                double perimeter = l1 + l2 + Math.sqrt(l1 * l1 + l2 * l2);
                double area = (l1 * l2) / 2;

                send("Perimetro = " + perimeter + " ; area = " + area, serverSide);
            }
        };

        public static final double PI = 3.14;

        abstract void handle(Socket serverSide) throws IOException;
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            System.out.println("Server " + serverSocket.getInetAddress()
                    + " in ascolto su porta " + serverSocket.getLocalPort());

            while (true) {
                Socket serverSide = serverSocket.accept();
                System.out.println("Connessione aperta con client " + serverSide.getRemoteSocketAddress());

                while (true) {
                    send("Elenco figure: " + Arrays.toString(Figure.values()), serverSide);

                    Figure figure;
                    String msg;
                    while (true) {
                        msg = receive(serverSide);
                        try {
                            figure = Figure.valueOf(msg);
                            send("OK" + SEPARATOR + "Figura memorizzata", serverSide);
                            break;
                        } catch (IllegalArgumentException e) {
                            send("ERR" + SEPARATOR + "Figura non valida", serverSide);
                        }
                    }

                    // Altrimenti si bugga (?)
                    TimeUnit.MILLISECONDS.sleep(500);
                    figure.handle(serverSide);

                    TimeUnit.MILLISECONDS.sleep(500);
                    send("Invia un carattere qualsiasi se vuoi continuare, oppure Q se vuoi terminare: ", serverSide);
                    msg = receive(serverSide);
                    if (msg.equals("Q")) {
                        serverSide.close();
                        break;
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void send(String toSend, Socket socket) throws IOException {
        OutputStream toClient = socket.getOutputStream();
        toClient.write(toSend.getBytes(), 0, toSend.length());
    }

    private static String receive(Socket socket) throws IOException {
        byte[] receiveBuffer = new byte[BUFFER_SIZE];
        InputStream fromClient = socket.getInputStream();
        int bytesRead = fromClient.read(receiveBuffer);
        if (bytesRead == -1) throw new IOException();
        String received = new String(receiveBuffer, 0, bytesRead);
        System.out.println("Ricevuto messaggio: " + received);
        return received;
    }
}
