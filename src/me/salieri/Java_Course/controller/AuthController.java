package me.salieri.Java_Course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.Authority;
import me.salieri.Java_Course.entity.SecuredUser;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.model.UserRequest;
import me.salieri.Java_Course.utils.AuthUtils;
import me.salieri.Java_Course.service.AuthorityService;
import me.salieri.Java_Course.service.UserService;
import me.salieri.Java_Course.utils.APIUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
public class AuthController {
  @Autowired
  private AuthUtils authUtils;
  @Autowired
  private UserService userService;
  @Autowired
  private AuthorityService authorityService;

  @GetMapping("/auth")
  @Transactional
  public ResponseEntity<?> createAuthenticationToken(@RequestBody UserRequest request) throws Exception {
    return createAuthenticationToken(request.getUsername(), request.getPassword());
  }

  @GetMapping(path = "/auth", params = { "username", "password" })
  @Transactional
  public ResponseEntity<?> createAuthenticationToken(@RequestParam String username, @RequestParam String password)
      throws Exception {
    authUtils.authenticate(username, password);
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    final User user = userService.loadUserByUsername(username);
    final String token = authUtils.generateToken(user);

    json.put("token", token);
    return APIUtils.apiResponse(json, HttpStatus.CREATED);
  }

  @GetMapping("/register")
  @Transactional
  public ResponseEntity<?> registerUser(@RequestBody UserRequest request) {
    return registerUser(request.getUsername(), request.getPassword());
  }

  @GetMapping(path = "/register", params = { "username", "password" })
  @Transactional
  public ResponseEntity<?> registerUser(@RequestParam String username, @RequestParam String password) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    User user;
    try {
      user = new User(username, password);
    } catch (NullPointerException e) {
      return APIUtils.emptyApiResponse(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    Set<Authority> authorities = Collections.singleton(authorityService.loadAuthorityByName("USER"));
    user.setAuthorities(authorities);
    user = userService.saveUser(user);
    if (user != null) {
      SecuredUser secUser = userService.loadSecuredUserById(user.getId());
      json.set("user", mapper.valueToTree(secUser));
      status = HttpStatus.CREATED;
    }

    return APIUtils.apiResponse(json, status);
  }
}
