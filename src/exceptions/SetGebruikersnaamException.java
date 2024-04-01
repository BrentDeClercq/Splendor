package exceptions;

public class SetGebruikersnaamException extends RuntimeException {
    public SetGebruikersnaamException() {
    }

    public SetGebruikersnaamException(String message) {
	super(message);
    }

    public SetGebruikersnaamException(String message, Throwable cause) {
	super(message, cause);
    }

    public SetGebruikersnaamException(Throwable cause) {
	super(cause);
    }

    public SetGebruikersnaamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
}
