package tsp.pro3600.memoryrace.connectionmodel;

/**
 * The is the mother class of all Response types
 * @version 0.1
 */
public abstract class Response implements Serializable{

	/**
	 * Actions to be executed after receiving the response.
	 * It can be throwing some exceptions are printing something.
	 * WIP - not implemented yet 
	 * @throws Exception
	 */
    public abstract void actionsAtReception() throws Exception;
   
    public abstract String getSerialized();

}
