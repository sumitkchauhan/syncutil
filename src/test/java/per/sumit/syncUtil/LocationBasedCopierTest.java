package per.sumit.syncUtil;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.IOException;

import org.junit.Test;

public class LocationBasedCopierTest {

	@Test
	public void testCopyLocationNotExist() {
		LocationChecker locChecker = new LocationChecker();
		FileCopier copier = createMock(FileCopier.class);
		try {
			copier.close();
			expectLastCall().times(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Configurations configurations = Configurations
				.getConfigurationFromClsPth("/configurations_nonexistingpaths.xml");
		replay(copier);
		new LocationBasedCopier(locChecker, copier, configurations).copy();
		verify(copier);
	}

	@Test
	public void testCopyLocationExist() {
		LocationChecker locChecker = new LocationChecker();
		FileCopier copier = createMock(FileCopier.class);
		try {
			copier.close();
			expectLastCall().times(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Configurations configurations = Configurations.getConfigurationFromClsPth("/configurations_existingpaths.xml");
		Configuration config = configurations.getNormalizedConfiguration().iterator().next();
		copier.copy(config.getSourceDirectory(), config.getDestinationDirectory(), null);
		expectLastCall().times(1);

		replay(copier);
		new LocationBasedCopier(locChecker, copier, configurations).copy();
		verify(copier);
	}

}
