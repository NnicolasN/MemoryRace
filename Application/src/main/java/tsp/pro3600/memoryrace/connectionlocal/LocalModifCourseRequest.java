package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.CourseData;
import tsp.pro3600.memoryrace.connectionmodel.ModifCourseRequest;

public class LocalModifCourseRequest extends ModifCourseRequest {

	public LocalModifCourseRequest(CourseData oldCourse, CourseData newCourse) {
		super(oldCourse, newCourse);
	}

	@Override
	public void actionsBeforeSending() throws Exception {}

	@Override
	public String getSerialized() {
		return "UPDATE Courses SET date=\"" + this.newCourse.getDate() +
				"\", lieu=\"" + this.newCourse.getPlace() +
				"\", nom=\"" + this.newCourse.getName() +
				"\" WHERE id_course=" + this.oldCourse.getId();
	}

}
