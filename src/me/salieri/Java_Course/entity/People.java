package me.salieri.Java_Course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "people")
public class People {
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Size(min = 1)
  @NotNull
  private String firstName;
  @Size(min = 1)
  @NotNull
  private String lastName;
  @Size(min = 1)
  @NotNull
  private String patherName;
  @ManyToOne
  @JoinColumn(referencedColumnName="id")
  private Group group;
  @NotNull
  private char type;

  @JsonIgnore
  @OneToMany(mappedBy = "student")
  private Set<Mark> marksStudent;
  @JsonIgnore
  @OneToMany(mappedBy = "teacher")
  private Set<Mark> marksTeacher;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPatherName() {
    return patherName;
  }

  public void setPatherName(String patherName) {
    this.patherName = patherName;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public char getType() {
    return type;
  }

  public void setType(char type) {
    this.type = type;
  }

  public Set<Mark> getMarksStudent() {
    return marksStudent;
  }

  public void setMarksStudent(Set<Mark> marksStudent) {
    this.marksStudent = marksStudent;
  }

  public Set<Mark> getMarksTeacher() {
    return marksTeacher;
  }

  public void setMarksTeacher(Set<Mark> marksTeacher) {
    this.marksTeacher = marksTeacher;
  }
}
