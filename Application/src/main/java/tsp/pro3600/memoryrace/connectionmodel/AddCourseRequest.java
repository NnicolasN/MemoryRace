package tsp.pro3600.memoryrace.connectionmodel;

import java.util.ArrayList;

/**
 * This class represents a request to add a new Course
 * @version 0.1
 */
public abstract class AddCourseRequest extends Request {
	
	/**
	 * The course to add.
	 * The id is not important as it will be override by implementation.
	 * It may be set to -1.
	 * @see DataFacory
	 */
	protected CourseData newCourse;
	
	/**
	 * A list of organisators, who will be able to modify it and upload photos.
	 */
	protected ArrayList<OrganisateurData> organisateurs;
	
	public AddCourseRequest(CourseData newCourse, ArrayList<OrganisateurData> organisateurs)
	{
		this.newCourse = newCourse;
		this.organisateurs = organisateurs;
	}

	@Override
	public abstract String getSerialized();

}
