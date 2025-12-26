package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This class is the response to an authentification request
 * @version 0.1
 */
public abstract class AuthentificationResponse extends Response {

    protected Boolean authentificationAccepted;
    protected OrganisateurData orgData;
    
    public AuthentificationResponse(boolean authentificationAccepted)
    {
    	this.authentificationAccepted = authentificationAccepted;
    }
    public AuthentificationResponse(boolean authentificationAccepted, OrganisateurData orgData)
    {
    	this.authentificationAccepted = authentificationAccepted;
    	this.orgData = orgData;
    }
    /**
     * This function is executed after the reception of the response.
     * It throws an exception if the request has been refused
     * @throws AuthentificationFailedException
     */
    public void actionsAfterReception() throws AuthentificationFailedException {
	if (!this.authentificationAccepted)
	    {
		throw new AuthentificationFailedException("Wrong email/password or user not found");
	    }
    }

    public Boolean hasBeenAccepted()
    {
	return this.authentificationAccepted;
    }

}
