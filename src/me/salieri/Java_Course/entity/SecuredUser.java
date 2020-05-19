package me.salieri.Java_Course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SecuredUser extends User {
  @Override
  @JsonIgnore
  public String getPassword() {
    return super.getPassword();
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return super.isAccountNonExpired();
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return super.isAccountNonLocked();
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return super.isCredentialsNonExpired();
  }
}
