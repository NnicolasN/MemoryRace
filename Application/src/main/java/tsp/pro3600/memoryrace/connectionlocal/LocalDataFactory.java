package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.*;

/**
 * This class is the DataFactory to use with a local db
 * @version 0.1
 */
public class LocalDataFactory implements DataFactory{

   public CourseData createCourseData(int id, String name, String place, String date)
    {
	return new LocalCourseData(id, name, place, date);
    }

   public CourseData createCourseData(String name, String place, String date)
    {
	return new LocalCourseData(-1, name, place, date);
    }
   public AuthentificationData createAuthentificationData(String email, String hashedPassword){
	return new LocalAuthentificationData(email, hashedPassword);
	}
   public OrganisateurData createOrganisateurData(int idOrg, String email) {
	return new LocalOrganisateurData(idOrg, email);
}

   @Override
	public PhotoData createPhotoData(int id, String date, float latitude, float longitude, byte[] imageBytes) {
		return new LocalPhotoData(id, date, longitude, longitude, imageBytes);
	}

	@Override
	public PhotoData createPhotoData(String date, float latitude, float longitude, byte[] imageBytes) {
		return new LocalPhotoData(date, longitude, longitude, imageBytes);
	}
}