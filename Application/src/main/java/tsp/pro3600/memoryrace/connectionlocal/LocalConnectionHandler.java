package tsp.pro3600.memoryrace.connectionlocal;

import tsp.pro3600.memoryrace.connectionmodel.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;

/**
 * This class represents a connection with a local database
 * @version 0.1
 */

public class LocalConnectionHandler implements ConnectionHandler{

	/**
	 * The JDBC Driver to use
	 */
    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    
    /**
     * The python bin to use (with all modules installed)
     * The root of path is the rootPath parameter
     * @see rootPath
     */
    private static final String PYTHON_BIN = "venv/bin/python3";
    
    /**
     * The name of the script to start detection
     * The root of path is the rootPath parameter
     * @see rootPath
     */
    private static final String PYTHON_SCRIPT = "dossards_extraction.py";
    /**
     * Address of MariaDB instance
     */
    private String host;

    /**
     * Port of MariaDB instance
     */
    private int port;

    /**
     * User for connection to MariaDB
     */
    private String user;

    /**
     * Password for connection to MariaDB
     */
    private String password;

    /**
     * Connection to the MariaDB databse
     */
    private Connection conn;


    /**
     * Selected Request
     */
    private Request req;

    /**
     * Last Response
     */
    private Response res;
    
    /**
     * Root of file image directory
     */
    private String rootPath;
    
    /**
     * Authentified user to check for database modifications
     */
    //private AuthentificationData authentifiedUser = null;
    private OrganisateurData authentifiedOrg = null;
    /**
     * @param host Address of MariaDB instance
     * @param port Port of MairiaDB instance
     * @param user Username for MariaDB
     * @param password Password for MariaDB
     * @param rootPath Root folder of photos on the file system
     */
    public LocalConnectionHandler(String host, int port, String user, String password, String rootPath){
	this.host = host;
	this.port = port;
	this.user = user;
	this.password = password;
	this.rootPath = rootPath;
    }

    /**
     * Open a connection to the MariaDB database
     * @throws ConnectionErrorExeption if the connection fail
     */
    public void connect() throws ConnectionErrorException {
	this.conn = null;

	try {
	    Class.forName("org.mariadb.jdbc.Driver");
	    conn = DriverManager.getConnection("jdbc:mariadb://"+this.host+":"+this.port+"/db", this.user, this.password);
	}
	catch(Exception e){
	    e.printStackTrace();
	    throw new ConnectionErrorException("Impossible de se connecter à la base de données : " + e.getMessage());
	}
    }

    /**
     * Close the connection to the MariaDB database
     * @throws ConnectionErrorExeption if the disconnection fail
     */
    public void disconnect() throws ConnectionErrorException {
	try {
	    this.conn.close();
	}
	catch(Exception e){
	    e.printStackTrace();
	    throw new ConnectionErrorException(e.getMessage());
	}
    }

    // You understand this function
    public void setRequest(Request req)
    {
	this.req = req;
    }

    // You understands this function
    public Response getResponse() {
	return this.res;
    }

    /**
     * Create a DataFactory for use with local database connection
     * @return a DataFactory object to create Data objects
     */
    public LocalDataFactory createDataFactory()
    {
	return new LocalDataFactory();
    }
    
    /**		// this.authentifiedUser = ((LocalAuthentificationRequest)this.req).getUser();
     * Create a RequestFactory for use with local database connection
     * @return a RequestFactory object to create Request objects
     */
    public LocalRequestFactory createRequestFactory()
    {
	return new LocalRequestFactory();
    }

     /**
     * Create a ResponseFactory for use with local database connection
     * @return a ResponseFactory object to create Response objects
     */
	@Override
	public ResponseFactory createResponseFactory() {
		// WIP not implemented yet
		return null;
	}

    /**
     * Execute the request set by setRequest(Request req)
     * @throws UnauthorizedAccessException 
     */
    public void execute() throws ConnectionErrorException, AuthentificationFailedException, UnauthorizedAccessException
    {
    	Statement stmt; // statement to execute queries on the DB
	try {
	    stmt = conn.createStatement();
	    /* LocalCourseListRequest : get a list of all courses */
	    if (this.req instanceof LocalCourseListRequest)
	    {
	    	// MariaDB Query
	    	ResultSet rs = stmt.executeQuery(this.req.getSerialized());
	    	// Treatment is done in the LocalCourseListResponse class
	    	this.res = new LocalCourseListResponse(rs);
	    	
	    }
	    else if (this.req instanceof LocalPhotoWithDossardRequest)
	    {
	    	/*LocalPhotoWithDossardRequest : get photos with a certain dossard in a course*/
	    	
	    	// MariaDB Query
	    	ResultSet rs = stmt.executeQuery(this.req.getSerialized());
	    	// Treatment is done in the LocalPhotoListResponse class
	    	this.res = new LocalPhotoListResponse(rs, this.rootPath);
	    }
	    else if (this.req instanceof LocalAuthentificationRequest)
	    {
	    	ResultSet rs = stmt.executeQuery(this.req.getSerialized());
	    	// Treatment is done in the LocalAuthentificationResponse class
	    	this.res = new LocalAuthentificationResponse(rs);
	    	// Throws an exception if failed
	    	((LocalAuthentificationResponse) this.res).actionsAtReception();
	    	// If the request has been accepted, set the authentifiedOrg field to remember who is the user
	    	if (((LocalAuthentificationResponse) this.res).hasBeenAccepted())
	    	{
	    		// Get the id
	    		rs = stmt.executeQuery("SELECT id_organisateur FROM Organisateurs WHERE email=\"" +
	    				((LocalAuthentificationRequest)this.req).getUser().getEmail() +
	    				"\" AND password=\"" +
	    				((LocalAuthentificationRequest)this.req).getUser().getHashedPassword() +
	    				"\";");
	    		if(rs.next()) {
	    			this.authentifiedOrg = new LocalOrganisateurData(rs.getInt("id_organisateur"), 
	    					((LocalAuthentificationRequest)this.req).getUser().getEmail());
	    		}
	    	}
	    	
	    }
	    else if (this.req instanceof LocalAddCourseRequest)
	    {
	    	if (this.authentifiedOrg != null)
	    	{
	    	ResultSet rs = stmt.executeQuery(this.req.getSerialized());
	    	int id = -1;
	    	if (rs.next()) {
	    		id = rs.getInt("id_course");
	    	}
	    	// Add the organisateur to the Organise table :
	    	stmt.executeQuery(((LocalAddCourseRequest)this.req).getOrganisateurStatement(id));
	    	this.res = new LocalAddCourseResponse(id, ((LocalAddCourseRequest)this.req).getNewCourse());
	    	}
	    	else {
	    		throw new UnauthorizedAccessException("You need to be authentified to add a course");
	    	}
	    }
	    else if (this.req instanceof LocalPhotoUploadRequest)
	    {
	    	if (this.authentifiedOrg != null && userIsOrganisator(stmt, this.authentifiedOrg, ((LocalPhotoUploadRequest)this.req).getCourse()))
	    	{
			String stmtString = "INSERT INTO Photos (date, id_course, latitude, longitude, file_path) VALUES ";
	    	int i = 0; // counter used to give different names to photos of the same upload
	    	Instant currentTimestamp = Instant.now(); // used to give different names to different uploads.
	    	for(PhotoData photo : ((LocalPhotoUploadRequest)this.req).getPhotos())
	    	{
	    		System.out.println(this.rootPath + currentTimestamp + "-" + i + ".jpg");
	    		try (FileOutputStream fos = new FileOutputStream(this.rootPath + currentTimestamp + "-" + i + ".jpg")) {
	    			
	    			   fos.write(photo.getImageBytes()); // write photos to the FS
	    			}
	    		// Generate the statement
				stmtString += "(\"" + 
						photo.getDate() + "\", " +
						((LocalPhotoUploadRequest)this.req).getCourse().getId() + ", " +
						photo.getLatitude()  +", " +
						photo.getLongitude() + ", \"" +
						currentTimestamp + "-" + i + ".jpg\"" + 
						")";
				if (i < ((LocalPhotoUploadRequest)this.req).getPhotos().size() - 1)
				{
					stmtString += ",";
				}
	    		i++;
	    	}
	    	ResultSet rs = stmt.executeQuery(stmtString + "RETURNING id_photo;");
	    	// Prepare the python subprocess
	    	ArrayList<String> argsPython = new ArrayList<>();
	    	argsPython.add(this.rootPath + PYTHON_BIN);
	    	argsPython.add(this.rootPath + PYTHON_SCRIPT);
	    	argsPython.add(this.rootPath);
	    	while(rs.next()) {
	    		argsPython.add("" + rs.getInt("id_photo"));
	    	}
	    	ProcessBuilder python = new ProcessBuilder(argsPython);
	    	// Execute the python subprocess
	    	Process detectionProcess = python.start();
	    	BufferedReader stdInput = new BufferedReader(new InputStreamReader(detectionProcess.getInputStream()));
	    	
	    // Print the subprocess messages in the console
        String lastLinePython = null;
        String currentLinePython = null;
        while ((currentLinePython = stdInput.readLine()) != null) {
        	lastLinePython = currentLinePython;
        	System.out.println(currentLinePython);
        }
	    	detectionProcess.waitFor(); // wait the python subprocess
	    	if(detectionProcess.exitValue() != 0) // in case of python error
	    	{
	    		throw new ConnectionErrorException("Issue while analysing the files : " + lastLinePython);
	    	}
	    	}
	    	else
	    	{
	    		throw new UnauthorizedAccessException("You need to be an organisateur of this course to upload photos");
	    	}
	    }
	    else if (this.req instanceof LocalModifCourseRequest)
	    {
	    	if ((this.authentifiedOrg != null && userIsOrganisator(stmt, this.authentifiedOrg, ((LocalModifCourseRequest)this.req).getOldCourse())))
	    	{
	    	stmt.executeUpdate(this.req.getSerialized());
	    	}
	    	else {
	    		throw new UnauthorizedAccessException("You need to be an organisator to motify a course");
	    	}
	    }
	    else if (this.req instanceof LocalRemoveCourseRequest)
	    {
	    	if ((this.authentifiedOrg != null && userIsOrganisator(stmt, this.authentifiedOrg, ((LocalRemoveCourseRequest)this.req).getCourse())))
	    	{
	    	stmt.executeUpdate(this.req.getSerialized());
	    	}
	    	else {
	    		throw new UnauthorizedAccessException("You need to be an organisator to remove a course");
	    	}
	    	
	    }
	    stmt.close();
	    
	    
    } catch (SQLException e) {
    	/*Replacing implementation specific exception by a more general one*/
		throw new ConnectionErrorException("Issue while accessing the database : "+ e.getMessage());

    } catch (IOException e)
	{
    	/*Replacing implementation specific exception by a more general one*/
    	throw new ConnectionErrorException("Issue while reading the files : "+ e.getMessage());
	} catch (InterruptedException e) {
		throw new ConnectionErrorException("Python process was interrupted : "+ e.getMessage());
	}
   }
    /**
     * Test if a user is organisateur of course.
     * @param stmt a SQL statement
     * @param user the user to test
     * @param course the course to test
     * @return true if user is organisateur of course
     * @throws SQLException
     */
    private static boolean userIsOrganisator(Statement stmt, OrganisateurData user, CourseData course) throws SQLException
    {
    	ResultSet rs = stmt.executeQuery("SELECT * FROM Organise WHERE id_course=" +
    									course.getId() + " AND id_organisateur=" + user.getId() + ";");
    	return rs.next();
    	
    }
    
    public OrganisateurData getAuthentifiedOrganisateur()
    {
    	return this.authentifiedOrg;
    }

    
}

