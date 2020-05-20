package me.salieri.Java_Course.repository;

import me.salieri.Java_Course.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
  List<Person> findAllByType(char Type);
}
