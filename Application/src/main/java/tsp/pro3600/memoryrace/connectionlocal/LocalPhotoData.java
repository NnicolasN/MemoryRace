package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.PhotoData;

/**
 * The implementation of PhotoData to use with a local db
 * @version 0.1
 */
public class LocalPhotoData extends PhotoData {

	
	public LocalPhotoData(int id, String date,  float latitude, float longitude, byte[] imageBytes) {
		super(id, date, latitude, longitude, imageBytes);
	}
	public LocalPhotoData(String date,  float latitude, float longitude, byte[] imageBytes) {
		super(date, latitude, longitude, imageBytes);
	}

	@Override
	public String getSerialized() {
		return "";
	}

}
