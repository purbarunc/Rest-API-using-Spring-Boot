package com.codex.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.dto.StudentPostRequest;
import com.codex.dto.StudentRequest;
import com.codex.exception.StudentNotFoundException;
import com.codex.httpresponse.ErrorResponse;
import com.codex.httpresponse.HttpResponse;
import com.codex.httpresponse.SuccessResponse;
import com.codex.mapper.StudentMapper;
import com.codex.mapper.StudentPostMapper;
import com.codex.model.Post;
import com.codex.model.Student;
import com.codex.service.PostService;
import com.codex.service.StudentService;

@RestController
@RequestMapping("/v1")
public class StudentControllerV1 {
	@Autowired
	private StudentService studentService;

	@Autowired
	private PostService postService;

	@Autowired
	private StudentMapper studentMapper;

	@Autowired
	private StudentPostMapper studentPostMapper;

	@GetMapping("/students")
	public ResponseEntity<List<Student>> allStudents() {
		return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/student")
	public ResponseEntity<Student> getStudent(@RequestParam("id") int studentId) {
		return new ResponseEntity<>(studentService.findById(studentId), HttpStatus.OK);
	}

	@PostMapping("/student")
	public ResponseEntity<Student> createStudent(@NotNull @Valid @RequestBody StudentRequest studentRequest) {
		Student student = mapStudentDTOToEntity(studentRequest);
		return new ResponseEntity<>(studentService.create(student), HttpStatus.CREATED);
	}

	@PutMapping("/student")
	public ResponseEntity<HttpResponse> updateStudent(@NotNull @Valid @RequestBody StudentRequest studentRequest,
			@RequestParam("id") int studentId) {
		Student student = mapStudentDTOToEntity(studentRequest);
		student.setId(studentId);
		studentService.update(student);
		return new ResponseEntity<>(new SuccessResponse(String.format("Student id=%d has been updated", studentId)),
				HttpStatus.OK);
	}

	@DeleteMapping("/student")
	public ResponseEntity<HttpResponse> deleteStudent(@RequestParam("id") int studentId) {
		try {
			studentService.delete(studentId);
			return new ResponseEntity<>(
					new SuccessResponse(String.format("Student id=%d succesfully deleted", studentId)), HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<>(new ErrorResponse(String.format("Student id=%d not found", studentId)),
					HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/student/posts")
	public ResponseEntity<List<Post>> allPosts(@RequestParam("id") int studentId) {
		return new ResponseEntity<>(studentService.findById(studentId).getPosts(), HttpStatus.OK);
	}

	@PostMapping("/student/posts")
	public ResponseEntity<Post> createStudentPost(@RequestParam("id") int studentId,
			@NotNull @RequestBody StudentPostRequest studentPostRequest) {
		Post post = studentPostMapper.convertToEntity(studentPostRequest);
		Student student = studentService.findById(studentId);
		if (student == null) {
			throw new StudentNotFoundException("id: " + studentId);
		}
		post.setStudent(student);
		return new ResponseEntity<>(postService.create(post), HttpStatus.CREATED);
	}

	private Student mapStudentDTOToEntity(StudentRequest studentRequest) {
		return studentMapper.convertToEntity(studentRequest);
	}
}
