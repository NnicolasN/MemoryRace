package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This interface represents a DataFactory
 * @see ConnectionHandler.createDataFactory()
 * @version 0.1
 */
public interface DataFactory {
	/**
	 * Return a new CourseData
	 * @param id course unique id
	 * @param name course name
	 * @param place course place
	 * @param date course date
	 * @return a new CourseData object
	 */
    public CourseData createCourseData(int id, String name, String place, String date);
    
	/**
	 * Return a new CourseData with id of -1
	 * @param name course name
	 * @param place course place
	 * @param date course date
	 * @return a new CourseData object
	 * The id will be set to -1
	 * For use with requests that doesn't need id.
	 */
    public CourseData createCourseData(String name, String place, String date);
    
    /**
	 * Return a new AuthentificationData
	 * @param email the provided email
	 * @param place hashedPassword the provided password (hashed)
	 * @return a new AuthentificationData object
	 */
    public AuthentificationData createAuthentificationData(String email, String hashedPassword);
    
    /**
	 * Return a new OrganisateurData
	 * @param idOrg the organisateur id
	 * @param email the organisateur email
	 * @return a new OrganisateurData object
	 */
    public OrganisateurData createOrganisateurData(int idOrg, String email);
    
    /**
	 * Return a new PhotoData
	 * @param id the photo id
	 * @param date the photo date
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param imageBytes the image itself
	 * @return a new PhotoData object
	 */
    public PhotoData createPhotoData(int id, String date, float latitude, float longitude, byte[] imageBytes);
    
    /**
	 * Return a new PhotoData with id -1
	 * @param date the photo date
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param imageBytes the image itself
	 * @return a new PhotoData object
	 * The id will be set to -1
	 * For use with requests that doesn't need id.
	 */
    public PhotoData createPhotoData(String date, float latitude, float longitude, byte[] imageBytes);

}

