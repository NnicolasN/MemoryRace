package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.AuthentificationData;

public class LocalAuthentificationData extends AuthentificationData {

	public LocalAuthentificationData(String email, String hashedPassword) {
		super(email, hashedPassword);
	}

	@Override
	public String getSerialized() {
		return null;
	}

}
