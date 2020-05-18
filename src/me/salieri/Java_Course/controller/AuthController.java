package me.salieri.Java_Course.controller;

import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.model.JwtRequest;
import me.salieri.Java_Course.model.JwtResponse;
import me.salieri.Java_Course.security.JwtTokenUtil;
import me.salieri.Java_Course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private UserService userService;

  @GetMapping("/auth")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
    authenticate(request.getUsername(), request.getPassword());
    final User userDetails = userService.loadUserByUsername(request.getUsername());
    final String token = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }

  @GetMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody JwtRequest request) throws Exception {
    User user = new User(request.getUsername(), request.getPassword());
    if (userService.saveUser(user)) {
      return ResponseEntity.ok("Registration successful.");
    } else {
      return ResponseEntity.ok("Given username already given.");
    }
  }
}
