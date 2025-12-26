package tsp.pro3600.memoryrace.connectionmodel;

import java.util.ArrayList;

/**
 * This class represents a request to upload photos for a certain course.
 */
public abstract class PhotoUploadRequest extends Request {
	
	/**
	 * The photos to upload.
	 * The id is not important (may be -1)
	 * @see DataFacory
	 */
	protected ArrayList<PhotoData> photos;
	
	/**
	 * The course to which photos are related
	 */
	protected CourseData course;

	public PhotoUploadRequest(ArrayList<PhotoData> photos, CourseData course)
	{
		this.course = course;
		this.photos = photos;
	}
	@Override
	public void actionsBeforeSending() throws Exception {}

	@Override
	public abstract String getSerialized();
	
	public ArrayList<PhotoData> getPhotos()
	{
		return this.photos;
	}
	
	public CourseData getCourse()
	{
		return this.course;
	}
}
