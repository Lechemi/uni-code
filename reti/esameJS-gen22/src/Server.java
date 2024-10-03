import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Server {
    private static final int BUFFER_SIZE = 100;
    public static final int ACCEPT_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 3000;
    public static final int MAX_CONNECTIONS = 4;
    public static final String SEPARATOR = ";";

    private enum Op {
        PLUS("+") {
            @Override
            double compute(double o1, double o2) {
                return o1 + o2;
            }
        },
        MULT("*") {
            @Override
            double compute(double o1, double o2) {
                return o1 * o2;
            }
        },
        MINUS("-") {
            @Override
            double compute(double o1, double o2) {
                return o1 - o2;
            }
        };

        private final String opCode;

        Op(String opCode) {
            this.opCode = opCode;
        }

        abstract double compute(double o1, double o2);

        static Op fromCode(String code) {
            for (Op op : Op.values()) {
                if (op.opCode.equals(code)) return op;
            }
            throw new IllegalArgumentException("Codice operazione non valido");
        }
    }

    public static void main(String[] args) {
        InputStreamReader keyboard = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(keyboard);
        byte[] receiveBuffer = new byte[BUFFER_SIZE];
        Map<String, SocketAddress> name2Address = new HashMap<>();

        try (ServerSocket serverSocket = new ServerSocket(0)) {
            System.out.println("Server " + serverSocket.getInetAddress()
                    + " riceve richieste di connessione sulla porta " + serverSocket.getLocalPort());

            List<Socket> serverSides = new ArrayList<>();
            int numberOfClients = 0;

            while (true) {
                try {
                    // Per un tempo ACCEPT_TIMEOUT vedo se arrivano nuovi client
                    serverSocket.setSoTimeout(ACCEPT_TIMEOUT);
                    while (numberOfClients < MAX_CONNECTIONS) {
                        Socket serverSide = serverSocket.accept();
                        String nickName = receive(serverSide);
                        if (name2Address.containsKey(nickName)) {
                            send("Nickname già preso!!!", serverSide);
                            serverSide.close();
                        } else {
                            name2Address.put(nickName, serverSide.getRemoteSocketAddress());
                            serverSides.add(serverSide);
                            send("Registrazione completata", serverSide);
                            numberOfClients++;
                        }
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("Accept timer expired");
                }

                int i = 0;
                // Itero sui client finché c'è almeno una connessione attiva
                while (numberOfClients > 0) {
                    Socket serverSide = serverSides.get(i);
                    System.out.println("Servo client " + serverSide.getInetAddress() + ":" + serverSide.getPort());

                    String msg;

                    try {
                        while (true) {
                            // Per un tempo READ_TIMEOUT vedo se il client invia il numero di operandi
                            serverSide.setSoTimeout(READ_TIMEOUT);
                            msg = receive(serverSide);

                            String[] data = msg.split(SEPARATOR);
                            String nickName = data[0];
                            int numberOfOperands = Integer.parseInt(data[1]);

                            // Identificazione del client
                            if (!name2Address.containsKey(nickName) || !name2Address.get(nickName).equals(serverSide.getRemoteSocketAddress())) {
                                // Non so identificare il client
                                throw new Exception("Identificazione non riuscita, chiudo la connessione");
                            }

                            send("Ricevuto numero di operandi", serverSide);

                            // Se il client si è identificato e ha mandato il numero di operandi, inizia l'operazione atomica
                            serverSide.setSoTimeout(0);

                            // Ricevo operazione
                            Op op = Op.fromCode(receive(serverSide));
                            send("Operazione ricevuta", serverSide);

                            // Ricevo operandi
                            msg = receive(serverSide);
                            String[] operandStrings = msg.split(SEPARATOR, numberOfOperands);
                            double[] operands = new double[numberOfOperands];
                            for (int j = 0; j < numberOfOperands; j++) {
                                operands[j] = Double.parseDouble(operandStrings[j]);
                            }
                            send("Operandi ricevuti. Calcolo risultato...", serverSide);

                            double res = operands[0];
                            for (int j = 1; j < numberOfOperands; j++) {
                                res = op.compute(res, operands[j]);
                            }

                            // Senza questo si verifica un bug strano, non so perché
                            TimeUnit.SECONDS.sleep(1);

                            send(String.valueOf(res), serverSide);

                            msg = receive(serverSide);
                            if (msg.equals("Q")) {
                                send("Chiusura connessione...", serverSide);
                                serverSide.close();
                                serverSides.remove(serverSide);
                                numberOfClients--;
                                i--; // Perché remove trasla a sinistra gli elementi di serverSides
                                break;
                            }
                        }
                    } catch (SocketTimeoutException e) {
                        System.out.println("Read timer expired");
                    } catch (Exception e) {
                        send(e.getMessage(), serverSide);
                        serverSide.close();
                        serverSides.remove(serverSide);
                        numberOfClients--;
                        i--;
                    }
                    i = numberOfClients != 0 ? (i + 1) % numberOfClients : 0;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void send(String toSend, Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(toSend.getBytes(), 0, toSend.length());
    }

    private static String receive(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] receiveBuffer = new byte[BUFFER_SIZE];
        int bytesRead = inputStream.read(receiveBuffer);
        String received = new String(receiveBuffer, 0, bytesRead);
        // Devo stampare ogni singolo messaggio ricevuto
        System.out.println("Messaggio: " + received
                + " da client " + socket.getInetAddress() + ":" + socket.getPort());
        return received;
    }

}
