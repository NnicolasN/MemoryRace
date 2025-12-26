package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.AuthentificationData;
import tsp.pro3600.memoryrace.connectionmodel.AuthentificationRequest;

public class LocalAuthentificationRequest extends AuthentificationRequest {

	public LocalAuthentificationRequest(AuthentificationData user) {
		super(user);
	}

	@Override
	public String getSerialized() {
		return "SELECT * FROM Organisateurs WHERE email=\"" + this.getUser().getEmail()
				+ "\" AND password=\"" + this.getUser().getHashedPassword() + "\";";
	}

	@Override
	public void actionsBeforeSending() throws Exception {
	}

}
