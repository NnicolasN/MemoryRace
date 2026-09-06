package memoryrace.connectionlocal;

import java.util.ArrayList;

import memoryrace.connectionmodel.CourseData;
import memoryrace.connectionmodel.PhotoData;
import memoryrace.connectionmodel.PhotoUploadRequest;

public class LocalPhotoUploadRequest extends PhotoUploadRequest {

	
	public LocalPhotoUploadRequest(ArrayList<PhotoData> photos, CourseData course) {
		super(photos, course);
	}
	@Override
	public String getSerialized() {
		return null;
	}

}
