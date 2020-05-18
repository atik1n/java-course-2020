package me.salieri.Java_Course.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.model.APIResponse;
import me.salieri.Java_Course.utils.APIUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {
  @GetMapping(path = "/greet")
  public ResponseEntity<?> greeter() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    if (!(auth instanceof AnonymousAuthenticationToken)) {
      User user = (User)auth.getPrincipal();
      json.put("username", String.valueOf(user));
      status = HttpStatus.OK;
    }

    APIResponse response = APIUtils.apiResponse(json, status.value());
    return new ResponseEntity<>(response, status);
  }
}
