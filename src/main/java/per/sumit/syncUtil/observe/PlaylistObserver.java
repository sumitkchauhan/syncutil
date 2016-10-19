package per.sumit.syncUtil.observe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import per.sumit.syncUtil.Configuration;
import per.sumit.syncUtil.exceptions.SyncException;

/**
 * Changes playlist to be compatible with Ford Sync
 * 
 * @author samurai
 *
 */
public final class PlaylistObserver implements CopyObserver {
	private static final Logger LGGR = LogManager.getLogger(PlaylistObserver.class);
	private static final String PLLST_M3U_REGEX = ".*\\.m3u";
	private static final List<PatternReplaceDetails> PTTRN_REPLACE_DETAILS;
	static {
		List<PatternReplaceDetails> replaceDetails = new ArrayList<>();
		replaceDetails.add(new PatternReplaceDetails(Pattern.compile("#.*\\n"), ""));
		replaceDetails.add(new PatternReplaceDetails(Pattern.compile("/"), "\\\\"));
		PTTRN_REPLACE_DETAILS = Collections.unmodifiableList(replaceDetails);
	}

	private final FileIO fileIO;

	public PlaylistObserver(FileIO fileIO) {
		this.fileIO = fileIO;
	}

	@Override
	public void notifyPreCopy(Configuration copyConfig) {
		final String sourceDirectory = copyConfig.getSourceDirectory();
		LGGR.info("Modifying playlists for directory:" + sourceDirectory);
		try {
			updateFiles(Paths.get(sourceDirectory));
		} catch (IOException e) {
			LGGR.error("Exception while updating playlist.",e);
			throw new SyncException("Exception while updating playlist.",e);
		}
	}

	private void updateFiles(Path path) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		String inputString;
		for (File file : fileIO.getChildFiles(path, PLLST_M3U_REGEX)) {
			try {
				inputString = IOUtils.toString(fileIO.getInputstreamToFile(file), "UTF-8");
				for (PatternReplaceDetails pttrnRplcDtls : PTTRN_REPLACE_DETAILS) {
					inputString = pttrnRplcDtls.getPattern().matcher(inputString)
							.replaceAll(pttrnRplcDtls.getReplacement());
				}
				os = fileIO.getOutputstreamToFile(file);
				IOUtils.write(inputString, os, "UTF-8");
			} finally {
				fileIO.closeQuietly(is);
				fileIO.closeQuietly(os);
			}

		}
	}

	/**
	 * Doesn't do anything
	 */
	@Override
	public void notifyPostCopy(Configuration copyConfig) {
		// NO-OP
	}

	private static final class PatternReplaceDetails {
		private final Pattern pattern;
		private final String replacement;

		public PatternReplaceDetails(Pattern pattern, String replacement) {
			this.pattern = pattern;
			this.replacement = replacement;
		}

		public Pattern getPattern() {
			return pattern;
		}

		public String getReplacement() {
			return replacement;
		}

	}

}
