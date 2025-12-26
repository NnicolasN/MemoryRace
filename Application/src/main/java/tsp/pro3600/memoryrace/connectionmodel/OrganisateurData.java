package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This class represents an organisateur.
 * @version 0.1
 */
public abstract class OrganisateurData implements Serializable {
	
	/**
	 * The organisateur id
	 */
	protected int idOrganisateur;
	
	/**
	 * The organisateur email
	 */
	protected String email;
	
	public OrganisateurData(int idOrg, String email)
	{
		this.email = email;
		this.idOrganisateur = idOrg;
	}
	
	public int getId()
	{
		return this.idOrganisateur;
	}
}
