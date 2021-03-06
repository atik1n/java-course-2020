package me.salieri.Java_Course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Size(min = 5)
  @NotNull
  private String authority;

  @JsonIgnore
  @ManyToMany(mappedBy = "authorities")
  private Set<User> users;

  public Authority() {

  }

  public Authority(String authority) {
    this.authority = authority;
  }

  public Authority(String authority, Long id) {
    this.id = id;
    this.authority = authority;
  }

  @Override
  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  @Override
  public String toString() {
    return this.authority;
  }
}
