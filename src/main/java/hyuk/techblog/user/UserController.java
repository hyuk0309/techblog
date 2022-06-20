package hyuk.techblog.user;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hyuk.techblog.jwt.JwtAuthentication;
import hyuk.techblog.jwt.JwtAuthenticationToken;

@RestController
@RequestMapping("/api")
public class UserController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;

	public UserController(UserService userService, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}

	/**
	 * 사용자 로그인
	 */
	@PostMapping(path = "/users/login")
	public UserDto login(@RequestBody LoginRequest loginRequest) {
		JwtAuthenticationToken authToken =
			new JwtAuthenticationToken(loginRequest.getPrincipal(), loginRequest.getCredential());
		Authentication resultToken = this.authenticationManager.authenticate(authToken);
		JwtAuthentication authentication = (JwtAuthentication)resultToken.getPrincipal();
		User user = (User)resultToken.getDetails();
		return new UserDto(authentication.token, authentication.username);
	}

	@GetMapping(path = "/users/me")
	public UserDto me(@AuthenticationPrincipal JwtAuthentication authentication) {
		return userService.findByLoginId(authentication.username)
			.map(user ->
				new UserDto(authentication.token, authentication.username))
			.orElseThrow(() -> new IllegalArgumentException("Could not found user for " + authentication.username));
	}

}
