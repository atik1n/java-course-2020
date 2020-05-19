package me.salieri.Java_Course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Size(min = 1)
  @NotNull
  private String name;

  @JsonIgnore
  @OneToMany(mappedBy="group")
  private Set<Person> people;

  public Group() {

  }

  public Group(String name) {
    this.name = Objects.requireNonNull(name);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = Objects.requireNonNull(name);
  }

  public Set<Person> getPeople() {
    return people;
  }

  public void setPeople(Set<Person> people) {
    this.people = people;
  }
}
