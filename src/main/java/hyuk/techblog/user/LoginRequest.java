package hyuk.techblog.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LoginRequest {

	private String principal;
	private String credential;

	protected LoginRequest() {
	}

	public LoginRequest(String principal, String credential) {
		this.principal = principal;
		this.credential = credential;
	}

	public String getPrincipal() {
		return this.principal;
	}

	public String getCredential() {
		return this.credential;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("principal", this.principal)
			.append("credential", this.credential)
			.toString();
	}
}
