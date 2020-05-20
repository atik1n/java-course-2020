package me.salieri.Java_Course.repository;

import me.salieri.Java_Course.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
  Optional<Subject> findByName(String name);
}
