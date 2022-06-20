package hyuk.techblog.user;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	public User login(String principal, String credential) {
		checkArgument(isNotEmpty(principal), "principal must be provided");
		checkArgument(isNotEmpty(credential), "credential must be provided");

		User user = userRepository.findByLoginId(principal)
			.orElseThrow(
				() -> new UsernameNotFoundException("Could not found user for " + principal));
		user.checkPassword(passwordEncoder, credential);
		return user;
	}

	public Optional<User> findByLoginId(String loginId) {
		checkArgument(isNotEmpty(loginId), "loginId must be provided");
		return userRepository.findByLoginId(loginId);
	}
}
