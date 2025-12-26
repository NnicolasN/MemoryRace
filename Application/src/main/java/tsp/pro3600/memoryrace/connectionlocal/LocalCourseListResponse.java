package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * This class is the implementation of CourseListResponse for use with a local db
 * @version 0.1
 */
public class LocalCourseListResponse extends CourseListResponse {

    public LocalCourseListResponse(ResultSet rs) throws SQLException
    {
    	super(null); // initialize upper class with null ArrayList
    	ArrayList<CourseData> courses = new ArrayList<>();
    	// Construction of the array with each query row
    	while(rs.next()) {
    		Date date = rs.getDate("date");
    		String fmtDate = date.toString();
    		courses.add(new LocalCourseData(rs.getInt("id_course"), rs.getString("nom"), rs.getString("lieu"), fmtDate));
    	}
    	
    	this.courses = courses; // replace upper class parameter
    }

    @Override
    public void actionsAtReception()
    {
	System.out.println("LocalCourseListResponse has been received…");
    }

    public String getSerialized(){
    	return ""; // Useless for use with a local db
    }


}
