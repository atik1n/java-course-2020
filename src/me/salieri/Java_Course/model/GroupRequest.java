package me.salieri.Java_Course.model;

public class GroupRequest extends EntityRequest {
  private String name;

  public GroupRequest() {

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
