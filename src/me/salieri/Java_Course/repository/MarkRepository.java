package me.salieri.Java_Course.repository;

import me.salieri.Java_Course.entity.Group;
import me.salieri.Java_Course.entity.Mark;
import me.salieri.Java_Course.entity.Person;
import me.salieri.Java_Course.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
  List<Mark> findAllByStudent(Person student);
  List<Mark> findAllBySubject(Subject subject);
  List<Mark> findAllByTeacher(Person teacher);
  List<Mark> findAllByStudent_Group(Group group);
}
