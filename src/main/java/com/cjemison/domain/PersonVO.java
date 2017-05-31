package com.cjemison.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by cjemison on 5/31/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonVO {
  @JsonProperty("id")
  private String id;
  @JsonProperty(value = "first_name", required = true)
  private String firstName;
  @JsonProperty(value = "last_name", required = true)
  private String lastName;

  public PersonVO() {
  }

  public PersonVO(final String id,
                  final String firstName,
                  final String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof PersonVO)) return false;

    final PersonVO personVO = (PersonVO) o;

    return getId() != null ? getId().equals(personVO.getId()) : personVO.getId() == null;
  }

  @Override
  public int hashCode() {
    return getId() != null ? getId().hashCode() : 0;
  }

  @Override
  public String toString() {
    return "PersonVO{" +
          "id='" + id + '\'' +
          ", firstName='" + firstName + '\'' +
          ", lastName='" + lastName + '\'' +
          '}';
  }
}
