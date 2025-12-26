package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.CourseData;
import tsp.pro3600.memoryrace.connectionmodel.RemoveCourseRequest;

public class LocalRemoveCourseRequest extends RemoveCourseRequest {

	public LocalRemoveCourseRequest(CourseData course) {
		super(course);
	}

	@Override
	public void actionsBeforeSending() throws Exception {}

	@Override
	public String getSerialized() {
		return "DELETE FROM Courses WHERE id_course=" + this.course.getId();
	}

}
