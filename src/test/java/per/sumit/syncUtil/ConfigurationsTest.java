package per.sumit.syncUtil;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.xml.sax.SAXException;

public class ConfigurationsTest {

	/**
	 * Test just creation of Configurations and normalization of path
	 * @throws SAXException 
	 */
	@Test
	public void testBasicCreation()  {
		Configurations configs = Configurations.getConfigurationFromClsPth("/configurations.xml");
		Collection<Configuration> configList = configs.getNormalizedConfiguration();
		Collection<Configuration> configTestList = new ArrayList<>();
		configTestList.add(new Configuration("sourceDirectory", "destinationDirectory",null,1000));
		configTestList.add(new Configuration("sourceDirectory", "destinationDirectory1","mtp", 1000));
		assertEquals("Expected 2 but was "+configList.size(),configList.size(), 2);
		assert(configTestList.containsAll(configTestList));
	}

}
