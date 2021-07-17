package com.codex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codex.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{
}
