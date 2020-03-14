package com.bridgelabz.jwt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bridgelabz.fundoonotesapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.mail.iap.Response;

import io.jsonwebtoken.lang.Assert;

@SpringBootTest
class FandooAppBackendApplicationTests {
	/**
	 * @Autowired LoginDTO token;
	 * @Autowired private UserService userService;
	 * @MockBean private UserRepository userRepository;
	 * 
	 * @Test public void loginTest() { //
	 *       when(userRepository.findAll()).thenReturn(Stream.of(new
	 *       User(28,"2020-03-05
	 *       17:53:12.649","vikkyghate12@gmail.com","Vikky",1,"Ghate","vikky777","9856321256")).collect(Collectors.toList()));
	 *       // assertEquals(1, userService.login(token).)); }
	 * 
	 * @Test void contextLoads() {
	 * 
	 *       } // public void addusers() { User user = new
	 *       User(11,"Krunal","Parate",
	 *       "kparate16@gmail.com","9860403086","kunal123",9860403086); }
	 */
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	ObjectMapper om = new ObjectMapper();

	@BeforeAll
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	@Test
	public void addUserTest() throws Exception {
		User user = new User();
		user.setEmail("kparate16@gmail.com");
		user.setFirstName("Krunal");
		user.setLastName("Parate");
		user.setPassword("kunal123");
		user.setPhoneNo(986040308);
		String jsonRequest = om.writeValueAsString(user);
		// Result Format in Row Data
		MvcResult result = (MvcResult) mockMvc
				.perform(post("/userapi/addusers").contentType(jsonRequest).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Response response = om.readValue(resultContent, Response.class);
		Assert.isTrue(response.isOK() == Boolean.TRUE);
	}
}
