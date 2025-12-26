package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This class is the response to a AddCourseRequest
 * @version 0.1
 */
public abstract class AddCourseResponse extends Response {

	/**
	 * The course created (with id)
	 */
	
	protected CourseData createdCourse;
	@Override
	public abstract void actionsAtReception() throws Exception;

	@Override
	public abstract String getSerialized();
	
	public CourseData getCourse() {
		return this.createdCourse;
	}

}
