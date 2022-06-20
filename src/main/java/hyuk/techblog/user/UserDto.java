package hyuk.techblog.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserDto {

	private final String token;
	private final String username;

	public UserDto(String token, String username) {
		this.token = token;
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("token", this.token)
			.append("username", this.username)
			.toString();
	}
}
