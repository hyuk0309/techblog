package hyuk.techblog.config;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtConfigureTest {

	@Autowired
	private JwtConfigure jwtConfigure;

	@Test
	void testJwtConfigCreate() {
		//given, when, then
		assertThat(jwtConfigure.getHeader()).isEqualTo("token");
		assertThat(jwtConfigure.getIssuer()).isEqualTo("issuer");
		assertThat(jwtConfigure.getClientSecret()).isEqualTo(
			"EENY5W0eegTf1naQB2eDeyCLl5kRS2b8xa5c4qLdS0hmVjtbvo8tOyhPMcAmtPuQ");
		assertThat(jwtConfigure.getExpirySeconds()).isEqualTo(60);
	}
}
