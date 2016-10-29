package per.sumit.syncUtil;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import per.sumit.syncUtil.exceptions.SyncException;

/**
 * Synchronous wrapper over rsync
 * 
 * @author samurai
 *
 */
public class RSyncFileCopier implements FileCopier {
	private static final Logger LOGGER = LogManager.getLogger(RSyncFileCopier.class);
	private static final String MTP = "mtp";

	@Override
	public void copy(String source, String destination) {
		startCopyThread(source, destination, null);
	}

	@Override
	public void copy(String source, String destination, String destinationType) {
		startCopyThread(source, destination, destinationType);
	}

	private void startCopyThread(String source, String destination, String destinationType) {
		try {
			long startTime = System.currentTimeMillis();

			ProcessBuilder pBuilder = (!MTP.equals(destinationType))
					? new ProcessBuilder(new String[] { "/bin/bash", "-c",
							"rsync -aWh  --inplace " + source + "/ " + destination + " --delete" })
					: new ProcessBuilder(new String[] { "/bin/bash", "-c",
							"rsync -aWh --omit-dir-times --no-perms --inplace --size-only " + source + "/ "
									+ destination + " --delete" });
			Process p = pBuilder.start();
			int val = p.waitFor();
			LOGGER.info("Directories synced in " + ((System.currentTimeMillis() - startTime) / 1000.0f) + "sec:"
					+ source + " --> " + destination + " of type:" + destinationType);

			if (val != 0) {
				throw new SyncException("Exception during RSync; return code = " + val);
			}
		} catch (IOException | InterruptedException e) {
			throw new SyncException("IO exception occurred.", e);
		}
	}

	@Override
	public void close() throws IOException {

	}

}
