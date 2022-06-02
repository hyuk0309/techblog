package hyuk.techblog.exception;

public enum Message {
	DUPLICATE_LOGIN_ID_EXP_MSG("이미 존재하는 아이디입니다."),
	DUPLICATE_NICK_NAME_EXP_MSG("이미 존재하는 닉네임입니다."),
	INVALID_LOGIN_ID_OR_PASSWORD_EXP_MSG("아이디 또는 비밀번호가 틀렸습니다."),
	INVALID_ID_EXP_MSG("존재하지 않는 아이디입니다.");

	private final String text;

	Message(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
