package me.salieri.Java_Course.model;

public class PersonRequest extends EntityRequest {
  private String firstName;
  private String lastName;
  private String patherName;

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
}
