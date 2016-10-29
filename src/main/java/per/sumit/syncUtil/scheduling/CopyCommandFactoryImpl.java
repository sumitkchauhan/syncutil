package per.sumit.syncUtil.scheduling;

import per.sumit.syncUtil.Configuration;
import per.sumit.syncUtil.FileCopier;
import per.sumit.syncUtil.LocationChecker;

/**
 * Creates instance of copy command
 * 
 * @author samurai
 *
 */
public class CopyCommandFactoryImpl implements CopyCommandFactory {
	private final LocationChecker locationChecker;
	private final FileCopier copier;

	public CopyCommandFactoryImpl(LocationChecker locationChecker, FileCopier copier) {
		this.locationChecker = locationChecker;
		this.copier = copier;
	}

	@Override
	public CopyCommand createCopyCommandForConfig(Configuration copyConfiguration) {
		return new CopyCommand(locationChecker, copier, copyConfiguration);
	}

}
