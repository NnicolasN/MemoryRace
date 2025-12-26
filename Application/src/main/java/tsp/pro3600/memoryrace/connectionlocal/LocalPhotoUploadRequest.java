package tsp.pro3600.memoryrace.connectionlocal;

import java.util.ArrayList;

import tsp.pro3600.memoryrace.connectionmodel.CourseData;
import tsp.pro3600.memoryrace.connectionmodel.PhotoData;
import tsp.pro3600.memoryrace.connectionmodel.PhotoUploadRequest;

public class LocalPhotoUploadRequest extends PhotoUploadRequest {

	
	public LocalPhotoUploadRequest(ArrayList<PhotoData> photos, CourseData course) {
		super(photos, course);
	}
	@Override
	public String getSerialized() {
		return null;
	}

}
