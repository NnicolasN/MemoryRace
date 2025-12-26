package tsp.pro3600.memoryrace.connectionmodel;

import java.util.ArrayList;

/**
 * The interface represents a RequestFactory
 * @see ConnectionHandler.createDataFactory()
 * @version 0.1
 */
public interface RequestFactory {

	/**
	 * Create a new CourseListRequest
	 * @return a new CourseListRequest
	 */
    public CourseListRequest createCourseListRequest();
    
    /**
     * Create a new PhotoWithDossardRequest
     * @param numDossard dossard number
     * @param course selected course
     * @return a new PhotoWithDossardRequest
     */
    public PhotoWithDossardRequest createPhotoWithDossardRequest(int numDossard, CourseData course);

    /**
     * Create a new AuthentificationRequets
     * @param authData the provided credentials
     * @return a new AuthentificationRequest
     */
	public AuthentificationRequest createAuthentificationRequest(AuthentificationData authData);
	
	/**
     * Create a new PhotoUploadRequest
     * @param photos the list of photos to upload
     * @param course the course with which the photos are related.
     * @return a new PhotoUploadRequest
     */
	public PhotoUploadRequest createPhotoUploadRequest(ArrayList<PhotoData> photos, CourseData course);
	
	/**
     * Create a new ModifCourseRequest
     * @param oldCourse the course to modify
     * @param newCourse the new informations
     * @return a new ModifCourseRequest
     * The id in newCourse is not important (may be -1).
     * @see DataFactory
     */
	public ModifCourseRequest createModifCourseRequest(CourseData oldCourse, CourseData newCourse);
	
	/**
     * Create a new AddCourseRequest
     * @param course the course informations
     * @param organisateurs the list of course organisateur
     * @return a new AddCourseRequest
     * The id in course is not important (may be -1).
     * @see DataFactory
     */
	public AddCourseRequest createAddCourseRequest(CourseData course, ArrayList<OrganisateurData> organisateurs);
	
	/**
     * Create a new RemoveCourseRequestv
     * @param course the course to remove
     * @return a new RemoveCourseRequest
     */
	public RemoveCourseRequest createRemoveCourseRequest(CourseData course);
}
