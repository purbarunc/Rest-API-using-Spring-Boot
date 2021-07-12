package com.codex.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.codex.controller.exceptionhandler.CustomExceptionHandler;
import com.codex.dto.UserPostRequest;
import com.codex.dto.UserRequest;
import com.codex.exception.UserNotFoundException;
import com.codex.mapper.UserMapper;
import com.codex.mapper.UserPostMapper;
import com.codex.model.Post;
import com.codex.model.User;
import com.codex.service.PostService;
import com.codex.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("local")
class UserControllerTest {
	private static final String ENDPOINT_GET_ALL_USERS = "/users";
	private static final String ENDPOINT_CREATE_USER = "/user";
	private static final String ENDPOINT_GET_USER = "/user";
	private static final String ENDPOINT_DELETE_USER = "/user";
	private static final String ENDPOINT_UPDATE_USER = "/user";
	private static final String ENDPOINT_GET_USERPOSTS = "/user/posts";
	private static final String ENDPOINT_CREATE_USERPOSTS = "/user/posts";

	@InjectMocks
	private UserController userController;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private UserService userService;

	@Mock
	private PostService postService;
	
	@Mock
	private UserMapper userMapper;

	@Mock
	private UserPostMapper userPostMapper;
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	public void testSetup() {
		MockitoAnnotations.openMocks(this);
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
		UserRequest userRequest = getUserRequest();
		String requestBody = objectMapper.writeValueAsString(userRequest);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_CREATE_USER)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.create(any())).thenReturn(getUser());
		mockMvc.perform(request).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("createUser() returns Data")
	void testCreateUserReturnsData() throws Exception {
		UserRequest userRequest = getUserRequest();
		String requestBody = objectMapper.writeValueAsString(userRequest);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_CREATE_USER)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.create(any())).thenReturn(getUser());
		String actualResponse = mockMvc.perform(request).andReturn().getResponse().getContentAsString();
		assertEquals(requestBody, actualResponse);
	}

	@Test
	@DisplayName("createUser() returns response status 400 on invalid request")
	void createUserReturns400OnInvalidRequest() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_CREATE_USER)
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.create(any())).thenReturn(new User());
		mockMvc.perform(request).andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("getUser() returns response status 200 on successful service call")
	void getUserReturns200OnSuccessfulServiceCall() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_GET_USER)
				.contentType(MediaType.APPLICATION_JSON_VALUE).queryParam("id", "10")
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.findById(anyInt())).thenReturn(getUser());
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
	void deleteUserReturns200OnSuccessfulServiceCall() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(ENDPOINT_DELETE_USER)
				.queryParam("id", "5").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	@DisplayName("deleteUser() returns 404 when data not found")
	void deleteUserReturns404OnWhenDataNotFound() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(ENDPOINT_DELETE_USER)
				.queryParam("id", "5").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		doThrow(EmptyResultDataAccessException.class).when(userService).delete(anyInt());
		mockMvc.perform(request).andExpect(status().isNotFound());
	}

	@Test
	void updateUserReturns200OnSuccessfulServiceCall() throws Exception {
		UserRequest userRequest = getUserRequest();
		String requestBody = objectMapper.writeValueAsString(userRequest);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(ENDPOINT_UPDATE_USER).queryParam("id", "5")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	void allPostsReturns200OnSuccessfulServiceCall() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_GET_USERPOSTS).queryParam("id", "5")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.findById(anyInt()).getPosts()).thenReturn(getPostList());
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	void createUserPostReturns200OnSuccessfulServiceCall() throws Exception {
		UserPostRequest userPostRequest = getUserPostRequest();
		String requestBody = objectMapper.writeValueAsString(userPostRequest);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_CREATE_USERPOSTS)
				.queryParam("id", "5").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.findById(anyInt())).thenReturn(getUser());
		when(userPostMapper.convertToEntity(any())).thenReturn(getPost());
		when(postService.create(any())).thenReturn(getPost());
		mockMvc.perform(request).andExpect(status().isCreated());
	}
	
	@Test
	void createUserPostReturnsNull() throws Exception {
		UserPostRequest userPostRequest = getUserPostRequest();
		String requestBody = objectMapper.writeValueAsString(userPostRequest);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_CREATE_USERPOSTS)
				.queryParam("id", "5").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(userService.findById(anyInt())).thenReturn(null);
		mockMvc.perform(request).andExpect(status().isNotFound());
	}

	private Post getPost() {
		Post post=new Post();
		post.setId(1);
		post.setUserPosts("Dummy Post");
		return post;
	}

	private UserPostRequest getUserPostRequest() {
		UserPostRequest userPostRequest = new UserPostRequest();
		userPostRequest.setUserPosts("Test");
		return userPostRequest;
	}

	private List<Post> getPostList() {
		List<Post> listOfPosts = new ArrayList<>();
		Post post = new Post();
		post.setId(1);
		post.setUserPosts("Test Post");
		listOfPosts.add(post);
		return listOfPosts;
	}

	private UserRequest getUserRequest() {
		UserRequest userRequest = new UserRequest();
		userRequest.setName("Ashish");
		userRequest.setAge(35);
		userRequest.setCity("Kota");
		return userRequest;
	}

	private User getUser() {
		User user = new User();
		user.setName("Ashish");
		user.setAge(35);
		user.setCity("Kota");
		return user;
	}

}
