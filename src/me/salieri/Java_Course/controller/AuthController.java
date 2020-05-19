package me.salieri.Java_Course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.Authority;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.model.JwtRequest;
import me.salieri.Java_Course.security.JwtTokenUtil;
import me.salieri.Java_Course.service.AuthorityService;
import me.salieri.Java_Course.service.UserService;
import me.salieri.Java_Course.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private UserService userService;
  @Autowired
  private AuthorityService authorityService;

  @GetMapping("/auth")
  @Transactional
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
    return createAuthenticationToken(request.getUsername(), request.getPassword());
  }

  @GetMapping(path = "/auth", params = { "username", "password" })
  @Transactional
  public ResponseEntity<?> createAuthenticationToken(@RequestParam String username, @RequestParam String password)
      throws Exception {
    authenticate(username, password);
    final User userDetails = userService.loadUserByUsername(username);
    final String token = jwtTokenUtil.generateToken(userDetails);

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    json.put("token", token);

    return APIUtils.apiResponse(json, HttpStatus.CREATED);
  }

  @Transactional
  public void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }

  @GetMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody JwtRequest request) {
    return registerUser(request.getUsername(), request.getPassword());
  }

  @GetMapping(path = "/register", params = { "username", "password" })
  public ResponseEntity<?> registerUser(@RequestParam String username, @RequestParam String password) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    User user = new User(username, password);
    Set<Authority> authorities = Collections.singleton(authorityService.loadAuthorityByName("USER"));
    if (userService.saveUser(user, authorities)) {
      user = userService.loadUserByUsername(username);
      json.set("user", mapper.valueToTree(user));
      status = HttpStatus.CREATED;
    }

    return APIUtils.apiResponse(json, status);
  }
}
