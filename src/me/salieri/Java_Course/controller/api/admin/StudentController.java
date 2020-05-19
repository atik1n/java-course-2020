package me.salieri.Java_Course.controller.api.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.Authority;
import me.salieri.Java_Course.entity.Person;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.model.StudentRequest;
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
public class StudentController {
  @Autowired
  AuthorityService authorityService;
  @Autowired
  GroupService groupService;
  @Autowired
  PersonService personService;
  @Autowired
  UserService userService;

  @GetMapping("/admin/createStudent")
  @Transactional
  public ResponseEntity<?> createStudent(@RequestBody StudentRequest request) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    User user;
    Person person;
    String username;
    String password;
    try {
      person = new Person(request.getFirstName(), request.getLastName(), request.getPatherName(), Person.STUDENT);
      person.setGroup(groupService.loadGroupByName(request.getGroup()));

      username = request.getUsername();
      int i = 1;
      while (i <= 255) {
        try {
          userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
          break;
        }
        username = request.getUsername(i++);
      }
      if (i == 256) {
        return APIUtils.apiResponse(json, HttpStatus.BAD_REQUEST);
      }
      user = new User(username);
      password = Integer.toHexString(user.hashCode());
      user.setPassword(password);
    } catch (NullPointerException e) {
      return APIUtils.apiResponse(json, HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (NotFoundException e) {
      return APIUtils.apiResponse(json, HttpStatus.BAD_REQUEST);
    }

    Set<Authority> authorities = new HashSet<>(Arrays.asList(
        authorityService.loadAuthorityByName("PERSON"),
        authorityService.loadAuthorityByName("STUDENT")
    ));

    person = personService.savePerson(person);
    user.setPerson(person);
    user.setAuthorities(authorities);
    user = userService.saveUser(user);
    if (user != null && person != null) {
      json.set("person", mapper.valueToTree(person));
      json.put("username", username);
      json.put("password", password);
      status = HttpStatus.CREATED;
    }

    return APIUtils.apiResponse(json, status);
  }
}
