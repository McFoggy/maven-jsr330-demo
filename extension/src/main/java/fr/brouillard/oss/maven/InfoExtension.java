package fr.brouillard.oss.maven;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.logging.Logger;

@Named
@Singleton
public class InfoExtension extends AbstractMavenLifecycleParticipant {
  private final InfoHolder infoHolder;
  private Logger logger;

  @Inject
  public InfoExtension(Logger logger, InfoHolder infoHolder) {
    this.logger = logger;
    this.infoHolder = infoHolder;
  }

  @Override
  public void afterSessionStart(MavenSession session) throws MavenExecutionException {
    super.afterSessionStart(session);

    String information = "Build started at " + System.currentTimeMillis();
    logger.info("extension generated information: " + information);
    infoHolder.setInformation(information);
  }
}
