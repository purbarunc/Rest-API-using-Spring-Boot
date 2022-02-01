package com.codex.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "student")
@DynamicUpdate
@ApiModel(description = "Details about Students")
public class Student extends RepresentationModel<Student> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@ApiModelProperty(notes = "Primary key of a Student")
	private int id;

	@Size(min = 3, message = "name should have atleast 3 characters")
	@Column(name = "NAME")
	@ApiModelProperty(notes = "Name of a Student")
	private String name;

	@Column(name = "AGE")
	@Min(value = 10, message = "Age should not be less than 10")
	@Max(value = 150, message = "Age should not be greater than 150")
	@ApiModelProperty(notes = "Age of Student")
	private int age;

	@Size(min = 4, message = "city name should not be less than 4 characters")
	@Column(name = "CITY")
	private String city;

	@OneToMany(mappedBy = "student", orphanRemoval = true, cascade = CascadeType.REMOVE)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<Post> posts;
}
