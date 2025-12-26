package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This class represents a request for all photos of a Course with a dossard number
 * @version 0.1
 */
public abstract class PhotoWithDossardRequest extends Request {

	/**
	 * The dossard number
	 */
	protected int numDossard;
	
	/**
	 * The selected course
	 */
	protected CourseData course;
	
	public PhotoWithDossardRequest(int numDossard, CourseData course)
	{
		this.numDossard = numDossard;
		this.course = course;
	}

	public abstract void actionsBeforeSending() throws Exception;

	public abstract String getSerialized();

}
