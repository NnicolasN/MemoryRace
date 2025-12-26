package tsp.pro3600.memoryrace.connectionmodel;

/**
 * This class represents a request to get the list of all registered Course
 * @version 0.1
 */
public abstract class CourseListRequest extends Request implements Serializable {

    public abstract String getSerialized();
    
}
