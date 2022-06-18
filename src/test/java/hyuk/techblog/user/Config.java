package hyuk.techblog.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hyuk.techblog.config.JwtConfigure;

@Configuration
public class Config {

	@Bean
	public JwtConfigure jwtConfigure() {
		return new JwtConfigure();
	}

	@Bean
	public Jwt jwt(JwtConfigure jwtConfigure) {
		return new Jwt(
			jwtConfigure().getIssuer(),
			jwtConfigure().getClientSecret(),
			jwtConfigure().getExpirySeconds()
		);
	}
}
