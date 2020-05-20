package me.salieri.Java_Course.controller.api.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.Subject;
import me.salieri.Java_Course.model.SubjectRequest;
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
public class SubjectController {
  @Autowired
  SubjectService subjectService;

  @GetMapping("/admin/subjects.create")
  @Transactional
  public ResponseEntity<?> createSubject(@RequestBody SubjectRequest request) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    Subject subject;
    try {
      subject = new Subject(request.getName());
    } catch (NullPointerException e) {
      return APIUtils.emptyApiResponse(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    subject = subjectService.saveSubject(subject);
    if (subject != null) {
      json.set("group", mapper.valueToTree(subject));
      status = HttpStatus.CREATED;
    }

    return APIUtils.apiResponse(json, status);
  }

  @GetMapping("/admin/subjects.delete")
  @Transactional
  public ResponseEntity<?> deleteSubject(@RequestBody SubjectRequest request) {
    HttpStatus status;
    try {
      status = deleteSubject(subjectService.loadSubjectByRequest(request));
    } catch (NotFoundException e) {
      status = HttpStatus.BAD_REQUEST;
    } catch (InvalidParameterException e) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    return APIUtils.emptyApiResponse(status);
  }

  private HttpStatus deleteSubject(Subject subject) {
    HttpStatus status = HttpStatus.OK;
    if (!subject.getMarks().isEmpty()) {
      status = HttpStatus.BAD_REQUEST;
    } else if (!subjectService.deleteSubjectById(subject.getId())) {
      status = HttpStatus.BAD_REQUEST;
    }

    return status;
  }
}
