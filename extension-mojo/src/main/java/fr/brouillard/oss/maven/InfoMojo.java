package fr.brouillard.oss.maven;

import javax.inject.Inject;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "info", defaultPhase = LifecyclePhase.VALIDATE, requiresProject = false)
public class InfoMojo extends AbstractMojo {
  private InfoHolder infoHolder;

  @Inject
  public InfoMojo(InfoHolder infoHolder) {
    this.infoHolder = infoHolder;
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    String information = infoHolder.getInformation();
    if (information != null) {
      getLog().info("Information: " + information);
    } else {
      getLog().error("No information provided, Singleton injection might have failed");
    }
  }
}
