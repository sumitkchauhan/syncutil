package per.sumit.syncUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import per.sumit.syncUtil.exceptions.ConfigurationException;
import per.sumit.syncUtil.observe.ObserverFactory;

/**
 * Central Configurations object,
 * 
 * @author samurai
 *
 */
public class Configurations {
	private static final Logger LOGGER = LogManager.getLogger(Configurations.class);
	private final ObserverFactory obsFactory = new ObserverFactory();

	private Set<Configuration> normalizedConfiguration;

	private Configurations(InputStream configFileStrm) {
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(per.sumit.syncUtil.model.Configurations.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			per.sumit.syncUtil.model.Configurations loadedConfigs = (per.sumit.syncUtil.model.Configurations) unmarshaller
					.unmarshal(configFileStrm);
			normalizeAndStoreConfiguration(loadedConfigs);
		} catch (JAXBException e) {
			LOGGER.error("Exception while parsing Configuration XML file.",e);
			throw new ConfigurationException("Exception while parsing Configuration XML file.",e);
		}
	}

	public static Configurations getConfigurationFromClsPth(String configFileClasspath) {
		try (InputStream is = Configurations.class.getResourceAsStream(configFileClasspath)) {
			return new Configurations(is);
		} catch (IOException e) {
			LOGGER.error("Exception while readin config file.",e);
			throw new ConfigurationException("Exception while readin config file.",e);
		}
	}

	public static Configurations getConfigurationFromFileSystem(String fsPath) {
		try (InputStream is = new FileInputStream(fsPath)) {
			return new Configurations(is);
		} catch (IOException e) {
			LOGGER.error("Exception while readin config file.",e);
			throw new ConfigurationException("Exception while readin config file.",e);
		}
	}

	/**
	 * Normalizes the configurations and stores them for later use
	 * 
	 * @param loadedConfigurations
	 *            Configurations loaded from file system.
	 */
	private void normalizeAndStoreConfiguration(per.sumit.syncUtil.model.Configurations loadedConfigurations) {
		List<per.sumit.syncUtil.model.Configurations.Configuration> loadedConfig = loadedConfigurations
				.getConfiguration();
		Set<Configuration> configToRemove = new HashSet<>();
		final Set<Configuration> tempConfiguration = new HashSet<>();
		for (per.sumit.syncUtil.model.Configurations.Configuration config : loadedConfig) {
			tempConfiguration.add(new Configuration(config.getSourceDirectory(), config.getDestinationDirectory(),
					config.getDestinationType(),
					config.getCopyObserver().stream().map(obsFactory::getObserverforKey).collect(Collectors.toList())));
		}
		for (Configuration currentConfig : tempConfiguration) {
			for (Configuration tempConfig : tempConfiguration) {
				if (isParentOf(currentConfig.getSourceDirectory(), tempConfig.getSourceDirectory())
						&& isParentOf(currentConfig.getDestinationDirectory(), tempConfig.getDestinationDirectory())) {
					configToRemove.add(tempConfig);
					LOGGER.debug("Removed redundant configuration with source path:" + tempConfig.getSourceDirectory()
							+ " and destination:" + tempConfig.getDestinationDirectory());
				} else if (isParentOf(tempConfig.getSourceDirectory(), currentConfig.getSourceDirectory())
						&& isParentOf(tempConfig.getDestinationDirectory(), currentConfig.getDestinationDirectory())) {
					configToRemove.add(currentConfig);
					LOGGER.debug(
							"Removed redundant configuration with source path:" + currentConfig.getSourceDirectory()
									+ " and destination:" + currentConfig.getDestinationDirectory());
				}
			}
		}
		tempConfiguration.removeAll(configToRemove);
		normalizedConfiguration = tempConfiguration;
	}

	/**
	 * Checks if path is parent of another path
	 * 
	 * @param probableParentPth
	 *            Parent path to check
	 * @param probableChildPth
	 *            Child path to check
	 * @return true if it's actually a parent
	 */
	private static final boolean isParentOf(String probableParentPth, String probableChildPth) {
		if (probableChildPth.equals(probableParentPth)) {
			return false;
		}
		Path probableParent = Paths.get(probableParentPth);
		Path probableChild = Paths.get(probableChildPth);
		return probableChild.startsWith(probableParent);
	}

	/**
	 * Normalized collection of paths, with redundant paths removed
	 * 
	 * @return normalized configuration
	 */
	public Set<Configuration> getNormalizedConfiguration() {
		return Collections.unmodifiableSet(normalizedConfiguration);
	}

}
