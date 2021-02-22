package fr.brouillard.oss.maven;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.building.Source;
import org.apache.maven.model.Build;
import org.apache.maven.model.InputSource;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.building.DefaultModelProcessor;
import org.apache.maven.model.building.ModelProcessor;
import org.codehaus.plexus.logging.Logger;

@Named
@Singleton
public class InfoModelProcessor extends DefaultModelProcessor {
  final static String MOJO_GROUP_ID = "fr.brouillard.oss";
  final static String MOJO_ARTIFACT_ID = "maven-demo-mojo";
  final static String MOJO_GOAL = "info";
  private Logger logger = null;
  
  @Inject
  public InfoModelProcessor(Logger logger) {
    this.logger = logger;
  }
  
  @Override
  public Model read(File input, Map<String, ?> options) throws IOException {
    return provisionModel(super.read(input, options), input, options);
  }

  @Override
  public Model read(Reader input, Map<String, ?> options) throws IOException {
    return provisionModel(super.read(input, options), null, options);
  }

  @Override
  public Model read(InputStream input, Map<String, ?> options) throws IOException {
    return provisionModel(super.read(input, options), null, options);
  }
  
  private Model provisionModel(Model model, File sourceFile, Map<String, ?> options) {
    File location = sourceFile;
    
    if (location == null) {
      Supplier<String> locationProvider = () -> null;
      
      if (options.get(ModelProcessor.SOURCE) instanceof Source) {
        Source source = (Source) options.get(ModelProcessor.SOURCE);
        locationProvider = source::getLocation;
      } else if (options.get(ModelProcessor.INPUT_SOURCE) instanceof InputSource) {
        InputSource source = (InputSource) options.get( ModelProcessor.INPUT_SOURCE);
        locationProvider = source::getLocation;
      }
      
      String locationStr = locationProvider.get();
      if (locationStr == null) {
        return model;
      }
      location = new File(locationStr);
    }

    if (!location.isFile()) {
      return model;
    }
    
    return addInfoMojoExecution(model);
  }

  private Model addInfoMojoExecution(Model model) {
    ensureBuildWithPluginsExistInModel(model);
    Plugin plugin = addOrRetrieveInfoMojoPlugin(model);
    ensureExecution(plugin);
    
    return model;
  }

  private void ensureExecution(Plugin plugin) {
    if (Objects.isNull(plugin.getExecutions())) {
      plugin.setExecutions(new ArrayList<>());
    }

    String pluginRunPhase = "validate";
    Optional<PluginExecution> pluginExecutionOptional = plugin.getExecutions().stream().findFirst();

    PluginExecution pluginExecution = pluginExecutionOptional.orElseGet(
        () -> {
          PluginExecution pluginExecution2 = new PluginExecution();
          pluginExecution2.setPhase(pluginRunPhase);

          plugin.getExecutions().add(pluginExecution2);
          return pluginExecution2;
        });

    if (Objects.isNull(pluginExecution.getGoals())) {
      pluginExecution.setGoals(new ArrayList<>());
    }

    if (!pluginExecution
        .getGoals()
        .contains(MOJO_GOAL)) {
      pluginExecution.getGoals().add(MOJO_GOAL);
    }
  }

  private Plugin addOrRetrieveInfoMojoPlugin(Model model) {
    return model.getBuild().getPlugins().stream()
        .filter(
            x -> MOJO_GROUP_ID.equalsIgnoreCase(x.getGroupId())
                && MOJO_ARTIFACT_ID.equalsIgnoreCase(x.getArtifactId()))
        .findFirst()
        .orElseGet(
            () -> {
              Plugin plugin = new Plugin();
              plugin.setGroupId(MOJO_GROUP_ID);
              plugin.setArtifactId(MOJO_ARTIFACT_ID);
              plugin.setVersion("0");

              model.getBuild().getPlugins().add(0, plugin);
              return plugin;
            });
  }

  private void ensureBuildWithPluginsExistInModel(Model model) {
    if (Objects.isNull(model.getBuild())) {
      model.setBuild(new Build());
    }

    if (Objects.isNull(model.getBuild().getPlugins())) {
      model.getBuild().setPlugins(new ArrayList<>());
    }
  }

}
