package com.codex.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Userposts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POSTID")
	@Getter
	@Setter
	private int id;

	@Column(name = "POSTS")
	@Size(min = 10, max = 200, message = "About Me must be between 10 and 200 characters")
	@Getter
	@Setter
	private String userPosts;

	@ManyToOne
	@JsonIgnore
	@Getter
	@Setter
	private User users;

	@Override
	public String toString() {
		return "Post [id=" + id + ", post=" + userPosts + "]";
	}

}
