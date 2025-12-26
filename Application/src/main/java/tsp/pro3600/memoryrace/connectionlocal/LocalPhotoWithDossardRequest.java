package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.CourseData;
import tsp.pro3600.memoryrace.connectionmodel.PhotoWithDossardRequest;

/**
 * The implementation of PhotoWithDossardRequest to use with a local db
 * @version 0.1
 */
public class LocalPhotoWithDossardRequest extends PhotoWithDossardRequest {

	public LocalPhotoWithDossardRequest(int numDossard, CourseData course) {
		super(numDossard, course);
	}

	@Override
	public void actionsBeforeSending() throws Exception {
		System.out.println("Sending LocalPhotoWithDossardRequest…");
	
	}

	@Override
	public String getSerialized() {
		return "SELECT * FROM Photos "
				+ "JOIN Detections ON Detections.id_photo = Photos.id_photo "
				+ "WHERE no_dossard = " + this.numDossard + " AND "
						+ "id_course = "+ this.course.getId();
	}

}
