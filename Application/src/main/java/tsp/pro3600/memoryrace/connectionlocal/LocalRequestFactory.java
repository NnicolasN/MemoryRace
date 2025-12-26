package tsp.pro3600.memoryrace.connectionlocal;

import java.util.ArrayList;

import tsp.pro3600.memoryrace.connectionmodel.*;

/**
 * The RequestFactory to use with a local db
 * @version 0.1
 */
public class LocalRequestFactory implements RequestFactory{

	@Override
    public CourseListRequest createCourseListRequest()
    {
	return new LocalCourseListRequest();
    }

	@Override
	public PhotoWithDossardRequest createPhotoWithDossardRequest(int numDossard, CourseData course) {
		return new LocalPhotoWithDossardRequest(numDossard, course);
	}

	@Override
	public AuthentificationRequest createAuthentificationRequest(AuthentificationData authData) {
		return new LocalAuthentificationRequest(authData);
	}

	@Override
	public PhotoUploadRequest createPhotoUploadRequest(ArrayList<PhotoData> photos, CourseData course) {
		return new LocalPhotoUploadRequest(photos, course);
	}

	@Override
	public ModifCourseRequest createModifCourseRequest(CourseData oldCourse, CourseData newCourse) {
		return new LocalModifCourseRequest(oldCourse, newCourse);
	}

	@Override
	public AddCourseRequest createAddCourseRequest(CourseData course, ArrayList<OrganisateurData> organisateurs) {
		return new LocalAddCourseRequest(course, organisateurs);
	}

	@Override
	public RemoveCourseRequest createRemoveCourseRequest(CourseData course) {
		return new LocalRemoveCourseRequest(course);
	}

}
