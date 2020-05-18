package me.salieri.Java_Course.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "table_users")
public class User implements UserDetails {
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Size(min = 5)
  @NotNull
  private String username;
  @Size(min = 5)
  @NotNull
  private String password;
  @Column(columnDefinition = "boolean default 1")
  private boolean active;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "table_user_authority",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "authority_id")
  )
  private Set<Authority> authorities;

  public User() {

  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }
  
  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !active;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return !active;
  }

  @Override
  public String toString() {
    return this.username + " " + this.authorities.toString();
  }
}
