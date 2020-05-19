package me.salieri.Java_Course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
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
  private boolean active;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "user_authority",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "authority_id")
  )
  private Set<Authority> authorities;
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(referencedColumnName="id")
  private Person person;

  public User() {

  }

  public User(String username) {
    this.username = username;
  }

  public User(String username, String password) {
    this.username = Objects.requireNonNull(username);
    this.password = Objects.requireNonNull(password);
  }

  @PrePersist
  private void PrePersist() {
    this.active = true;
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
    this.password = Objects.requireNonNull(password);
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = Objects.requireNonNull(id);
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = Objects.requireNonNull(username);
  }

  @Override
  public boolean isAccountNonExpired() {
    return active;
  }

  @Override
  public boolean isAccountNonLocked() {
    return active;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return active;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }

  @Override
  public String toString() {
    return this.username + " " + this.authorities.toString();
  }

  @JsonIgnore
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }
}
