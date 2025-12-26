package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This exception is thrown when the authentification has failed.
 * (Wrong credentials or user does not exist).
 */
public class UnauthorizedAccessException extends Exception {

	public UnauthorizedAccessException(String message)
	{
		super(message);
	}
}
