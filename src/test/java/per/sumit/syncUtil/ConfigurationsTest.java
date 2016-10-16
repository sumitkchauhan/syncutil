package per.sumit.syncUtil;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class ConfigurationsTest {

	/**
	 * Test just creation of Configurations and normalization of path
	 */
	@Test
	public void testBasicCreation() {
		Configurations configs = Configurations.getConfigurationFromClsPth("/configurations.xml");
		Collection<Configuration> configList = configs.getNormalizedConfiguration();
		Collection<Configuration> configTestList = new ArrayList<>();
		configTestList.add(new Configuration("sourceDirectory", "destinationDirectory",null));
		configTestList.add(new Configuration("sourceDirectory", "destinationDirectory1","mtp"));
		assertEquals("Expected 2 but was "+configList.size(),configList.size(), 2);
		assert(configTestList.containsAll(configTestList));
	}

}
