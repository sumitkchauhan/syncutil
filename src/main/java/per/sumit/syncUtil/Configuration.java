package per.sumit.syncUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.core.config.ConfigurationException;

import per.sumit.syncUtil.observe.CopyObserver;

/**
 * One molecule of copy information
 * 
 * @author samurai
 *
 */
public final class Configuration {

	private static final Pattern SPURIOUS_SEP_PTTRN = Pattern.compile("[\\\\/](?=[^\\s])");
	private static final String SEPARATOR = File.separator;

	private List<CopyObserver> observerList = new ArrayList<>();
	private final String sourceDirectory;
	private final String destinationDirectory;
	private final String destinationDirectoryType;
	/**
	 * Delay for this configuration to be executed
	 */
	private final long scheduleFixedDelay;

	public Configuration(String sourceDirectory, String destinationDirectory, String destinationDirectoryType,
			long scheduleFixedDelay) {
		this(sourceDirectory, destinationDirectory, destinationDirectoryType, null, scheduleFixedDelay);
	}

	public Configuration(String sourceDirectory, String destinationDirectory, String destinationDirectoryType,
			List<CopyObserver> observerList, long scheduleFixedDelay) {
		this.sourceDirectory = sanitizePath(sourceDirectory);
		this.destinationDirectory = sanitizePath(destinationDirectory);
		this.scheduleFixedDelay = scheduleFixedDelay;
		this.destinationDirectoryType = destinationDirectoryType;
		if (CollectionUtils.isNotEmpty(observerList)) {
			observerList.forEach(this.observerList::add);
		}
	}

	private String sanitizePath(String inputPath) {
		try {
			return SPURIOUS_SEP_PTTRN.matcher(new File(inputPath).getCanonicalPath()).replaceAll(SEPARATOR);
		} catch (IOException e) {
			throw new ConfigurationException("Exception during standardizing file path.", e);
		}
	}

	@Override
	public int hashCode() {
		return sourceDirectory.hashCode() + 31 * destinationDirectory.hashCode();
	}

	public String getSourceDirectory() {
		return sourceDirectory;
	}

	public String getDestinationDirectory() {
		return destinationDirectory;
	}

	public String getDestinationDirectoryType() {
		return destinationDirectoryType;
	}

	public void notifyPreActivation() {
		for (CopyObserver obs : observerList) {
			obs.notifyPreCopy(this);
		}
	}

	public void notifyPostFinishing() {
		for (CopyObserver obs : observerList) {
			obs.notifyPostCopy(this);
		}
	}

	public long getScheduleFixedDelay() {
		return scheduleFixedDelay;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if ((obj != null) && obj.getClass().equals(Configuration.class)) {
			Configuration that = (Configuration) obj;
			isEqual = Paths.get(that.getSourceDirectory()).equals(Paths.get(this.getSourceDirectory()))
					&& Paths.get(that.getDestinationDirectory()).equals(Paths.get(this.getDestinationDirectory()));
		}
		return isEqual;
	}

	@Override
	public String toString() {
		return "[SD=" + getSourceDirectory() + ", DD=" + getDestinationDirectory() + "]";
	}

}
