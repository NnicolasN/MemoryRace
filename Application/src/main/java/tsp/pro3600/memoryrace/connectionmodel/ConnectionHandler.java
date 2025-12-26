package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This is a generic interface to communicate with the database and other components.
 * This abstraction allow to use the same code for a local database and a remote one.
 * @version 0.1
 */
public interface ConnectionHandler {

	/**
	 * Connect to the database / the server
	 */
    public void connect() throws ConnectionErrorException;

    /**
     * Set the Request to be executed
     * @param req the request to execute or transmit on call of execute()
     */
    public void setRequest(Request req);

    /**
     * Execute or transmit the request and wait for response
     * @throws ConnectionErrorException
     * @throws AuthentificationFailedException 
     * @throws UnauthorizedAccessException 
     */
    public void execute() throws ConnectionErrorException, AuthentificationFailedException, UnauthorizedAccessException;
   
    /**
     * Get the last response for the database / remote host
     * @return the response
     */
    public Response getResponse();

    /**
     * Disconnect from the database / the remote host
     * @throws ConnectionErrorException
     */
    public void disconnect() throws ConnectionErrorException;

    /**
     * Create a DataFactory.
     * A DataFactory is an interface to create Data objects adapted to the actual ConnectionHandler used.
     * For example, a LocalConnectionHandler needs LocalCourseData so the DataFactory returned by LocalConnectionHandler is a LocalDataFactory
     * which makes this kind of CourseData.
     * @return a new DataFactory
     */
    public DataFactory createDataFactory();

    /**
     * Create a RequestFactory
     * @see createDataFactory()
     * @return a new RequestFacotry
     */
    public RequestFactory createRequestFactory();

    /**
     * Create a ResponseFactory
     * @see createDataFactory()
     * @return a new ResponseFacotry
     */
    public ResponseFactory createResponseFactory();
    
    /**
     * Get the current authentified organisateur
     * null if none.
     * @return OrganisateurData object associated with current user.
     */
    public OrganisateurData getAuthentifiedOrganisateur();
}
    
