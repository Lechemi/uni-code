package bancarelle;

public class MissingRequiredToysException extends Exception{
    public MissingRequiredToysException() {
        super();
    }

    public MissingRequiredToysException(final String msg) {
        super(msg);
    }
}

