package hyuk.techblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import hyuk.techblog.jwt.Jwt;
import hyuk.techblog.jwt.JwtAuthenticationFilter;
import hyuk.techblog.jwt.JwtAuthenticationProvider;
import hyuk.techblog.user.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

	private final JwtConfigure jwtConfigure;

	public WebSecurityConfigure(JwtConfigure jwtConfigure) {
		this.jwtConfigure = jwtConfigure;
	}

	@Bean
	public Jwt jwt() {
		return new Jwt(
			jwtConfigure.getIssuer(),
			jwtConfigure.getClientSecret(),
			jwtConfigure.getExpirySeconds());
	}

	@Bean
	public JwtAuthenticationProvider jwtAuthenticationProvider(UserService userService, Jwt jwt) {
		return new JwtAuthenticationProvider(jwt, userService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/*
	to do BCryptPasswordEncoder로 바꾸기
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		Jwt jwt = getApplicationContext().getBean(Jwt.class);
		return new JwtAuthenticationFilter(jwtConfigure.getHeader(), jwt);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/api/users/me").hasAnyRole("CUSTOMER", "ADMIN")
			.anyRequest().permitAll()
			.and()

			.csrf()
			.disable()

			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()

			.addFilterAfter(jwtAuthenticationFilter(), SecurityContextPersistenceFilter.class);
	}
}
