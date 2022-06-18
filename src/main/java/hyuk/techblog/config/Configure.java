package hyuk.techblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hyuk.techblog.user.Jwt;

@Configuration
public class Configure {

	@Bean
	public Jwt jwt(JwtConfigure jwtConfigure) {
		return new Jwt(
			jwtConfigure.getIssuer(),
			jwtConfigure.getClientSecret(),
			jwtConfigure.getExpirySeconds()
		);
	}
}
