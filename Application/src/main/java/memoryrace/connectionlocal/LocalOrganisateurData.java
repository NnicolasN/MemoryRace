package memoryrace.connectionlocal;

import memoryrace.connectionmodel.OrganisateurData;

public class LocalOrganisateurData extends OrganisateurData {

	public LocalOrganisateurData(int idOrg, String email) {
		super(idOrg, email);
	}

	@Override
	public String getSerialized() {
		
		return null;
	}

}
