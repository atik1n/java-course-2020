package me.salieri.Java_Course.model;

public class StudentRequest extends PersonRequest {
  private String group;

  public StudentRequest() {

  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }
}
