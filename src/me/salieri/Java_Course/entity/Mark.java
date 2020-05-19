package me.salieri.Java_Course.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "marks")
public class Mark {
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @ManyToOne
  @JoinColumn(referencedColumnName="id")
  private Person student;
  @ManyToOne
  @JoinColumn(referencedColumnName="id")
  private Subject subject;
  @ManyToOne
  @JoinColumn(referencedColumnName="id")
  private Person teacher;
  @NotNull
  private int value;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Person getStudent() {
    return student;
  }

  public void setStudent(Person student) {
    this.student = student;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public Person getTeacher() {
    return teacher;
  }

  public void setTeacher(Person teacher) {
    this.teacher = teacher;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
