package memoryrace.connectionlocal;

import java.sql.ResultSet;
import java.sql.SQLException;

import memoryrace.connectionmodel.AuthentificationFailedException;
import memoryrace.connectionmodel.AuthentificationResponse;

public class LocalAuthentificationResponse extends AuthentificationResponse {

	public LocalAuthentificationResponse(ResultSet rs) throws SQLException
	{
		super(false);
		if (rs.next())
		{
			this.authentificationAccepted = true;
		}
	}
	@Override
	public String getSerialized() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void actionsAtReception() throws AuthentificationFailedException {
		if (!this.hasBeenAccepted()) {
			throw new AuthentificationFailedException("Mauvais couple email / mot de passe");
		}
	}

}
