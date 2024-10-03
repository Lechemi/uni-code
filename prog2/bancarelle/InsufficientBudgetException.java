package bancarelle;

public class InsufficientBudgetException extends Exception {
    public InsufficientBudgetException() {
        super();
    }

    public InsufficientBudgetException(final String msg) {
        super(msg);
    }
}

