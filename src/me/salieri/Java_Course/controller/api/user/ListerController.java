package me.salieri.Java_Course.controller.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.Group;
import me.salieri.Java_Course.entity.Mark;
import me.salieri.Java_Course.entity.Person;
import me.salieri.Java_Course.entity.Subject;
import me.salieri.Java_Course.model.GroupRequest;
import me.salieri.Java_Course.model.PersonRequest;
import me.salieri.Java_Course.model.SubjectRequest;
import me.salieri.Java_Course.service.*;
import me.salieri.Java_Course.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Set;

@RestController
public class ListerController {
  @Autowired
  GroupService groupService;
  @Autowired
  MarkService markService;
  @Autowired
  PersonService personService;
  @Autowired
  SubjectService subjectService;
  @Autowired
  UserService userService;

  @GetMapping("/user/groups.all")
  @Transactional
  public ResponseEntity<?> allGroups() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    List<Group> groups = groupService.allGroups();
    ArrayNode node = mapper.valueToTree(groups);
    json.putArray("groups").addAll(node);

    return APIUtils.apiResponse(json);
  }

  @GetMapping("/user/groups.getStudents")
  @Transactional
  public ResponseEntity<?> getStudentsGroup(@RequestBody GroupRequest request) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.OK;

    try {
      Group group = groupService.loadGroupByRequest(request);
      Set<Person> students = group.getPeople();
      ArrayNode node = mapper.valueToTree(students);
      json.putArray("students").addAll(node);
    } catch (NotFoundException e) {
      status = HttpStatus.BAD_REQUEST;
    } catch (InvalidParameterException e) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    return APIUtils.apiResponse(json, status);
  }

  @GetMapping("/user/marks.all")
  @Transactional
  public ResponseEntity<?> allMarks() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    List<Mark> marks = markService.allMarks();
    ArrayNode node = mapper.valueToTree(marks);
    json.putArray("marks").addAll(node);

    return APIUtils.apiResponse(json);
  }

  @GetMapping("/user/marks.byStudent")
  @Transactional
  public ResponseEntity<?> marksByStudent(@RequestBody PersonRequest request) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    List<Mark> marks;
    try {
      Person student = personService.loadPersonById(request.getId());
      if (student.getType() == Person.STUDENT) {
        marks = markService.allMarksByStudent(student);
        ArrayNode node = mapper.valueToTree(marks);
        json.putArray("marks").addAll(node);
        status = HttpStatus.OK;
      }
    } catch (NotFoundException ignored) {
    } catch (NullPointerException e) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    return APIUtils.apiResponse(json, status);
  }

  @GetMapping("/user/marks.bySubject")
  @Transactional
  public ResponseEntity<?> marksBySubject(@RequestBody SubjectRequest request) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    List<Mark> marks;
    try {
      Subject subject = subjectService.loadSubjectByRequest(request);
      marks = markService.allMarksBySubject(subject);
      ArrayNode node = mapper.valueToTree(marks);
      json.putArray("marks").addAll(node);
      status = HttpStatus.OK;
    } catch (NotFoundException ignored) {
    } catch (NullPointerException e) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    return APIUtils.apiResponse(json, status);
  }

  @GetMapping("/user/marks.byTeacher")
  @Transactional
  public ResponseEntity<?> marksByTeacher(@RequestBody PersonRequest request) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    List<Mark> marks;
    try {
      Person teacher = personService.loadPersonById(request.getId());
      if (teacher.getType() == Person.TEACHER) {
        marks = markService.allMarksByStudent(teacher);
        ArrayNode node = mapper.valueToTree(marks);
        json.putArray("marks").addAll(node);
        status = HttpStatus.OK;
      }
    } catch (NotFoundException ignored) {
    } catch (NullPointerException e) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    return APIUtils.apiResponse(json, status);
  }

  @GetMapping("/user/marks.byGroup")
  @Transactional
  public ResponseEntity<?> marksByGroup(@RequestBody GroupRequest request) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    List<Mark> marks;
    try {
      Group group = groupService.loadGroupByRequest(request);
      marks = markService.allMarksByGroup(group);
      ArrayNode node = mapper.valueToTree(marks);
      json.putArray("marks").addAll(node);
      status = HttpStatus.OK;
    } catch (NotFoundException ignored) {
    } catch (NullPointerException e) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    return APIUtils.apiResponse(json, status);
  }

  @GetMapping("/user/people.all")
  @Transactional
  public ResponseEntity<?> allPersons() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    List<Person> students = personService.allPersons();
    ArrayNode node = mapper.valueToTree(students);
    json.putArray("persons").addAll(node);

    return APIUtils.apiResponse(json);
  }

  @GetMapping("/user/students.all")
  @Transactional
  public ResponseEntity<?> allStudents() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    List<Person> students = personService.allStudents();
    ArrayNode node = mapper.valueToTree(students);
    json.putArray("students").addAll(node);

    return APIUtils.apiResponse(json);
  }

  @GetMapping("/user/subjects.all")
  @Transactional
  public ResponseEntity<?> allSubjects() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    List<Subject> subjects = subjectService.allSubjects();
    ArrayNode node = mapper.valueToTree(subjects);
    json.putArray("subjects").addAll(node);

    return APIUtils.apiResponse(json);
  }

  @GetMapping("/user/teachers.all")
  @Transactional
  public ResponseEntity<?> allTeachers() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    List<Person> teachers = personService.allTeachers();
    ArrayNode node = mapper.valueToTree(teachers);
    json.putArray("teachers").addAll(node);

    return APIUtils.apiResponse(json);
  }
}
