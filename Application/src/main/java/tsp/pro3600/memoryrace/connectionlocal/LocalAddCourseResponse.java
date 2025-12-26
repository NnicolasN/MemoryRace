package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.AddCourseRequest;
import tsp.pro3600.memoryrace.connectionmodel.AddCourseResponse;
import tsp.pro3600.memoryrace.connectionmodel.CourseData;

public class LocalAddCourseResponse extends AddCourseResponse {

	public LocalAddCourseResponse(int id, CourseData courseAdded)
	{
		String nom = courseAdded.getName();
		String date = courseAdded.getDate();
		String lieu = courseAdded.getPlace();
		this.createdCourse = new LocalCourseData(id, nom, lieu, date);
	}
	@Override
	public void actionsAtReception() throws Exception {}

	@Override
	public String getSerialized() {
		return null;
	}

}
