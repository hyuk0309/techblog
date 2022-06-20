package hyuk.techblog.jwt;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.StringUtils.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JwtAuthentication {

	public final String token;
	public final String username;

	public JwtAuthentication(String token, String username) {
		checkArgument(isNotEmpty(token), "token must be provided");
		checkArgument(isNotEmpty(username), "username must be provided");

		this.token = token;
		this.username = username;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("token", token)
			.append("username", username)
			.toString();
	}
}
