package me.salieri.Java_Course.controller.api.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.model.UserRequest;
import me.salieri.Java_Course.service.UserService;
import me.salieri.Java_Course.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
  @Autowired
  UserService userService;

  @GetMapping(path = "/admin/allUsers")
  public ResponseEntity<?> allUsers() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    List<User> users = userService.allUsers();
    ArrayNode usersNode = mapper.valueToTree(users);
    json.putArray("users").addAll(usersNode);

    return APIUtils.apiResponse(json);
  }

  @GetMapping(path = "/admin/deleteUser")
  @Transactional
  public ResponseEntity<?> deleteUser(@RequestBody UserRequest request) {
    if (request.getId() != null) {
      return deleteUser(request.getId());
    }

    if (request.getUsername() != null) {
      return deleteUser(request.getUsername());
    }

    return APIUtils.apiResponse(new ObjectMapper().createObjectNode(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @GetMapping(path = "/admin/deleteUser", params = { "username" })
  @Transactional
  public ResponseEntity<?> deleteUser(@RequestParam String username) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.OK;

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (((User)auth.getPrincipal()).getUsername().equals(username)) {
      status = HttpStatus.BAD_REQUEST;
      return APIUtils.apiResponse(json, status);
    }

    if (!userService.deleteUserByUsername(username)) {
      status = HttpStatus.BAD_REQUEST;
    }

    return APIUtils.apiResponse(json, status);
  }

  @GetMapping(path = "/admin/deleteUser", params = { "id" })
  @Transactional
  public ResponseEntity<?> deleteUser(@RequestParam Long id) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.OK;

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userService.loadUserByUsername(((User)auth.getPrincipal()).getUsername());
    if (user.getId().equals(id)) {
      status = HttpStatus.BAD_REQUEST;
      return APIUtils.apiResponse(json, status);
    }

    if (!userService.deleteUserById(id)) {
      status = HttpStatus.BAD_REQUEST;
    }

    return APIUtils.apiResponse(json, status);
  }
}