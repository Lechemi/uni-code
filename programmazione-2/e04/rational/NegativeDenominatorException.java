package e04.rational;

public class NegativeDenominatorException extends RuntimeException {
    public NegativeDenominatorException(){
        super();
    }

    public NegativeDenominatorException(String msg) {
        super(msg);
    }
}
