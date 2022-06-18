package hyuk.techblog.user;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

	private final Jwt jwt;

	public UserController(Jwt jwt) {
		this.jwt = jwt;
	}

	@GetMapping("/users/{username}/token")
	public String getToken(@PathVariable String username) {
		String[] roles = {"CUSTOMER"};
		String token = jwt.sign(Jwt.Claims.from(username, roles));
		return token;
	}

	@GetMapping("/users/token/verify")
	public Map<String, Object> verify(@RequestHeader("token") String token) {
		return jwt.verify(token).asMap();
	}
}
