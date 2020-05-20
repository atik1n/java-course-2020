package me.salieri.Java_Course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "people")
public class Person {
  public static char STUDENT = 'S';
  public static char TEACHER = 'T';
  public static char DISABLE = 'D';

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
  @JsonIgnore
  @OneToOne(mappedBy = "person")
  private User user;

  @JsonIgnore
  @Transient
  private String username;
  @JsonIgnore
  @Transient
  private String password;

  public Person() {

  }

  public Person(String firstName, String lastName, String patherName, char type) {
    this.firstName = Objects.requireNonNull(firstName);
    this.lastName = Objects.requireNonNull(lastName);
    this.patherName = Objects.requireNonNull(patherName);
    this.type = type;
  }

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
    this.firstName = Objects.requireNonNull(firstName);
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = Objects.requireNonNull(lastName);
  }

  public String getPatherName() {
    return patherName;
  }

  public void setPatherName(String patherName) {
    this.patherName = Objects.requireNonNull(patherName);
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

  public String generateUsername() {
    this.username = (
        lastName + "." +
        firstName.substring(0, 1) +
        patherName.substring(0, 1)
    ).toLowerCase();
    return this.username;
  }

  public String generateUsername(int i) {
    this.username = (
        lastName + i + "." +
        firstName.substring(0, 1) +
        patherName.substring(0, 1)
    ).toLowerCase();
    return this.username;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
