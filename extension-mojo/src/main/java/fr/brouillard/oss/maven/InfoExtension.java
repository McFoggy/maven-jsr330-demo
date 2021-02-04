package fr.brouillard.oss.maven;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;

@Named
@Singleton
public class InfoExtension extends AbstractMavenLifecycleParticipant {
  private final InfoHolder infoHolder;
  
  public InfoExtension(InfoHolder infoHolder) {
    this.infoHolder = infoHolder;
  }
  
  @Override
  public void afterSessionStart(MavenSession session) throws MavenExecutionException {
    super.afterSessionStart(session);
    
    infoHolder.setInformation("Build started at " + System.currentTimeMillis());
  }
}
