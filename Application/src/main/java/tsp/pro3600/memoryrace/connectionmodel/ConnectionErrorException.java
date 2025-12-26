package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This Exception is means to be used for generic errors in the connection.
 * It's role is to replace implementation specific errors such as SQLException
 * @version 0.1
 */
public class ConnectionErrorException extends Exception {

    public ConnectionErrorException(String message)
    {
	super(message);
    }
}
