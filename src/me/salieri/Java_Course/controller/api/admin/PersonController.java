package me.salieri.Java_Course.controller.api.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.Authority;
import me.salieri.Java_Course.entity.Person;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.model.EntityRequest;
import me.salieri.Java_Course.model.PersonRequest;
import me.salieri.Java_Course.service.AuthorityService;
import me.salieri.Java_Course.service.GroupService;
import me.salieri.Java_Course.service.PersonService;
import me.salieri.Java_Course.service.UserService;
import me.salieri.Java_Course.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
public class PersonController {
  @Autowired
  AuthorityService authorityService;
  @Autowired
  GroupService groupService;
  @Autowired
  PersonService personService;
  @Autowired
  UserService userService;

  @GetMapping("/admin/people.delete")
  @Transactional
  public ResponseEntity<?> deactivatePerson(@RequestBody EntityRequest request) {
    HttpStatus status = HttpStatus.OK;

    try {
      if (!personService.deactivatePerson(personService.loadPersonByRequest(request))) {
        status = HttpStatus.BAD_REQUEST;
      }
    } catch (NotFoundException e) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    return APIUtils.emptyApiResponse(status);
  }
}
