package me.salieri.Java_Course.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.salieri.Java_Course.model.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class APIUtils {
  private static final String version = "v0.1";

  public static ResponseEntity<?> apiResponse(Object response) {
    return new ResponseEntity<>(new APIResponse(
        version,
        HttpStatus.OK.value(),
        response
    ),HttpStatus.OK);
  }

  public static ResponseEntity<?> apiResponse(Object response, HttpStatus status) {
    return new ResponseEntity<>(new APIResponse(
        version,
        status.value(),
        response
    ), status);
  }

  public static ResponseEntity<?> emptyApiResponse(HttpStatus status) {
    return new ResponseEntity<>(new APIResponse(
        version,
        status.value(),
        new ObjectMapper().createObjectNode()
    ), status);
  }
}
