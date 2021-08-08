package com.codex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codex.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{
}
