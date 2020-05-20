package me.salieri.Java_Course.controller.api.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.Person;
import me.salieri.Java_Course.model.PersonRequest;
import me.salieri.Java_Course.model.StudentRequest;
import me.salieri.Java_Course.service.PersonService;
import me.salieri.Java_Course.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {
  @Autowired
  PersonService personService;

  @GetMapping("/admin/teachers.create")
  @Transactional
  public ResponseEntity<?> createStudent(@RequestBody PersonRequest request) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.CREATED;

    Person person;
    try {
      person = personService.createPerson(request, null);
      json.set("person", mapper.valueToTree(person));
      json.put("username", person.getUsername());
      json.put("password", person.getPassword());
    } catch (NotFoundException | StackOverflowError e) {
      status = HttpStatus.BAD_REQUEST;
    } catch (NullPointerException e) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    return APIUtils.apiResponse(json, status);
  }
}
