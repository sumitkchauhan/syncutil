package per.sumit.syncUtil;

import static org.junit.Assert.*;

import org.junit.Test;

public class LocationCheckerTest {

	@Test
	public void test() {
		LocationChecker locationChecker = new LocationChecker();
		assertFalse("Location shouldn't exist.",locationChecker.locationExists("dasjhdjsajdhsa"));
	}

}
