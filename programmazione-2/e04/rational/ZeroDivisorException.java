package e04.rational;

public class ZeroDivisorException extends RuntimeException {
    public ZeroDivisorException(){
        super();
    }

    public ZeroDivisorException(String msg){
        super(msg);
    }
}
