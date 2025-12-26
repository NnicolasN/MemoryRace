package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This class represents a photo
 * @version 0.1
 */
public abstract class PhotoData implements Serializable {

	/**
	 * The photo unique id
	 */
    protected int id;
    
    /**
     * When to photo was taken
     */
    protected String date;
    
    /**
     * The latitude of the place where the photo was taken
     */
    protected float latitude;
    
    /**
     * The latitude of the place where the photo was taken
     */
    protected float longitude;
    
    /**
     * The actual image, in the form of a byte array
     */
    protected byte[] imageBytes;
    public PhotoData(String date, float latitude, float longitude, byte[] imageBytes)
    {
    	this(-1, date, latitude, longitude, imageBytes);
    }
    
    public PhotoData(int id, String date, float latitude, float longitude, byte[] imageBytes) {
	this.id = id;
	this.date = date;
	this.latitude = latitude;
	this.longitude = longitude;
	this.imageBytes = imageBytes;
    }

    public abstract String getSerialized();
    
    public int getId() {
    	return this.id;
    }
    
    public String getDate()
    {
    	return this.date;
    }

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}


	public byte[] getImageBytes() {
		return imageBytes;
	}

    
}
