package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This class represents a request to removea course.
 */
public abstract class RemoveCourseRequest extends Request {

	/**
	 * The course to suppress
	 */
	protected CourseData course;
	
	public RemoveCourseRequest(CourseData course)
	{
		this.course = course;
	}
	
	public CourseData getCourse()
	{
		return this.course;
	}
	@Override
	public abstract void actionsBeforeSending() throws Exception;

	@Override
	public abstract String getSerialized();

}
