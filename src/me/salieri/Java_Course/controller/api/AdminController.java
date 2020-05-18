package me.salieri.Java_Course.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.model.APIResponse;
import me.salieri.Java_Course.service.UserService;
import me.salieri.Java_Course.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
  @Autowired
  UserService userService;

  @GetMapping(path = "/admin/allUsers")
  public ResponseEntity<?> allUsers() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    List<User> users = userService.allUsers();
    ArrayNode usersNode = mapper.valueToTree(users);
    json.putArray("users").addAll(usersNode);

    APIResponse response = APIUtils.apiResponse(json);
    return ResponseEntity.ok(response);
  }
}
