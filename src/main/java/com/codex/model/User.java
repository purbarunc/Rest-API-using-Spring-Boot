package com.codex.model;

import java.util.List;

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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usertbl")
@DynamicUpdate
@ApiModel(description = "Details about the User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USERID")
	@ApiModelProperty(notes = "Primary key of the User")
	private int id;

	@Size(min = 3, message = "name should have atleast 3 characters")
	@Column(name = "NAME")
	@ApiModelProperty(notes = "Name of the User")
	private String name;

	@Column(name = "AGE")
	@Min(value = 10, message = "Age should not be less than 10")
	@Max(value = 150, message = "Age should not be greater than 150")
	@ApiModelProperty(notes = "Age of the User")
	private int age;

	@Size(min = 4, message = "city name should not be less than 4 characters")
	@Column(name = "CITY")
	private String city;

	@OneToMany(mappedBy = "users")
	List<Post> posts;
}
