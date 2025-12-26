package tsp.pro3600.memoryrace.connectionmodel;

/**
 * 
 * @version 0.1
 */
public abstract class AuthentificationRequest extends Request implements Serializable {

    private AuthentificationData user;

    public AuthentificationRequest(AuthentificationData user) {
	this.user = user;
    }

    public AuthentificationData getUser() {
	return this.user;
    }

    public abstract String getSerialized();
}
