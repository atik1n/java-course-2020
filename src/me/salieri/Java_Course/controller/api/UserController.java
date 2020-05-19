package me.salieri.Java_Course.controller.api;

import me.salieri.Java_Course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @Autowired
  UserService userService;
}
