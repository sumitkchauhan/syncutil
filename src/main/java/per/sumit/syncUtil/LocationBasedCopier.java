package per.sumit.syncUtil;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Facade for copying
 * 
 * @author samurai
 *
 */
public final class LocationBasedCopier {

	private final Logger LOGGER = LogManager.getLogger(LocationBasedCopier.class);
	private final LocationChecker locationChecker;
	private final Configurations copyConfiguration;
	private final FileCopier copier;

	public LocationBasedCopier(LocationChecker locationChecker, FileCopier copier, Configurations copyConfiguration) {
		this.locationChecker = locationChecker;
		this.copier = copier;
		this.copyConfiguration = copyConfiguration;
	}

	/**
	 * Copies according to the master configuration object
	 * 
	 * @param copyConfiguration
	 */
	public void copy() {
		String sourceDirectory;
		String destinationDirectory = null;
		String destinationDirectoryType = null;
		for (Configuration configuration : copyConfiguration.getNormalizedConfiguration()) {
			sourceDirectory = configuration.getSourceDirectory();
			destinationDirectory = configuration.getDestinationDirectory();
			destinationDirectoryType = configuration.getDestinationDirectoryType();
			if ((null != destinationDirectoryType) && destinationDirectoryType.equals("mtp")
					&& locationChecker.locationExists(sourceDirectory)) {
				// TODO:check destination directory existence for MTP too
				configuration.notifyPreActivation();
				copier.copy(sourceDirectory, destinationDirectory, configuration.getDestinationDirectoryType());
				configuration.notifyPostFinishing();

			} else {
				if (locationChecker.locationExists(sourceDirectory)
						&& locationChecker.locationExists(destinationDirectory)) {
					configuration.notifyPreActivation();
					copier.copy(sourceDirectory, destinationDirectory, configuration.getDestinationDirectoryType());
					configuration.notifyPostFinishing();

				} else {
					LOGGER.warn(
							"One of these directories don't exist:" + sourceDirectory + " OR " + destinationDirectory);
				}
			}
		}
		try {
			// TODO: Might need to move to another class as this class doesn't
			// manage
			// copier lifeyecle
			copier.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
