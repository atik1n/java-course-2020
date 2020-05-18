package me.salieri.Java_Course.model;

import org.springframework.validation.ObjectError;

import java.io.Serializable;

public class APIResponse implements Serializable {
  private final String version;
  private final int status;
  private final Object response;

  public APIResponse(String version, int status, Object response) {
    this.version = version;
    this.status = status;
    this.response = response;
  }

  public String getVersion() {
    return version;
  }

  public int getStatus() {
    return status;
  }

  public Object getResponse() {
    return response;
  }
}
