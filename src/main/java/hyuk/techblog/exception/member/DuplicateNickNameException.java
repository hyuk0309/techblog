package hyuk.techblog.exception.member;

public class DuplicateNickNameException extends RuntimeException {
	public DuplicateNickNameException() {
		super();
	}

	public DuplicateNickNameException(String message) {
		super(message);
	}

	public DuplicateNickNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateNickNameException(Throwable cause) {
		super(cause);
	}

	protected DuplicateNickNameException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
