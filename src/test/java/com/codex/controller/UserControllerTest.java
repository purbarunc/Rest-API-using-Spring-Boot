package com.codex.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.codex.controller.exceptionhandler.CustomExceptionHandler;
import com.codex.dto.UserRequest;
import com.codex.exception.UserNotFoundException;
import com.codex.model.User;
import com.codex.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class UserControllerTest {
	private static final String ENDPOINT_GET_ALL_USERS = "/users";
	private static final String ENDPOINT_CREATE_USER = "/user";
	private static final String ENDPOINT_GET_USER = "/user";
	private static final String ENDPOINT_DELETE_USER = "/user";

	@InjectMocks
	private UserController userController;

	@MockBean
	private UserService userService;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	public void testSetup() {
		MockitoAnnotations.initMocks(this);
		objectMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new CustomExceptionHandler())
				.build();
	}

	@AfterEach
	public void testTearDown() {
		mockMvc = null;
		userController = null;
		userService = null;
	}

	@Test
	@DisplayName("allUsers() returns response status 200 in successful service call")
	void allUsersReturn200OnSuccessfulServiceCall() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_GET_ALL_USERS)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	@DisplayName("createUser() returns response status 201 on successful service call")
	void testCreateUserReturns201OnSuccessfulServiceCall() throws Exception {
		User userRequest = getUserRequest();
		String requestBody = objectMapper.writeValueAsString(userRequest);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_CREATE_USER)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.create(any())).thenReturn(getUserRequest());
		mockMvc.perform(request).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("createUser() returns Data")
	void testCreateUserReturnsData() throws Exception {
		User userRequest = getUserRequest();
		String requestBody = objectMapper.writeValueAsString(userRequest);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_CREATE_USER)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.create(any())).thenReturn(getUserRequest());
		String actualResponse = mockMvc.perform(request).andReturn().getResponse().getContentAsString();
		assertEquals(requestBody, actualResponse);
	}

	@Test
	@DisplayName("createUser() returns response status 400 on invalid request")
	void createUserReturns400OnInvalidRequest() throws Exception {
		UserRequest requestData = new UserRequest();
		String requestBody = objectMapper.writeValueAsString(requestData);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_CREATE_USER)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.create(any())).thenReturn(new User());
		mockMvc.perform(request).andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("getUser() returns response status 200 on successful service call")
	void getUserReturns200OnSuccessfulServiceCall() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_GET_USER)
				.contentType(MediaType.APPLICATION_JSON_VALUE).queryParam("id", "10")
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.findById(anyInt())).thenReturn(getUserRequest());
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	@DisplayName("getUser() returns response status 404 when user is not found")
	void getUserReturns404whenUserIsNotFound() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_GET_USER)
				.contentType(MediaType.APPLICATION_JSON_VALUE).queryParam("id", "1")
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.findById(anyInt())).thenThrow(UserNotFoundException.class);
		mockMvc.perform(request).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("getUser() returns response status 500 when some exception occurs")
	void getUserReturns500whenSomeExceptionOccurs() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_GET_USER)
				.contentType(MediaType.APPLICATION_JSON_VALUE).queryParam("id", "1")
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.findById(anyInt())).thenThrow(RuntimeException.class);
		mockMvc.perform(request).andExpect(status().isInternalServerError());
	}

	@Test
	@DisplayName("deleteUser() returns response status 200 on successful service call")
	void testDeleteUserReturns200OnSuccessfulServiceCall() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(ENDPOINT_DELETE_USER)
				.queryParam("id", "5").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	@DisplayName("deleteUser() returns 404 when data not found")
	void testDeleteUserReturns404OnWhenDataNotFound() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(ENDPOINT_DELETE_USER)
				.queryParam("id", "5").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		doThrow(EmptyResultDataAccessException.class).when(userService).delete(anyInt());
		mockMvc.perform(request).andExpect(status().isNotFound());
	}

	private User getUserRequest() {
		User user = new User();
		user.setName("Ashish");
		user.setAge(35);
		user.setCity("Kota");
		return user;
	}

}
