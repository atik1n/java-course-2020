package me.salieri.Java_Course.repository;

import me.salieri.Java_Course.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
