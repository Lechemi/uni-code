package e04;

public class DuplicateException extends RuntimeException {
    public DuplicateException() {
        super();
    }

    public DuplicateException(String msg) {
        super(msg);
    }
}
