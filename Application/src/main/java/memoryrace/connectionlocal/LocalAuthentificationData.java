package memoryrace.connectionlocal;

import memoryrace.connectionmodel.AuthentificationData;

public class LocalAuthentificationData extends AuthentificationData {

	public LocalAuthentificationData(String email, String hashedPassword) {
		super(email, hashedPassword);
	}

	@Override
	public String getSerialized() {
		return null;
	}

}
