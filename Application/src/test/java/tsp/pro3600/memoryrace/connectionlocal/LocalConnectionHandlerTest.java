package tsp.pro3600.memoryrace.connectionlocal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import tsp.pro3600.memoryrace.connectionmodel.*;
import tsp.pro3600.memoryrace.interfaceswing.FenetreOrganisateurCreer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class LocalConnectionHandlerTest {
/*
 * For unit tests to run, you need to setup a mariadb instance with the right data :
 * Database/JUnit.sql
 */
	LocalConnectionHandler conn;
	RequestFactory reqFact;
	DataFactory dataFact;
	
	
	@BeforeEach
	public void setUp() throws ConnectionErrorException 
	{
		this.conn = new LocalConnectionHandler("127.0.0.1", 3306, "root", "password", "/tmp/junit-photos/");
		this.reqFact = this.conn.createRequestFactory();
		this.dataFact = this.conn.createDataFactory();
		this.conn.connect();
	}
	
	@Test
	@DisplayName("Ensure an exception is thrown if the we failed to connect to the db")
	public void wrongDb()
	{
		LocalConnectionHandler conn = new LocalConnectionHandler("10.0.0.1", 3306, "root", "password", "/tmp/junit-photos/");
		Exception ex = assertThrows(ConnectionErrorException.class, () -> {conn.connect();});
		assertTrue(ex.getMessage().contains("Impossible de se connecter à la base de données"));
	}

	
	@Test
	@DisplayName("Ensure we can get the course list")
	public void courseList()
	{
		CourseListRequest courseReq = this.reqFact.createCourseListRequest();
		this.conn.setRequest(courseReq);
		
		assertDoesNotThrow(() -> {
		this.conn.execute();
		});
		
		assertTrue(this.conn.getResponse() instanceof LocalCourseListResponse);
		ArrayList<CourseData> courses = ((LocalCourseListResponse) this.conn.getResponse()).getCourseList();
		/* assertTrue(courses.get(0).getName() == "Course 1");
		assertTrue(courses.get(0).getDate() == "2000-01-01");
		assertTrue(courses.get(0).getPlace() == "Paris");
		assertTrue(courses.get(0).getId() == 0);
		
		assertTrue(courses.get(1).getName() == "Course 2");
		assertTrue(courses.get(1).getDate() == "2020-12-12");
		assertTrue(courses.get(1).getPlace() == "Nice");
		assertTrue(courses.get(1).getId() == 1); */
		
	}
	
	@Test
	@DisplayName("Ensure we can get photos of Course 1 with dossard 1")
	public void dossardCorrect()
	{
		CourseData course1 = this.dataFact.createCourseData(1, "Course 1", "Paris", "2000-01-01");
		PhotoWithDossardRequest reqDoss = this.reqFact.createPhotoWithDossardRequest(1, course1);
		this.conn.setRequest(reqDoss);
		assertDoesNotThrow(() -> {
		conn.execute();
		});
		
		assertTrue(this.conn.getResponse() instanceof LocalPhotoListResponse);
		ArrayList<PhotoData> photos = ((LocalPhotoListResponse) this.conn.getResponse()).getPhotoList();
		
		assertTrue(photos.size() == 1);
		assertTrue(photos.get(0).getId() == 1);	
	}
	
	@Test
	@DisplayName("Ensure what happens if the course doesn’t exist (empty list) in PhotoWithDossardRequest")
	public void dossardWrongCourse()
	{
		CourseData course1 = this.dataFact.createCourseData(3, "Course Inconnue", "Palaiseau", "2004-01-01");
		PhotoWithDossardRequest reqDoss = this.reqFact.createPhotoWithDossardRequest(1, course1);
		this.conn.setRequest(reqDoss);
		
		assertDoesNotThrow(() -> {
		this.conn.execute();
		});

		assertTrue(this.conn.getResponse() instanceof LocalPhotoListResponse);
		assertTrue(((LocalPhotoListResponse) this.conn.getResponse()).getPhotoList().size() == 0);
	}
	
	@Test
	@DisplayName("Ensure what happens if the dossard number doesn’t exist (empty list) in PhotoWithDossardRequest")
	public void dossardWrongNumber()
	{
		CourseData course1 = this.dataFact.createCourseData(1, "Course 1", "Paris", "2000-01-01");
		PhotoWithDossardRequest reqDoss = this.reqFact.createPhotoWithDossardRequest(666, course1);
		this.conn.setRequest(reqDoss);
		
		assertDoesNotThrow(() -> {
		this.conn.execute();
		});

		assertTrue(this.conn.getResponse() instanceof LocalPhotoListResponse);
		assertTrue(((LocalPhotoListResponse) this.conn.getResponse()).getPhotoList().size() == 0);
	}
	
	@Test
	@DisplayName("Ensure that authentification works")
	public void authRight()
	{
		AuthentificationData authData = this.dataFact.createAuthentificationData("test@junit.org", "junit");
		AuthentificationRequest authReq = this.reqFact.createAuthentificationRequest(authData);
		this.conn.setRequest(authReq);


		assertDoesNotThrow(() -> {
			this.conn.execute();
		});


		
		assertTrue(this.conn.getResponse() instanceof LocalAuthentificationResponse);
		assertTrue(((LocalAuthentificationResponse) this.conn.getResponse()).hasBeenAccepted());
		assertTrue(this.conn.getAuthentifiedOrganisateur().getId() == 1);
	}
	
	@Test
	@DisplayName("Ensure that authentification works (wrong credentials)")
	public void authWrongCreds()
	{
		AuthentificationData authData = this.dataFact.createAuthentificationData("test@junit.org", "gnuisnotunix");
		AuthentificationRequest authReq = this.reqFact.createAuthentificationRequest(authData);
		this.conn.setRequest(authReq);
		
		Exception ex = assertThrows(AuthentificationFailedException.class, () -> {
			this.conn.execute();
		});
		
		assertTrue(this.conn.getResponse() instanceof LocalAuthentificationResponse);
		assertTrue(!((LocalAuthentificationResponse) this.conn.getResponse()).hasBeenAccepted());
		assertTrue(this.conn.getAuthentifiedOrganisateur() == null);
		assertTrue(ex.getMessage().contains("Mauvais couple email / mot de passe"));
	}
	
	@Test
	@DisplayName("Ensure that authentification works (empty credentials)")
	public void authEmptyCreds()
	{
		AuthentificationData authData = this.dataFact.createAuthentificationData("", "");
		AuthentificationRequest authReq = this.reqFact.createAuthentificationRequest(authData);
		this.conn.setRequest(authReq);
		
		Exception ex = assertThrows(AuthentificationFailedException.class, () -> {
			this.conn.execute();
		});
		
		assertTrue(this.conn.getResponse() instanceof LocalAuthentificationResponse);
		assertTrue(!((LocalAuthentificationResponse) this.conn.getResponse()).hasBeenAccepted());
		assertTrue(this.conn.getAuthentifiedOrganisateur() == null);
		
		assertTrue(ex.getMessage().contains("Mauvais couple email / mot de passe"));
	}
	
	@Test
	@DisplayName("Ensure we can remove a course that exists")
	public void removeCourse()
	{
		authRight();
		CourseData course1 = this.dataFact.createCourseData(1, "Course 1", "Paris", "2000-01-01");
		RemoveCourseRequest rmReq = this.reqFact.createRemoveCourseRequest(course1);
		this.conn.setRequest(rmReq);
		
		assertDoesNotThrow(() -> {
		this.conn.execute();
		});
		
		CourseListRequest courseReq = this.reqFact.createCourseListRequest();
		this.conn.setRequest(courseReq);
		
		assertDoesNotThrow(() -> {
		this.conn.execute();
		});
		
		assertTrue(this.conn.getResponse() instanceof LocalCourseListResponse);
		ArrayList<CourseData> courses = ((LocalCourseListResponse) this.conn.getResponse()).getCourseList();
		
		/*
		assertTrue(courses.size() == 1);
		assertTrue(courses.get(0).getName() == "Course 2");
		assertTrue(courses.get(0).getDate() == "2020-12-12");
		assertTrue(courses.get(0).getPlace() == "Nice");
		assertTrue(courses.get(0).getId() == 1);*/
	}
	
	@Test
	@DisplayName("Try to remove a course without the rights")
	public void removeCourseNoRights()
	{
		authRight();
		CourseData course1 = this.dataFact.createCourseData(2, "Course 2", "Nice", "2020-12-12");
		RemoveCourseRequest rmReq = this.reqFact.createRemoveCourseRequest(course1);
		this.conn.setRequest(rmReq);
		
		assertThrows(UnauthorizedAccessException.class, () -> {
		this.conn.execute();
		});
		/*
		CourseListRequest courseReq = this.reqFact.createCourseListRequest();
		this.conn.setRequest(courseReq);
		
		assertDoesNotThrow(() -> {
		this.conn.execute();
		});
		
		assertTrue(this.conn.getResponse() instanceof LocalCourseListResponse);
		ArrayList<CourseData> courses = ((LocalCourseListResponse) this.conn.getResponse()).getCourseList();
		
		assertTrue(courses.size() == 0);*/
	}
	

	@Test
	@DisplayName("Try to remove a course that doesn’t exist")
	public void removeCourseDoesntExist()
	{
		authRight();
		CourseData course1 = this.dataFact.createCourseData(3, "Course 3", "Versailles", "2004-12-12");
		RemoveCourseRequest rmReq = this.reqFact.createRemoveCourseRequest(course1);
		this.conn.setRequest(rmReq);
		Exception ex = assertThrows(ConnectionErrorException.class, () -> {
			this.conn.execute();
		});
		
		assertTrue(ex.getMessage().contains("La course n’existe pas"));
	}
	
	@Test
	@DisplayName("Try to add a course")
	public void addNewCourse()
	{
		authRight();
		CourseData course1 = this.dataFact.createCourseData("Course 4", "Versailles", "1998-09-30");
		ArrayList<OrganisateurData> orgs = new ArrayList<>();
		orgs.add(this.conn.getAuthentifiedOrganisateur());
		AddCourseRequest reqAdd = this.reqFact.createAddCourseRequest(course1, orgs);
		this.conn.setRequest(reqAdd);
		
		assertDoesNotThrow(() -> 
		{
			this.conn.execute();
		});
	}
	
	@Test
	@DisplayName("Try to add a course without authentification")
	public void addNewCourseNoAuth()
	{
		CourseData course1 = this.dataFact.createCourseData("Course 5","Versailles","1998-09-30");
		ArrayList<OrganisateurData> orgs = new ArrayList<>();
		orgs.add(this.dataFact.createOrganisateurData(0, "test@junit.org"));
		AddCourseRequest reqAdd = this.reqFact.createAddCourseRequest(course1, orgs);
		this.conn.setRequest(reqAdd);
		
		Exception ex = assertThrows(UnauthorizedAccessException.class, () -> 
		{
			this.conn.execute();
		});
		
		assertTrue(ex.getMessage().contains("You need to be authentified to add a course"));
	}
	
	@Test
	@DisplayName("Try to upload photos")
	public void uploadPhotos() throws IOException
	{
				authRight();
                byte[] bytes = Files.readAllBytes(new File("/tmp/junit-photos/photo.jpg").toPath());
                CourseData course1 = this.dataFact.createCourseData(1, "Course 1", "Paris", "2000-01-01");
                PhotoData photo = this.dataFact.createPhotoData("1998-09-30", 2.1f, 1.2f, bytes);
                ArrayList<PhotoData> photos = new ArrayList<>();
                photos.add(photo);
                PhotoUploadRequest reqUpload = this.reqFact.createPhotoUploadRequest(photos, course1);
                this.conn.setRequest(reqUpload);
                
                assertDoesNotThrow(() -> {
                	this.conn.execute();
                });
	}
	
	@Test
	@DisplayName("Try to upload photos without auth")
	public void uploadPhotosNoAuth() throws IOException
	{
                byte[] bytes = Files.readAllBytes(new File("/tmp/junit-photos/photo.jpg").toPath());
                CourseData course1 = this.dataFact.createCourseData(4, "Course 4", "Versailles", "1998-09-30");
                PhotoData photo = this.dataFact.createPhotoData("1998-09-30", 2.1f, 1.2f, bytes);
                ArrayList<PhotoData> photos = new ArrayList<>();
                photos.add(photo);
                PhotoUploadRequest reqUpload = this.reqFact.createPhotoUploadRequest(photos, course1);
                this.conn.setRequest(reqUpload);
                
                assertThrows(UnauthorizedAccessException.class, () -> {
                	this.conn.execute();
                });
	}
	
	@AfterEach
	public void endOfTests() throws ConnectionErrorException
	{
		this.conn.disconnect();
	}
}
