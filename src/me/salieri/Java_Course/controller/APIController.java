package me.salieri.Java_Course.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.model.APIResponse;
import me.salieri.Java_Course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

@RestController
public class APIController {
  private final String version = "v0.1";

  @Autowired
  private UserService userService;

  @GetMapping(path = "/")
  public ResponseEntity<?> mainIndex() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    List<User> users = userService.allUsers();
    ArrayNode usersNode = mapper.valueToTree(users);
    json.putArray("users").addAll(usersNode);

    APIResponse response = apiResponse(json);
    return ResponseEntity.ok(response);
  }

  @GetMapping(path = "/greet")
  public ResponseEntity<?> greeter() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!(auth instanceof AnonymousAuthenticationToken)) {
      User user = (User)auth.getPrincipal();
      json.put("username", user.getUsername());
    }

    APIResponse response = apiResponse(json);
    return ResponseEntity.ok(response);
  }

  private APIResponse apiResponse(Object response) {
    return new APIResponse(version, HttpServletResponse.SC_OK, response);
  }

  private APIResponse apiResponse(Object response, int status) {
    return new APIResponse(version, status, response);
  }
}
