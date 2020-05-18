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
  private People student;
  @ManyToOne
  @JoinColumn(referencedColumnName="id")
  private Subject subject;
  @ManyToOne
  @JoinColumn(referencedColumnName="id")
  private People teacher;
  @NotNull
  private int value;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public People getStudent() {
    return student;
  }

  public void setStudent(People student) {
    this.student = student;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public People getTeacher() {
    return teacher;
  }

  public void setTeacher(People teacher) {
    this.teacher = teacher;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
