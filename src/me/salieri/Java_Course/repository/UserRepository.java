package me.salieri.Java_Course.repository;

import me.salieri.Java_Course.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
