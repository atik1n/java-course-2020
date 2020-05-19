package me.salieri.Java_Course.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.SecuredUser;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.service.UserService;
import me.salieri.Java_Course.utils.APIUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
public class PublicController {
  @Autowired
  UserService userService;

  @GetMapping(path = "/getUser")
  public ResponseEntity<?> getUser() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.UNAUTHORIZED;

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!(auth instanceof AnonymousAuthenticationToken)) { ;
      SecuredUser user = new SecuredUser();
      BeanUtils.copyProperties(userService.loadUserByPrincipal(auth.getPrincipal()), user);
      json.set("user", mapper.valueToTree(user));
      status = HttpStatus.OK;
    }

    return APIUtils.apiResponse(json, status);
  }

  @GetMapping(path = "/getUser", params = { "username" })
  public ResponseEntity<?> getUser(@RequestParam String username) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.OK;

    SecuredUser user = new SecuredUser();
    try {
      BeanUtils.copyProperties(userService.loadUserByUsername(username), user);
      json.set("user", mapper.valueToTree(user));
    } catch (UsernameNotFoundException e) {
      status = HttpStatus.NOT_FOUND;
    }

    return APIUtils.apiResponse(json, status);
  }

  @GetMapping(path = "/getUser", params = { "id" })
  public ResponseEntity<?> getUser(@RequestParam Long id) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    HttpStatus status = HttpStatus.OK;

    SecuredUser user = new SecuredUser();
    try {
      BeanUtils.copyProperties(userService.loadUserById(id), user);
      json.set("user", mapper.valueToTree(user));
    } catch (UsernameNotFoundException e) {
      status = HttpStatus.NOT_FOUND;
    }

    return APIUtils.apiResponse(json, status);
  }
}
