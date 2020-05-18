package me.salieri.Java_Course.controller;

import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.repository.UserRepository;
import me.salieri.Java_Course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

@Controller
public class APIController {
  @Autowired
  private UserService userService;

  @GetMapping(path = "/")
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String sayHello() {
    List<User> users = userService.allUsers();
    return "{ \"code\": " + HttpServletResponse.SC_OK + ", \"test\": \"" + users.toString() + "\" }";
  }
}
