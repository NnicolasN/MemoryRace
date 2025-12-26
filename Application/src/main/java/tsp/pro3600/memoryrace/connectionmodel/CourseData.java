package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This class is the representation of a Course
 * It contains all the necessary parameters
 * @version 0.1
 */
public abstract class CourseData implements Serializable {

	/**
	 * The course unique id
	 */
	protected int id;
	
	/**
	 * The name of the course
	 */
    protected String name;
    
    /**
     * The place of the course
     */
    protected String place;
    
    /**
     * The date of the course
     */
    protected String date;

    public CourseData(int id, String name, String place, String date) {
	this.name = name;
	this.place = place;
	this.date = date;
	this.id = id;
    }

    public abstract String getSerialized();
    
    public String getName()
    {
    	return this.name;
    }
    
    public int getId()
    {
    	return this.id;
    }

	public String getDate() {
		// TODO Auto-generated method stub
		return this.date;
	}
	
	public String getPlace()
	{
		return this.place;
	}
}
					    
