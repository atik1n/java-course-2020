package me.salieri.Java_Course.model;

public class StudentRequest {
  private String firstName;
  private String lastName;
  private String patherName;
  private String group;

  public StudentRequest() {

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

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getUsername() {
    return (
        lastName + "." +
        firstName.substring(0, 1) +
        patherName.substring(0, 1)
    ).toLowerCase();
  }

  public String getUsername(int i) {
    return (
        lastName + i + "." +
            firstName.substring(0, 1) +
            patherName.substring(0, 1)
    ).toLowerCase();
  }
}
