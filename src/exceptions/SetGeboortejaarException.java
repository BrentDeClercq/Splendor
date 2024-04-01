package exceptions;

public class SetGeboortejaarException extends RuntimeException {
    public SetGeboortejaarException() {
    }

    public SetGeboortejaarException(String message) {
	super(message);
    }

    public SetGeboortejaarException(String message, Throwable cause) {
	super(message, cause);
    }

    public SetGeboortejaarException(Throwable cause) {
	super(cause);
    }

    public SetGeboortejaarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
}
