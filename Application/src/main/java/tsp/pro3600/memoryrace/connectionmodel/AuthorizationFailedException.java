package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This exception is thrown when an authentification request has been refused.
 * (Most likely the provided credentials aren't good or the user doesn't exist)
 */
public class AuthorizationFailedException extends Exception {

    public AuthorizationFailedException(String message){
	super(message);
    }
}
