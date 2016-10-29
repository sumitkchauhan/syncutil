package per.sumit.syncUtil.exceptions;

/**
 * To denote any exception during copying
 * 
 * @author samurai
 *
 */
public class SyncException extends RuntimeException {

	public SyncException(String message) {
		super(message);
	}

	public SyncException(String message, Throwable baseException) {
		super(message, baseException);
	}
}
