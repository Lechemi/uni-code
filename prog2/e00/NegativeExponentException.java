package e00;

public class NegativeExponentException extends RuntimeException {
    public NegativeExponentException() {
        super(); // Vuol dire che chiamo il costruttore di RuntimeException così com'è
    }

    public NegativeExponentException(String msg) {
        super(msg);
    }
}
