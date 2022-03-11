package hyuk.techblog.exception.member;

public class NonExistLoginIdException extends RuntimeException {
	public NonExistLoginIdException() {
		super();
	}

	public NonExistLoginIdException(String message) {
		super(message);
	}

	public NonExistLoginIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonExistLoginIdException(Throwable cause) {
		super(cause);
	}

	protected NonExistLoginIdException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
