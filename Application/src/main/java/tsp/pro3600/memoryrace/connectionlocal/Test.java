package tsp.pro3600.memoryrace.connectionlocal;

import java.util.ArrayList;

import tsp.pro3600.memoryrace.connectionmodel.*;

/* +------------+
 * | TEST CLASS |
 * +------------+
 * FOR DEMONSTRATION PURPOSE ONLY !
 * Will be removed in final version
 */
public class Test {

	public static void main(String[] args) throws ConnectionErrorException, AuthentificationFailedException, UnauthorizedAccessException
	{
		LocalConnectionHandler conn = new LocalConnectionHandler("127.0.0.1", 3306, "root", "password", "/tmp/photos/");
		conn.connect();
		RequestFactory reqFact = conn.createRequestFactory();
		CourseListRequest reqCL = reqFact.createCourseListRequest();
		conn.setRequest(reqCL);
		conn.execute();
		CourseListResponse resCL = (CourseListResponse)conn.getResponse();
		ArrayList<CourseData> courses = resCL.getCourseList();
		for(CourseData cd : courses)
		{
			System.out.println(cd.getName());
		}
		
		PhotoWithDossardRequest reqPL = reqFact.createPhotoWithDossardRequest(1, courses.get(0));
		conn.setRequest(reqPL);
		conn.execute();
		PhotoListResponse resPL = (PhotoListResponse)conn.getResponse();
		ArrayList<PhotoData> photos = resPL.getPhotoList();
		for(PhotoData pd : photos)
		{
			System.out.println(pd.getDate());
		}
		conn.disconnect();
	}
}
