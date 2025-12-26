package tsp.pro3600.memoryrace.connectionmodel;

import java.util.ArrayList;

/**
 * This class represents a response that consists of a list of photos
 * @version 0.1
 */
public abstract class PhotoListResponse extends Response {

	/**
	 * The list of photos
	 */
    protected ArrayList<PhotoData> photoList;

    public PhotoListResponse() {
    	this.photoList = null;
    }
 
    public ArrayList<PhotoData> getPhotoList() {
    	return this.photoList;
    }
}
