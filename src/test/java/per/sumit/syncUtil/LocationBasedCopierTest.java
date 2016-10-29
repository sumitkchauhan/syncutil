package per.sumit.syncUtil;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import per.sumit.syncUtil.scheduling.CopyCommand;
import per.sumit.syncUtil.scheduling.CopyCommandFactory;
import per.sumit.syncUtil.scheduling.CopyCommandFactoryImpl;
import per.sumit.syncUtil.scheduling.Scheduler;
import per.sumit.syncUtil.scheduling.SchedulerImpl;

public class LocationBasedCopierTest {

	@Test
	public void testCopyLocationNotExist() throws SAXException {
		LocationChecker locChecker = new LocationChecker();
		FileCopier copier = createMock(FileCopier.class);

		Configurations configurations = Configurations
				.getConfigurationFromClsPth("/configurations_nonexistingpaths.xml");
		CopyCommandFactory commandFact = new CopyCommandFactoryImpl(locChecker, copier);
		Scheduler scheduler = new SchedulerImpl(1);
		replay(copier);
		new LocationBasedCopier(commandFact, configurations, scheduler)  .copy();
		verify(copier);
		scheduler.shutDown();
	}

	@Test
	public void testCopyLocationExist() throws SAXException {
		LocationChecker locChecker = new LocationChecker();
		FileCopier copier = createMock(FileCopier.class);
		
		Configurations configurations = Configurations.getConfigurationFromClsPth("/configurations_existingpaths.xml");
		Configuration config = configurations.getNormalizedConfiguration().iterator().next();
		copier.copy(config.getSourceDirectory(), config.getDestinationDirectory(), null);
		expectLastCall().times(1);

		CopyCommandFactory commandFact = new CopyCommandFactoryImpl(locChecker, copier);
		Scheduler scheduler = new SchedulerImpl(1);
		replay(copier);
		new LocationBasedCopier(commandFact, configurations, scheduler)  .copy();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(copier);
		scheduler.shutDown();
	}

}
