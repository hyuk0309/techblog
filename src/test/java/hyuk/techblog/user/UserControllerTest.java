package hyuk.techblog.user;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import hyuk.techblog.jwt.Jwt;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Jwt jwt;

	@BeforeEach
	void setUp() {
		User user = new User(
			1L,
			"pang",
			"1234",
			Role.ROLE_USER);

		userRepository.save(user);
	}

	//to do password enconder 에서 문제 생김.
	@Test
	@DisplayName("login test")
	void testLoginSuccess() throws Exception {
		//given
		LoginRequest loginRequest = new LoginRequest("pang", "1234");

		//when, then
		mockMvc.perform(post("/api/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.token").exists());
	}

	@Test
	@DisplayName("인증, 인가 test")
	void testMeSuccess() throws Exception {
		//given
		String username = "pang";
		String[] roles = {"ROLE_CUSTOMER"};

		String token = jwt.sign(Jwt.Claims.from(username, roles));

		//when, then
		mockMvc.perform(get("/api/users/me")
				.header("token", token)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.token").value(equalTo(token)))
			.andExpect(jsonPath("$.username").value(equalTo(username)));
	}
}
