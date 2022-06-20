package hyuk.techblog.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "login_id")
	private String loginId;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	@Enumerated(value = EnumType.STRING)
	private Role role;

	protected User() {
	}

	public User(Long id, String loginId, String password, Role role) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.role = role;
	}

	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.name()));
		return authorities;
	}

	public void checkPassword(PasswordEncoder passwordEncoder, String credential) {
		if (!passwordEncoder.matches(credential, password)) {
			throw new IllegalArgumentException("Bad credential");
		}
	}

	public String getLoginId() {
		return loginId;
	}

}
