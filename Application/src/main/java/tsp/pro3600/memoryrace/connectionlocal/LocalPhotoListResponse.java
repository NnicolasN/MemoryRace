package tsp.pro3600.memoryrace.connectionlocal;

import java.io.File;	
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tsp.pro3600.memoryrace.connectionmodel.PhotoData;
import tsp.pro3600.memoryrace.connectionmodel.PhotoListResponse;

/**
 * The is the implementation of PhotoListResponse to use with a local db
 * @version 0.1
 */
public class LocalPhotoListResponse extends PhotoListResponse {

	public LocalPhotoListResponse(ResultSet rs, String rootPath) throws SQLException, IOException
	{
		super();
		ArrayList<PhotoData> photos = new ArrayList<>();
		// Construction of array wit response rows
		while (rs.next()) {
			// loading the file and putting it in a byte[]
			File fi;
			fi = new File(rootPath + rs.getString("file_path"));
			byte[] fileContent = Files.readAllBytes(fi.toPath());
			// date formating
			Date date = rs.getDate("date");
    		String fmtDate = date.toString();
			photos.add(new LocalPhotoData(rs.getInt("id_photo"), fmtDate, rs.getFloat("latitude"), rs.getFloat("longitude"), fileContent));
		}
		this.photoList = photos;
	}
	
	@Override
	public void actionsAtReception() throws Exception {
		System.out.println("Received LocalPhotoListResponse…");
	}

	@Override
	public String getSerialized() {
		return "";
	}

}
