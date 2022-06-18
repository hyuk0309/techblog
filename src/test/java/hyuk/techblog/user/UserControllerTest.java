package hyuk.techblog.user;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@Import(Config.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserController userController;

	@Autowired
	private Jwt jwt;

	@Test
	@DisplayName("Jwt token 생성 테스트")
	void testGetTokenSuccess() throws Exception {
		//given when then
		mockMvc.perform(get("/api/users/{username}/token", "pang"))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Jwt token 검증 테스트")
	void testVerifySuccess() throws Exception {
		//given
		String username = "pang";
		String[] roles = {"CUSTOMER"};
		String token = jwt.sign(Jwt.Claims.from(username, roles));

		//when then
		mockMvc.perform(get("/api/users/token/verify")
				.header("token", token))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username").value(equalTo(username)))
			.andExpect(jsonPath("$.roles.[0]").value(equalTo(roles[0])));
	}
}
