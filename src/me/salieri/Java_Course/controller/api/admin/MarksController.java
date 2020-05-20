package me.salieri.Java_Course.controller.api.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.Group;
import me.salieri.Java_Course.entity.Mark;
import me.salieri.Java_Course.entity.Person;
import me.salieri.Java_Course.entity.Subject;
import me.salieri.Java_Course.model.GroupRequest;
import me.salieri.Java_Course.model.MarkRequest;
import me.salieri.Java_Course.repository.MarkRepository;
import me.salieri.Java_Course.service.MarkService;
import me.salieri.Java_Course.service.PersonService;
import me.salieri.Java_Course.service.SubjectService;
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

@RestController
public class MarksController {
  @Autowired
  MarkService markService;
  @Autowired
  PersonService personService;
  @Autowired
  SubjectService subjectService;

  @GetMapping("/admin/marks.create")
  @Transactional
  public ResponseEntity<?> createMark(@RequestBody MarkRequest request) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    Mark mark;
    try {
      mark = new Mark(request.getValue());
      Person student = personService.loadPersonById(request.getStudent());
      Subject subject = subjectService.loadSubjectById(request.getSubject());
      Person teacher = personService.loadPersonById(request.getTeacher());
      mark = markService.saveMark(mark, student, subject, teacher);
    } catch (NotFoundException | InvalidParameterException e) {
      return APIUtils.emptyApiResponse(HttpStatus.BAD_REQUEST);
    } catch (NullPointerException e) {
      return APIUtils.emptyApiResponse( HttpStatus.UNPROCESSABLE_ENTITY);
    }

    if (mark != null) {
      json.set("mark", mapper.valueToTree(mark));
      status = HttpStatus.CREATED;
    }

    return APIUtils.apiResponse(json, status);
  }

  @GetMapping("/admin/marks.change")
  @Transactional
  public ResponseEntity<?> changeMark(@RequestBody MarkRequest request) {
    HttpStatus status = HttpStatus.OK;
    Mark mark;
    try {
      mark = markService.loadMarkById(request.getId());
      mark.setValue(request.getValue());
    } catch (NullPointerException e) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
    } catch (NotFoundException e) {
      status = HttpStatus.BAD_REQUEST;
    }
    return APIUtils.emptyApiResponse(status);
  }
}
