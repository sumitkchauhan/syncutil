package per.sumit.syncUtil;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import per.sumit.syncUtil.observe.CopyObserver;
import per.sumit.syncUtil.observe.FileIO;
import per.sumit.syncUtil.observe.PlaylistObserver;

public class PlaylistObserverTest {

	@Test
	public void testPreCopy() throws UnsupportedEncodingException, IOException {
		final String inputString = "#thisremove\nthiskeep\nchangethis/chng\n#rem\nasd";
		final String expectedString = "thiskeep\nchangethis\\chng\nasd";

		final String sourceDirectory = "dummySource";
		final String destDirectory = "dummyDest";

		FileIO fileIO = createMock(FileIO.class);
		// Mock input stream
		expect(fileIO.getInputstreamToFile(anyObject(File.class)))
				.andReturn(new ByteArrayInputStream(inputString.getBytes("UTF-8"))).anyTimes();
		// Mock output stream
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		expect(fileIO.getOutputstreamToFile(anyObject(File.class))).andReturn(baos).anyTimes();
		List<File> files = new ArrayList<>();
		files.add(new File("test.m3u"));
		expect(fileIO.getChildFiles(anyObject(Path.class), anyObject(String.class))).andReturn(files);
		fileIO.closeQuietly(anyObject());
		expectLastCall().times(2);

		PlaylistObserver playlistObserver = new PlaylistObserver(fileIO);
		List<CopyObserver> obsList = new ArrayList<>();
		Configuration copyConfiguration = new Configuration(sourceDirectory, destDirectory, null, obsList,200);

		replay(fileIO);
		playlistObserver.notifyPreCopy(copyConfiguration);

		assertEquals(expectedString, baos.toString("UTF-8"));
	}

}
