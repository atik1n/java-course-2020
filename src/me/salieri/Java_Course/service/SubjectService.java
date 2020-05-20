package me.salieri.Java_Course.service;

import me.salieri.Java_Course.entity.Group;
import me.salieri.Java_Course.entity.Subject;
import me.salieri.Java_Course.model.GroupRequest;
import me.salieri.Java_Course.model.SubjectRequest;
import me.salieri.Java_Course.repository.GroupRepository;
import me.salieri.Java_Course.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubjectService {
  @Autowired
  SubjectRepository subjectRepository;

  public Subject loadSubjectByName(String name) throws NotFoundException {
    Optional<Subject> subject = subjectRepository.findByName(Objects.requireNonNull(name));
    if (subject.isEmpty()) {
      throw new NotFoundException("Subject not found");
    }
    return subject.get();
  }

  public Subject loadSubjectById(Long id) throws NotFoundException {
    Optional<Subject> subject = subjectRepository.findById(Objects.requireNonNull(id));
    if (subject.isEmpty()) {
      throw new NotFoundException("Subject not found");
    }
    return subject.get();
  }

  public Subject loadSubjectByRequest(SubjectRequest request) throws NotFoundException, InvalidParameterException {
    Subject subject;
    if (request.getId() != null) {
      subject = loadSubjectById(request.getId());
    } else if (request.getName() != null) {
      subject = loadSubjectByName(request.getName());
    } else {
      throw new InvalidParameterException();
    }

    return subject;
  }

  public Subject saveSubject(Subject subject) {
    Optional<Subject> loaded = subjectRepository.findByName(Objects.requireNonNull(subject.getName()));
    if (loaded.isPresent()) {
      return null;
    }
    return subjectRepository.save(subject);
  }

  public boolean deleteSubjectByName(String name) {
    return deleteSubject(subjectRepository.findByName(name).orElse(null));
  }

  public boolean deleteSubjectById(Long id) {
    return deleteSubject(subjectRepository.findById(id).orElse(null));
  }

  private boolean deleteSubject(Subject subject) {
    if (subject == null) {
      return false;
    }
    subjectRepository.deleteById(subject.getId());
    return true;
  }

  public List<Subject> allSubjects() {
    return subjectRepository.findAll();
  }
}
