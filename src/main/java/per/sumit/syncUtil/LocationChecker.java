package per.sumit.syncUtil;

import java.io.File;

/**
 * Location Checker
 * 
 * @author samurai
 *
 */
public final class LocationChecker {

	public boolean locationExists(String location) {
		return new File(location).exists();
	}

	public boolean locationExists(File fileLocation) {
		return fileLocation.exists();
	}
}
