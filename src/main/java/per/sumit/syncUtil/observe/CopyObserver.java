package per.sumit.syncUtil.observe;

import per.sumit.syncUtil.Configuration;

/**
 * An observer class which can take action on any copy
 * 
 * @author samurai
 *
 */
public interface CopyObserver {
	/**
	 * Notify before the copy starts
	 * 
	 * @param copyConfig
	 *            active ccopy configuration
	 */
	public void notifyPreCopy(Configuration copyConfig);

	/**
	 * Notify after copy finishes
	 * 
	 * @param copyConfig
	 *            active ccopy configuration
	 */
	public void notifyPostCopy(Configuration copyConfig);

}
