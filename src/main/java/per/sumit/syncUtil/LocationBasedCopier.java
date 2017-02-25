package per.sumit.syncUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import per.sumit.syncUtil.scheduling.CopyCommandFactory;
import per.sumit.syncUtil.scheduling.Scheduler;

/**
 * Facade for copying
 * 
 * @author samurai
 *
 */
public final class LocationBasedCopier {
	private final CopyCommandFactory commandFactory;
	private final Scheduler scheduler;
	private final Logger LOGGER = LogManager.getLogger(LocationBasedCopier.class);
	private final Configurations copyConfiguration;
	private static final Long FIXED_CHECK_INTERVAL = 120000L;//ms

	public LocationBasedCopier(CopyCommandFactory commandFact, Configurations copyConfiguration, Scheduler scheduler) {
		this.commandFactory = commandFact;
		this.copyConfiguration = copyConfiguration;
		this.scheduler = scheduler;
	}

	/**
	 * Copies according to the master configuration object
	 * 
	 * @param copyConfiguration
	 */
	public void copy() {
		LOGGER.info("Starting copying process.");
		for (Configuration configuration : copyConfiguration.getNormalizedConfiguration()) {
			scheduler.scheduleExecution(commandFactory.createCopyCommandForConfig(configuration),
					FIXED_CHECK_INTERVAL);
		}

	}

}
