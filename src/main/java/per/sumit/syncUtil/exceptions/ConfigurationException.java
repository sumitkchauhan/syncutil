package per.sumit.syncUtil.exceptions;

/**
 * To denote any exception in configuration
 * @author samurai
 *
 */
public class ConfigurationException extends RuntimeException {

	public ConfigurationException(String message){
		super(message);
	}
	public ConfigurationException(String message, Throwable baseException){
		super(message, baseException);
	}
}
