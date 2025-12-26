package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.OrganisateurData;

public class LocalOrganisateurData extends OrganisateurData {

	public LocalOrganisateurData(int idOrg, String email) {
		super(idOrg, email);
	}

	@Override
	public String getSerialized() {
		
		return null;
	}

}
