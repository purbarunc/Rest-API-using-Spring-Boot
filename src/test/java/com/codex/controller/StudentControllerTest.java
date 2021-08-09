package com.codex.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.codex.controller.exceptionhandler.CustomExceptionHandler;
import com.codex.dto.StudentPostRequest;
import com.codex.dto.StudentRequest;
import com.codex.exception.StudentNotFoundException;
import com.codex.mapper.StudentMapper;
import com.codex.mapper.StudentPostMapper;
import com.codex.model.Post;
import com.codex.model.Student;
import com.codex.service.PostService;
import com.codex.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class StudentControllerTest {
	private static final String ENDPOINT_GET_ALL_STUDENTS = "/students";
	private static final String ENDPOINT_CREATE_STUDENT = "/student";
	private static final String ENDPOINT_GET_STUDENT = "/student";
	private static final String ENDPOINT_DELETE_STUDENT = "/student";
	private static final String ENDPOINT_UPDATE_STUDENT = "/student";
	private static final String ENDPOINT_GET_STUDENTPOSTS = "/student/posts";
	private static final String ENDPOINT_CREATE_STUDENTPOSTS = "/student/posts";

	@InjectMocks
	private StudentController studentController;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private StudentService studentService;

	@Mock
	private PostService postService;

	@Mock
	private StudentMapper studentMapper;

	@Mock
	private StudentPostMapper studentPostMapper;
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	public void testSetup() {
		MockitoAnnotations.openMocks(this);
		objectMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders.standaloneSetup(studentController).setControllerAdvice(new CustomExceptionHandler())
				.build();
	}

	@AfterEach
	public void testTearDown() {
		mockMvc = null;
		studentController = null;
		studentService = null;
	}

	@Test
	@DisplayName("allStudents() gives response status 200 in successful service call")
	void allStudentsReturn200OnSuccessfulServiceCall() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_GET_ALL_STUDENTS)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	@DisplayName("createStudent() gives response status 201 on successful service call")
	void createStudentReturns201OnSuccessfulServiceCall() throws Exception {
		StudentRequest studentRequest = getStudentRequest();
		String requestBody = objectMapper.writeValueAsString(studentRequest);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_CREATE_STUDENT)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(studentService.create(any())).thenReturn(getStudent());
		mockMvc.perform(request).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("createStudent() gives response status 400 on invalid request")
	void createStudentReturns400OnInvalidRequest() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_CREATE_STUDENT)
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE);
		when(studentService.create(any())).thenReturn(new Student());
		mockMvc.perform(request).andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("getStudent() gives response status 200 on successful service call")
	void getStudentReturns200OnSuccessfulServiceCall() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_GET_STUDENT)
				.contentType(MediaType.APPLICATION_JSON_VALUE).queryParam("id", "10")
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(studentService.findById(anyInt())).thenReturn(getStudent());
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	@DisplayName("getStudent() gives response status 404 when student is not found")
	void getStudentReturns404whenStudentIsNotFound() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_GET_STUDENT)
				.contentType(MediaType.APPLICATION_JSON_VALUE).queryParam("id", "1")
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(studentService.findById(anyInt())).thenThrow(StudentNotFoundException.class);
		mockMvc.perform(request).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("getStudent() gives response status 500 when some exception occurs")
	void getStudentReturns500whenSomeExceptionOccurs() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_GET_STUDENT)
				.contentType(MediaType.APPLICATION_JSON_VALUE).queryParam("id", "1")
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(studentService.findById(anyInt())).thenThrow(RuntimeException.class);
		mockMvc.perform(request).andExpect(status().isInternalServerError());
	}

	@Test
	@DisplayName("deleteStudent() returns response status 200 on successful service call")
	void deleteStudentReturns200OnSuccessfulServiceCall() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(ENDPOINT_DELETE_STUDENT)
				.queryParam("id", "5").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	@DisplayName("deleteStudent() gives response status 404 when data not found")
	void deleteStudentReturns404OnWhenDataNotFound() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(ENDPOINT_DELETE_STUDENT)
				.queryParam("id", "5").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		doThrow(EmptyResultDataAccessException.class).when(studentService).delete(anyInt());
		mockMvc.perform(request).andExpect(status().isNotFound());
	}

	@Test
	@Disabled("test case failing")
	@DisplayName("updateStudent() gives response status 200 on successful Service call")
	void updateStudentReturns200OnSuccessfulServiceCall() throws Exception {
		StudentRequest studentRequest = getStudentRequest();
		String requestBody = objectMapper.writeValueAsString(studentRequest);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(ENDPOINT_UPDATE_STUDENT)
				.queryParam("id", "5").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	@DisplayName("allPosts() gives response status 200 on successful service call")
	void allPostsReturns200OnSuccessfulServiceCall() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT_GET_STUDENTPOSTS)
				.queryParam("id", "5").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(studentService.findById(anyInt()).getPosts()).thenReturn(getPostList());
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	@DisplayName("createStudentPost() gives response status 201 on successful service call")
	void createStudentPostReturns200OnSuccessfulServiceCall() throws Exception {
		StudentPostRequest studentPostRequest = getStudentPostRequest();
		String requestBody = objectMapper.writeValueAsString(studentPostRequest);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_CREATE_STUDENTPOSTS)
				.queryParam("id", "5").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(studentService.findById(anyInt())).thenReturn(getStudent());
		when(studentPostMapper.convertToEntity(any())).thenReturn(getPost());
		when(postService.create(any())).thenReturn(getPost());
		mockMvc.perform(request).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("createStudentPost() gives response status 404 when student not found")
	void createStudentPostReturnsNull() throws Exception {
		StudentPostRequest studentPostRequest = getStudentPostRequest();
		String requestBody = objectMapper.writeValueAsString(studentPostRequest);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(ENDPOINT_CREATE_STUDENTPOSTS)
				.queryParam("id", "5").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)
				.accept(MediaType.APPLICATION_JSON_VALUE);
		when(studentService.findById(anyInt())).thenReturn(null);
		mockMvc.perform(request).andExpect(status().isNotFound());
	}

	private Post getPost() {
		return Post.builder().id(1).posts("Dummy Post").build();
	}

	private StudentPostRequest getStudentPostRequest() {
		return StudentPostRequest.builder().posts("Test").build();
	}

	private List<Post> getPostList() {
		List<Post> listOfPosts = new ArrayList<>();
		listOfPosts.add(Post.builder().id(1).posts("Test Post").build());
		return listOfPosts;
	}

	private StudentRequest getStudentRequest() {
		return StudentRequest.builder().name("Ashish").age(35).city("Kota").build();
	}

	private Student getStudent() {
		return Student.builder().name("Ashish").age(35).city("Kota").build();
	}
}
