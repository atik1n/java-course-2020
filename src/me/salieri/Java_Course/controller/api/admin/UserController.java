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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
public class UserController {
  @Autowired
  UserService userService;

  @GetMapping("/admin/users.all")
  @Transactional
  public ResponseEntity<?> allUsers() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    List<User> users = userService.allUsers();
    ArrayNode node = mapper.valueToTree(users);
    json.putArray("users").addAll(node);

    return APIUtils.apiResponse(json);
  }

  @GetMapping("/admin/users.delete")
  @Transactional
  public ResponseEntity<?> deactivateUser(@RequestBody UserRequest request) {
    HttpStatus status;

    try {
      status = deactivateUser(userService.loadUserByRequest(request));
    } catch (UsernameNotFoundException e) {
      status = HttpStatus.BAD_REQUEST;
    } catch (InvalidParameterException e) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    return APIUtils.emptyApiResponse(status);
  }

  public HttpStatus deactivateUser(User user) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authUser;
    try {
      authUser = userService.loadUserByUsername(((User) auth.getPrincipal()).getUsername());
    } catch (UsernameNotFoundException e) {
      return HttpStatus.UNAUTHORIZED;
    }

    HttpStatus status = HttpStatus.OK;
    if (user.getId().equals(authUser.getId())) {
      status = HttpStatus.BAD_REQUEST;
    } else if (!userService.deactivateUserById(user.getId())) {
      status = HttpStatus.BAD_REQUEST;
    }

    return status;
  }
}
