package me.salieri.Java_Course.controller.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.model.PasswordRequest;
import me.salieri.Java_Course.service.UserService;
import me.salieri.Java_Course.utils.APIUtils;
import me.salieri.Java_Course.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
  @Autowired
  AuthUtils authUtils;
  @Autowired
  UserService userService;

  @GetMapping("/user/password.change")
  @Transactional
  public ResponseEntity<?> passwordChange(@RequestBody PasswordRequest request) throws Exception {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userService.loadUserByPrincipal(auth.getPrincipal());
    try {
      authUtils.authenticate(user.getUsername(), request.getOldPassword());
    } catch (Exception e) {
      return APIUtils.emptyApiResponse(HttpStatus.FORBIDDEN);
    }

    userService.changeUserPassword(user, request.getNewPassword());
    return APIUtils.emptyApiResponse(HttpStatus.OK);
  }
}
