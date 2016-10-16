package per.sumit.syncUtil;

import java.io.Closeable;

/**
 * Platform specific implementation of copy file logic
 * 
 * @author samurai
 *
 */
public interface FileCopier extends Closeable {

	/**
	 * Copies data from source to destination
	 * 
	 * @param source
	 *            from where to sync
	 * @param destination
	 *            where to sync to
	 */
	public void copy(String source, String destination);

	/**
	 * Copies data from source to destination. Can handle special scenarios like
	 * MTP
	 * 
	 * @param source
	 *            from where to sync
	 * @param destination
	 *            where to sync to
	 */
	// TODO:Can move destinationType to enum
	public void copy(String source, String destination, String destinationType);

}
