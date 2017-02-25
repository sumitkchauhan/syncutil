package per.sumit.syncUtil.scheduling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import per.sumit.syncUtil.Configuration;
import per.sumit.syncUtil.FileCopier;
import per.sumit.syncUtil.LocationChecker;

public class CopyCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger(CopyCommand.class);
	private final LocationChecker locationChecker;
	private final FileCopier copier;
	private final Configuration copyConfiguration;

	public CopyCommand(LocationChecker locationChecker, FileCopier copier, Configuration copyConfiguration) {
		this.locationChecker = locationChecker;
		this.copier = copier;
		this.copyConfiguration = copyConfiguration;
	}

	public void execute() {
		long timeSinceLastExe = ExecutionRegistry.getTimeSinceLastExecution(copyConfiguration);
		if (timeSinceLastExe < copyConfiguration.getScheduleFixedDelay()) {
			LOGGER.info("Not sufficient delay for Config:" + copyConfiguration);
			return;
		}
		LOGGER.info("Copying");
		String sourceDirectory = copyConfiguration.getSourceDirectory();
		String destinationDirectory = copyConfiguration.getDestinationDirectory();
		String destinationDirectoryType = copyConfiguration.getDestinationDirectoryType();
		if ((null != destinationDirectoryType) && "mtp".equals(destinationDirectoryType)
				&& locationChecker.locationExists(sourceDirectory)) {
			// TODO:check destination directory existence for MTP too
			copyConfiguration.notifyPreActivation();
			copier.copy(sourceDirectory, destinationDirectory, copyConfiguration.getDestinationDirectoryType());
			copyConfiguration.notifyPostFinishing();
			ExecutionRegistry.registerSuccessfulConfigExecution(copyConfiguration);

		} else {
			if (locationChecker.locationExists(sourceDirectory)
					&& locationChecker.locationExists(destinationDirectory)) {
				copyConfiguration.notifyPreActivation();
				copier.copy(sourceDirectory, destinationDirectory, copyConfiguration.getDestinationDirectoryType());
				copyConfiguration.notifyPostFinishing();
				ExecutionRegistry.registerSuccessfulConfigExecution(copyConfiguration);

			} else {
				LOGGER.warn("One of these directories don't exist:" + sourceDirectory + " OR " + destinationDirectory);
			}
		}
		
	}

}
