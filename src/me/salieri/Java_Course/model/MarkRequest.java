package me.salieri.Java_Course.model;

public class MarkRequest extends EntityRequest {
  private Long student;
  private Long subject;
  private Long teacher;
  private Integer value;

  public Long getStudent() {
    return student;
  }

  public void setStudent(Long student) {
    this.student = student;
  }

  public Long getSubject() {
    return subject;
  }

  public void setSubject(Long subject) {
    this.subject = subject;
  }

  public Long getTeacher() {
    return teacher;
  }

  public void setTeacher(Long teacher) {
    this.teacher = teacher;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }
}
