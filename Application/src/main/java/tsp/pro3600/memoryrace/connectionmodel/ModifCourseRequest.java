package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This class represents a request to modify a course.
 * @version 0.1
 */
public abstract class ModifCourseRequest extends Request {

	/**
	 * The course to modify
	 */
	protected CourseData oldCourse;
	/**
	 * The CourseData object that represents the new informations.
	 * The id is note important here (can be set to -1).
	 * @see DataFactory
	 */
	protected CourseData newCourse;
	
	public ModifCourseRequest(CourseData oldCourse, CourseData newCourse)
	{
		this.newCourse = newCourse;
		this.oldCourse = oldCourse;
	}
	
	@Override
	public abstract void actionsBeforeSending() throws Exception;

	@Override
	public abstract String getSerialized();
	
	public CourseData getOldCourse()
	{
		return this.oldCourse;
	}

}
