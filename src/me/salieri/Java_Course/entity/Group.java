package me.salieri.Java_Course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
  private Set<People> people;

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
    this.name = name;
  }

  public Set<People> getPeople() {
    return people;
  }

  public void setPeople(Set<People> people) {
    this.people = people;
  }
}
