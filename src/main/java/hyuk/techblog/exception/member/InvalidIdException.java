package hyuk.techblog.exception.member;

import hyuk.techblog.exception.Message;
import hyuk.techblog.exception.ServiceException;

public class InvalidIdException extends ServiceException {

	private final Message message;

	public InvalidIdException(Message message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message.getText();
	}
}
