package me.salieri.Java_Course.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {
  private final String token;

  public JwtResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return this.token;
  }
}
