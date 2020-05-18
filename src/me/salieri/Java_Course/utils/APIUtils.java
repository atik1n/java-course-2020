package me.salieri.Java_Course.utils;

import me.salieri.Java_Course.model.APIResponse;

import javax.servlet.http.HttpServletResponse;

public class APIUtils {
  private static final String version = "v0.1";

  public static APIResponse apiResponse(Object response) {
    return new APIResponse(version, HttpServletResponse.SC_OK, response);
  }

  public static APIResponse apiResponse(Object response, int status) {
    return new APIResponse(version, status, response);
  }
}
