package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.*;

/**
 * This class is the local implementation of CourseListRequest to use with a local db
 * @version 0.1
 */
public class LocalCourseListRequest extends CourseListRequest {

    public void actionsBeforeSending() {
	System.out.println("Sending LocalCourseListRequest…");
    }

    /**
     * @see LocalCourseData.getSerialized()
     * @return the SQL query
     */
    public String getSerialized() {
	return "SELECT id_course, date, lieu, nom FROM Courses;";
    }
}
