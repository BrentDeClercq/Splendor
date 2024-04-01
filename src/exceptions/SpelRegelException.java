package exceptions;

public class SpelRegelException extends RuntimeException {
    public SpelRegelException() {
    }

    public SpelRegelException(String message) {
	super(message);
    }

    public SpelRegelException(String message, Throwable cause) {
	super(message, cause);
    }

    public SpelRegelException(Throwable cause) {
	super(cause);
    }

    public SpelRegelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
}
