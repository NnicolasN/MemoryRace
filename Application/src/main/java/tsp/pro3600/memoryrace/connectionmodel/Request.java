package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This class is the mother class of all Request types
 * @version 0.1
 */
public abstract class Request implements Serializable{

	/**
	 * Actions to be executed before sending the request.
	 * It can be throwing some exceptions are printing something.
	 * WIP - not implemented yet 
	 * @throws Exception
	 */
    public abstract void actionsBeforeSending() throws Exception;

	public abstract String getSerialized();
    
}
