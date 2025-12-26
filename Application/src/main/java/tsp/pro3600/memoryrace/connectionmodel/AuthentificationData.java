package tsp.pro3600.memoryrace.connectionmodel;

// WIP - not implemented yet
public abstract class AuthentificationData implements Serializable {

	/**
	 * The email of the user who tries to auth.
	 */
    private String email;
    
    /**
     * User’s hashed password
     */
    private String hashedPassword;

    public AuthentificationData(String email, String hashedPassword){
	this.email = email;
	this.hashedPassword = hashedPassword;
    }


    public abstract String getSerialized();
    
    public String getEmail()
    {
    	return this.email;
    }
    
    public String getHashedPassword()
    {
    	return this.hashedPassword;
    }

}
