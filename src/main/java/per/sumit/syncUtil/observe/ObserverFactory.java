package per.sumit.syncUtil.observe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ObserverFactory {

	private static final Logger LGGR = LogManager.getLogger(ObserverFactory.class);

	public CopyObserver getObserverforKey(String key) {
		if ("playlist".equals(key)) {
			return new PlaylistObserver(new FileIO());
		} else {
			LGGR.error("Observer not supported:" + key);
			throw new IllegalArgumentException("Observer not supported:" + key);
		}
	}

}
