package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This class is thrown when the credentials provided by user are wrong
 * @version 0.1
 */

public class AuthentificationFailedException extends Exception {

    public AuthentificationFailedException(String message)
    {
	super(message);
    }
}
