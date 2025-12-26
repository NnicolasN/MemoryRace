package tsp.pro3600.memoryrace.connectionlocal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tsp.pro3600.memoryrace.connectionmodel.AddCourseRequest;
import tsp.pro3600.memoryrace.connectionmodel.CourseData;
import tsp.pro3600.memoryrace.connectionmodel.OrganisateurData;

public class LocalAddCourseRequest extends AddCourseRequest {

	public LocalAddCourseRequest(CourseData newCourse, ArrayList<OrganisateurData> organisateurs) {
		super(newCourse, organisateurs);
	}

	@Override
	public String getSerialized() {
		String statement = "";
		if (this.newCourse.getId() == -1 )
		{
			statement = "INSERT INTO Courses (nom, date, lieu) VALUES (\"" + 
		this.newCourse.getName() + "\",\"" +
		this.newCourse.getDate() + "\",\"" +
		this.newCourse.getPlace() + "\") RETURNING id_course;";
		}
		else {
			statement = "INSERT INTO Courses (id_course, nom, date, lieu) VALUES (\"" +
			this.newCourse.getId() + "\",\"" +
			this.newCourse.getName() + "\",\"" +
			this.newCourse.getDate() + "\",\"" +
			this.newCourse.getPlace() + "\") RETURNING id_course;";
	}
		return statement;
	}
	
	public String getOrganisateurStatement(int id) throws SQLException {
		String values = "";
		for (int i = 0; i < this.organisateurs.size(); i++)
		{
			values += "(" + id + "," + this.organisateurs.get(i).getId() + ")";
			if (i < this.organisateurs.size()-1)
			{
				values += ",";
			}
		}
		return "INSERT INTO Organise (id_course, id_organisateur) VALUES " + values + ";";
	}

	@Override
	public void actionsBeforeSending() throws Exception {
	}

	public CourseData getNewCourse() {
		return this.newCourse;
	}

}
