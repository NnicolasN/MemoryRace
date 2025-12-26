package tsp.pro3600.memoryrace.connectionmodel;

import java.util.ArrayList;

/**
 * This class represents a response which consists of a list of Course
 * @version 0.1
 */
public abstract class CourseListResponse extends Response implements Serializable {

	/**
	 * The list of course
	 */
    protected ArrayList<CourseData> courses;

    public CourseListResponse(ArrayList<CourseData> courses)
    {
	this.courses = courses;
    }

    public ArrayList<CourseData> getCourseList() {
	return this.courses;
    }

    public abstract String getSerialized();

}
