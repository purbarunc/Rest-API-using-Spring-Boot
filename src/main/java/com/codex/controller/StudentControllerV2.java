package com.codex.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.model.Post;
import com.codex.model.Student;
import com.codex.service.StudentService;

@RestController
@RequestMapping("/v2")
public class StudentControllerV2 {
	@Autowired
	private StudentService studentService;

	@GetMapping("/students")
	public ResponseEntity<CollectionModel<Student>> getAllStudents() {
		List<Student> students = studentService.findAll();
		students.forEach(student -> {
			student.add(linkTo(methodOn(StudentControllerV2.class).getStudent(student.getId())).withSelfRel());
			if (!student.getPosts().isEmpty()) {
				student.add(
						linkTo(methodOn(StudentControllerV2.class).allPosts(student.getId())).withRel("studentposts"));
			}
		});
		Link allStudentsLink = linkTo(methodOn(StudentControllerV2.class).getAllStudents()).withSelfRel();
		return new ResponseEntity<>(CollectionModel.of(students, allStudentsLink), HttpStatus.OK);
	}

	@GetMapping("/student/posts")
	public ResponseEntity<CollectionModel<Post>> allPosts(@RequestParam("id") int studentId) {
		List<Post> allPosts = studentService.findById(studentId).getPosts();
		Link allStudentsLink = linkTo(methodOn(StudentControllerV2.class).getAllStudents()).withRel("students");
		Link studentLink = linkTo(methodOn(StudentControllerV2.class).getStudent(studentId)).withRel("student");
		return new ResponseEntity<>(CollectionModel.of(allPosts, studentLink, allStudentsLink), HttpStatus.OK);
	}

	@GetMapping("/student")
	public ResponseEntity<Student> getStudent(@RequestParam("id") int studentId) {
		return new ResponseEntity<>(
				studentService.findById(studentId)
						.add(linkTo(methodOn(StudentControllerV2.class).getAllStudents()).withRel("students")),
				HttpStatus.OK);
	}
}
