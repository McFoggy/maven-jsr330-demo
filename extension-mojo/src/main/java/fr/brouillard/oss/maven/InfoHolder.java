package fr.brouillard.oss.maven;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class InfoHolder {
  private String information;

  public String getInformation() {
    return information;
  }

  public void setInformation(String information) {
    this.information = information;
  }
}
