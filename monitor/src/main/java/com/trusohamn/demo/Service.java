package com.trusohamn.demo;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Service {

  @Id
  @GeneratedValue
  private Long id;

  private String name;
  private String url;
  private ArrayList<Boolean> status = new ArrayList<Boolean>();

  Service() {}

  Service(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getUrl() {
    return this.url;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public ArrayList<Boolean> getStatus() {
    return status;
  }

  public void setStatus(ArrayList<Boolean> status) {
    this.status = status;
  }
}
