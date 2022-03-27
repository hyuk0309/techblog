package hyuk.techblog.exception.category;

public class DuplicateCategoryException extends RuntimeException {

	public DuplicateCategoryException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicateCategoryException() {
	}

	public DuplicateCategoryException(String message) {
		super(message);
	}

	public DuplicateCategoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateCategoryException(Throwable cause) {
		super(cause);
	}
}
