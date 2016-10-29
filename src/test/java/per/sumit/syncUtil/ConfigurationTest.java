package per.sumit.syncUtil;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void test() {
		Configuration config = new Configuration("/dummyPath\\asda/dada1s//", "/dummyPath\\asda/dadas", null,200);
		assertEquals(config.getSourceDirectory(), "/dummyPath"+File.separator+"asda"+File.separator+"dada1s");
	}

}
