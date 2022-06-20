package hyuk.techblog.jwt;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;
	private String credential;

	public JwtAuthenticationToken(String principal, String credential) {
		super(null);
		super.setAuthenticated(false);

		this.principal = principal;
		this.credential = credential;
	}

	JwtAuthenticationToken(Object principal, String credential,
		Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		super.setAuthenticated(true);

		this.principal = principal;
		this.credential = credential;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public String getCredentials() {
		return this.credential;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		if (super.isAuthenticated()) {
			throw new IllegalArgumentException(
				"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}
		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.credential = null;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("principal", this.principal)
			.append("credential", this.credential)
			.toString();
	}
}
