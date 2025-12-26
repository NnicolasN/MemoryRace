package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This interface represents an object which can be passed through a socket connection
 * @version 0.1
 */
public interface Serializable {

	/**
	 * Return a serialized version of the object, for use for communication with a remote host
	 * @return a string that represent this object
	 */
    public String getSerialized();

}
