package me.salieri.Java_Course.service;

import me.salieri.Java_Course.entity.Group;
import me.salieri.Java_Course.entity.Mark;
import me.salieri.Java_Course.entity.Person;
import me.salieri.Java_Course.entity.Subject;
import me.salieri.Java_Course.repository.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MarkService {
  @Autowired
  MarkRepository markRepository;

  public Mark loadMarkById(Long id) throws NotFoundException {
    Optional<Mark> mark = markRepository.findById(Objects.requireNonNull(id));
    if (mark.isEmpty()) {
      throw new NotFoundException("Mark not found");
    }
    return mark.get();
  }

  public Mark saveMark(Mark mark, Person student, Subject subject, Person teacher) {
    if (student.getType() != Person.STUDENT) {
      throw new InvalidParameterException();
    } else if (teacher.getType() != Person.TEACHER) {
      throw new InvalidParameterException();
    }
    mark.setStudent(student);
    mark.setSubject(subject);
    mark.setTeacher(teacher);
    return markRepository.save(mark);
  }

  public List<Mark> allMarks() {
    return markRepository.findAll();
  }

  public List<Mark> allMarksByStudent(Person student) {
    return markRepository.findAllByStudent(student);
  }

  public List<Mark> allMarksBySubject(Subject subject) {
    return markRepository.findAllBySubject(subject);
  }

  public List<Mark> allMarksByTeacher(Person teacher) {
    return markRepository.findAllByTeacher(teacher);
  }

  public List<Mark> allMarksByGroup(Group group) {
    return markRepository.findAllByStudent_Group(group);
  }
}
