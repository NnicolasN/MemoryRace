package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.*;

/**
 * This class is the representation of a Course for use with a local db
 * @version 0.1
 */
public class LocalCourseData extends CourseData {

     public LocalCourseData(int id, String name, String place, String date) {
	 super(id, name, place, date);
    }
     
 	/**
 	 * The choice have been made to use Serializable interface for building the query,
 	 * as it has no other usage because we are in the context of a local db
 	 * @return the SQL query
 	*/
    public String getSerialized() {

    	return "WHERE id = " + this.id + " AND  nom = " + this.name + "AND date = " + this.date + " AND lieu = " +this.place + ";";
    }
}
