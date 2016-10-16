package per.sumit.syncUtil.observe;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

/**
 * Class to provide File IO
 * 
 * @author samurai
 *
 */
public class FileIO {
	private static final Map<String, Pattern> patternCache = new ConcurrentHashMap<>();

	public InputStream getInputStreamforPath(Path path) throws IOException {
		return new BufferedInputStream(Files.newInputStream(path));
	}

	public OutputStream getOutputstreamToLocation(Path path) throws IOException {
		return new BufferedOutputStream(Files.newOutputStream(path));
	}

	public InputStream getInputstreamToFile(File file) throws IOException {
		return new BufferedInputStream(new FileInputStream(file));
	}

	public OutputStream getOutputstreamToFile(File file) throws IOException {
		return new BufferedOutputStream(new FileOutputStream(file));
	}

	public List<File> getChildFiles(Path path, String regex) throws IOException {
		List<File> fileList = new ArrayList<>();
		getMatchingFiles(path.toFile(), regex, fileList);
		return fileList;

	}

	private void getMatchingFiles(File parentFile, String regex, List<File> fileList) {
		Pattern pattern = patternCache.get(regex);
		if (pattern == null) {
			pattern = Pattern.compile(regex);
			patternCache.put(regex, pattern);
		}
		for (File file : parentFile.listFiles()) {
			if (file.isDirectory()) {
				getMatchingFiles(file, regex, fileList);
			} else if (pattern.matcher(file.getName()).matches()) {
				fileList.add(file);
			}
		}
	}

	public void closeQuietly(Closeable stream) {
		IOUtils.closeQuietly(stream);
	}
}