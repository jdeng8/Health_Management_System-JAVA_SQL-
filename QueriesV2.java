
import java.sql.*;
import java.util.*;
import java.text.*;


public class QueriesV2 {

	static final boolean USE_MY_ORCL = false;
	static final boolean USE_LOCAL_ORCL = false;
	
	static final String jdbcURL 
	= "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";
	//= "jdbc:oracle:thin:@localhost:1521:XE";
	
	String user = null;
	String passwd = null;
	/*
	 * @input: key: UserId, Pw
	 * @status: 1: successfully login  
	 			0: fail to login  
	 			-1: exception
	 * @message: 1: Successfully log in  
	 			 0: Error: userid or password is not valid!  
	 			 -1: Exception message
	 * @schema
	 * @data
	 */
	public Query login (Map<String, String> input) {
		Query result = new Query();
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
		
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);

				String sql = "SELECT * FROM Users WHERE UserID = ? AND Pw = GET_MD5(?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				pstmt.setString(2, input.get("Password"));

				rs = pstmt.executeQuery();
				if(!rs.next()) {
					result.message = "Error: userid or password is not valid!";  //no such user!
					result.status = 0;
				} else {
					result.message = "Successfully log in";  //okay
					result.status = 1;
				}
				
			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
	        
		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		} 
		return result;
	}

	/*
	 * @input: key: UserId, Pw
	 * @status: 1: successfully sign up  
	 			-1: exception
	 * @message: 1: Successfully sign up  
	 			 -1: Exception message
	 * @schema
	 * @data
	 */
	public Query signup (Map<String, String> input) {
		Query result = new Query();
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);

				String sql = "INSERT INTO Users VALUES (?, GET_MD5(?))";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				pstmt.setString(2, input.get("Password"));

				pstmt.executeUpdate();

				result.status = 1;
				result.message = "Successfully sign up.";
				
			}  finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
	        
		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
		return result;
	}

	/*
	 * @input: key: UserID
	 * @status: 0: no such patient, create profile  
	 			1: well patient  
	            2: sick patient with supporter  
	            3: sick patient without support, add supporter!!
	            -1: exception
	 * @message: 0: Error: patient account does not exist!
	 			 1: Successfully login as well patient.
	 			 2: Successfully login as sick patient.
	 			 3: Sick patient with no support! Please add your supporters.
	 			 -1: Exception message
	 * @schema
	 * @data
	 */
	public Query choosePatient (Map<String, String> input) {
		Query result = new Query();
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);
				sql = "SELECT SickOrNot FROM Patient WHERE UserID = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));

				rs = pstmt.executeQuery();
				if(!rs.next()) {
					result.message = "Error: patient account does not exist!";  //no such patient!
					result.status = 0;
				} else {
					int sickOrNot = rs.getInt("SickOrNot");  //okay
					if(sickOrNot == 0) {
						result.message = "Successfully login as well patient.";  //well patient
						result.status = 1;
					} else {
						//sick patient, check supporter
						sql = "SELECT * FROM Assists WHERE P_UserID = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.clearParameters();
						pstmt.setString(1, input.get("UserID"));
						rs = pstmt.executeQuery();
						if(rs.next()) {
							result.message = "Successfully login as sick patient.";  //sick patient
							result.status = 2;							
						} else {
							result.message = "Sick patient with no support! Please add your supporters.";  //sick patient
							result.status = 3;	
						}
					}
				}
				
			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
			

		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
		return result;
	        
	}

	/*
	 * @input: key: UserID
	 * @status: 0: no such supporter, create profile  
	            1: supporter
	            -1: exception
	 * @message: 0: Error: supporter account does not exist!
	 			 1: Successfully login as supporter.
	 			 -1: Exception message
	 * @schema
	 * @data
	 */
	public Query chooseSupporter (Map<String, String> input) {
		Query result = new Query();
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);
				
				sql = "SELECT * FROM Supporter WHERE UserID = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));

				rs = pstmt.executeQuery();
				if(!rs.next()) {
					result.message = "Error: supporter account does not exist!";  //no such supporter!
					result.status = 0;
				} else {
					result.message = "Successfully login as supporter.";  //supporter
					result.status = 1;
				}
			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
			

		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
		return result;
	        
	}

	/*
	 * @input: key: UserId, identity
	 * @status: 1: successfully retrieved patient's profile
	 			2: successfully retrieved supporter's profile  
	 			-1: exception
	 * @message: 1: Successfully retrieved patient's profile. 
	 			 2: Successfully retrieved supporter's profile.
	 			 -1: Exception message
	 * @schema: Patient or Supporter table
	 * @data: Patient or Supporter data
	 */
	public Query getProfile (Map<String, String> input) {
		Query result = new Query();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;
			String sql = null;
			String identity = input.get("identity");
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);
				if(identity.equals("SickPatient") || identity.equals("WellPatient")) {
					//This is a patient
					sql = "SELECT * FROM Patient WHERE UserID = ?"; 
					pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, input.get("UserID"));

					rs = pstmt.executeQuery();
					rsmd = rs.getMetaData();
					saveResult(rs, rsmd, result);
					result.status = 1;
					result.message = "Successfully retrieved patient's profile.";
					
					return result;

				} else if(identity.equals("Supporter")) {
					//This is a supporter
					sql = "SELECT * FROM Supporter WHERE UserID = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, input.get("UserID"));

					rs = pstmt.executeQuery();
					rsmd = rs.getMetaData();
					saveResult(rs, rsmd, result);
					result.status = 2;
					result.message = "Successfully retrieved supporter's profile.";
					
					return result;

				}
				

				//If there is no such user, return empty map

			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	            // close(rsmd);
	        }
	    	

		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		} 
		return result;
	}

	/*
	 * @input: key: all fields in Patient table, identity
	 * @status: 1: successfully saved patient's profile  
	 			2: successfully saved supporter's profile
	 			-1: exception
	 * @message: 1: Successfully saved patient's profile. 
	 			 2: Successfully saved supporter's profile.
	 			 -1: Exception message
	 * @schema
	 * @data
	 */
	public Query saveProfile (Map<String, String> input) {
		Query result = new Query();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;

		
			String sql = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String identity = input.get("identity");
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);

				if(identity.equals("SickPatient") || identity.equals("WellPatient")) {
					String userid = input.get("UserID");
					String name = input.get("Name");
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					java.util.Date date = sdf.parse(input.get("D.O.B.(mm/dd/yyyy)"));
					java.sql.Date dob = new java.sql.Date(date.getTime());
					int gender = Integer.parseInt(input.get("Gender"));
					String address = input.get("Address");
					int sickornot = 0;
					//TRANSACTION
					sql = "SELECT SickOrNot FROM Patient WHERE UserID = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, userid);
					rs = pstmt.executeQuery();

					if(rs.next()) {
						sickornot = rs.getInt("SickOrNot");
					} else {
						sql = "INSERT INTO Patient VALUES (?, 'null', TO_DATE('01/01/2016', 'MM/dd/yyyy'), 1, 'a', 0)";
						pstmt = conn.prepareStatement(sql);
						pstmt.clearParameters();
						pstmt.setString(1, userid);
						pstmt.executeUpdate();
					}
					
					sql = "UPDATE Patient SET Name = ?, DOB = ?, Gender = ?, Address = ?, SickOrNot = ? WHERE UserID = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, name);
					pstmt.setDate(2, dob);
					pstmt.setInt(3, gender);
					pstmt.setString(4, address);
					pstmt.setInt(5, sickornot);
					pstmt.setString(6, userid);

					pstmt.executeUpdate();

					result.status = 1;
					result.message = "Successfully saved patient's profile.";

					return result;
				} else if(identity.equals("Supporter")) {
					String userid = input.get("UserID");
					String name = input.get("Name");
					String mobile = input.get("Mobile");
					//TRANSACTION
					sql = "BEGIN " +
							"savepoint saveBeforeDelete; " +

							"begin " +
								"DELETE FROM Supporter WHERE UserID = ?; " +

								"INSERT INTO Supporter VALUES (?, ?, ?); " +

								"EXCEPTION " +
								"when others " +
								"then ROLLBACK to saveBeforeDelete; " +
								"raise_application_error(-20001,'Supporter edit Failure, please check your input.'); "+

							"end; " +
						"COMMIT; "+
						"END; ";

					pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, userid);
					pstmt.setString(2, userid);
					pstmt.setString(3, name);
					pstmt.setString(4, mobile);
					pstmt.executeUpdate();

					result.status = 2;
					result.message = "Successfully saved supporter's profile.";

					return result;
				}
				

				
			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
	        

		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
		return result;
	}

	/*
	 * @input: key: UserId, (identity)
	 * @status: 1: successfully retrieved patient's diagnosis
	 			-1: exception
	 * @message: 1: Successfully retrieved patient's diagnosis. 
	 			 -1: Exception message
	 * @schema: Did
	 * @data: Dname
	 */
	public Query getDiagnosis (Map<String, String> input) {
		Query result = new Query();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;
			String sql = null;
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);

				sql = "SELECT D.Did, D.Dname FROM Disease_DRecomm D, PatientDisease P WHERE P.UserID = ? AND P.Did = D.Did";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				rs = pstmt.executeQuery();
				rsmd = rs.getMetaData();
				saveResult(rs, rsmd, result);
				result.status = 1;
				result.message = "Successfully retrieved patient's diagnosis.";
				return result;
				

			} finally {
	            close(rs);
	            // close(rsmd);
	            close(pstmt);
	            close(conn);
	        }
	        

		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
		// return result;
	}

	/*
	 * @input: key: UserId, Did, Since, (identity)
	 * @status: 1: successfully saved patient's diagnosis
	 			-1: exception
	 * @message: 1: Successfully saved patient's diagnosis. 
	 			 -1: Exception message
	 * @schema
	 * @data
	 */
	public Query addDiagnosis (Map<String, String> input) {
		Query result = new Query();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;

			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);
				
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date date = sdf.parse(input.get("Diagnose Time"));
				java.sql.Date since = new java.sql.Date(date.getTime());

				sql = "INSERT INTO PatientDisease VALUES (?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				pstmt.setString(2, input.get("Disease ID"));
				pstmt.setDate(3, since);
				pstmt.executeUpdate();

				result.status = 1;
				result.message = "Successfully saved patient's diagnosis.";
				return result;

			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }

		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
		// return result;
	}

	/*
	 * @input: key: UserId, (identity), Did
	 * @status: 1: successfully deleted patient's diagnosis
	 			-1: exception
	 * @message: 1: Successfully deleted patient's diagnosis. 
	 			 -1: Exception message
	 * @schema
	 * @data
	 */
	public Query deleteDiagnosis (Map<String, String> input) {
		Query result = new Query();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;

			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);

				sql = "DELETE FROM PatientDisease WHERE UserID = ? AND Did = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				pstmt.setString(2, input.get("Disease ID"));
				pstmt.executeUpdate();

				result.status = 1;
				result.message = "Successfully deleted patient's diagnosis.";
				return result;
			
			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }

		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
		// return result;
	}

	/*
	 * @input: key: UserId, identity
	 * @status: 1: successfully retrieved patient's doctor recommendation
	 			2: successfully retrieved well patient's general recommendation
	 			3: successfully retrieved patient's disease recommendation
	 			-1: exception
	 * @message: 1: successfully retrieved patient's doctor recommendation
	 			2: successfully retrieved well patient's general recommendation
	 			3: successfully retrieved patient's disease recommendation
	 			 -1: Exception message
	 * @schema: recommendation table
	 * @data: recommendation data
	 */
	public Query getRecomm (Map<String, String> input) {
		Query result = new Query();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;

			PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;
			// ResultSet rs2 = null;
			String sql = null;
			String identity = input.get("identity");
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);

				
				sql = "SELECT " + 
					"Weight_bool, " + 
					"Wfreq, " + 
					"Wuplimit_lb, " + 
					"Wlowlimit_lb, " + 
					"BP_bool, " + 
					"BPfreq, " + 
					"BPSuplimit_mmHg, " + 
					"BPSlowlimit_mmHg, " + 
					"BPDuplimit_mmHg, " + 
					"BPDlowlimit_mmHg, " + 
					"Pain_bool, " +
					"Pfreq, " + 
					"Pvalue, " + 
					"Mood_bool, " + 
					"Mfreq, " + 
					"Mvalue, " + 
					"Oxy_bool, " + 
					"Ofreq, " + 
					"Ouplimit_pct, " + 
					"Olowlimit_pct, " + 
					"Temp_bool, " + 
					"Tfreq, " + 
					"Tuplimit_F, " + 
					"Tlowlimit_F " + 
					"FROM Recommendation R "+
					"WHERE R.Rid = (SELECT DR.Rid "+
					"FROM DoctorRecomm DR " + 
					"WHERE DR.UserID = ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				rs = pstmt.executeQuery();

				rsmd = rs.getMetaData();
				saveResult(rs, rsmd, result);
				//There is a doctor's recommendation
				if(result.results.size() > 0) {
					result.status = 1;
					result.message = "Successfully retrieved patient's doctor recommendation.";
					return result;
				}

				if(identity.equals("Supporter")) {
					sql = "SELECT SickOrNot FROM Patient WHERE UserID = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, input.get("UserID"));
					rs = pstmt.executeQuery();
					if(rs.next()) {
						int sickornot = rs.getInt("SickOrNot");
						if(sickornot == 0) {
							identity = "WellPatient";
						} else {
							identity = "SickPatient";
						}
					}
				}
				if(identity.equals("WellPatient")) {
					sql = "SELECT "+
						"Weight_bool, " + 
						"Wfreq, " +
						"Wuplimit_lb, "+
						"Wlowlimit_lb, " + 
						"BP_bool, " + 
						"BPfreq, " + 
						"BPSuplimit_mmHg, " + 
						"BPSlowlimit_mmHg, " + 
						"BPDuplimit_mmHg, " + 
						"BPDlowlimit_mmHg, " + 
						"Pain_bool, " +
						"Pfreq, " + 
						"Pvalue, " + 
						"Mood_bool, " + 
						"Mfreq, " + 
						"Mvalue, " + 
						"Oxy_bool, " + 
						"Ofreq, " + 
						"Ouplimit_pct, " + 
						"Olowlimit_pct, " + 
						"Temp_bool, " + 
						"Tfreq, " + 
						"Tuplimit_F, " + 
						"Tlowlimit_F " + 
						"FROM Recommendation " + 
						"WHERE Rid = 0 ";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					rsmd = rs.getMetaData();
					saveResult(rs, rsmd, result);

					
					result.status = 2;
					result.message = "Successfully retrieved well patient's general recommendation.";
					return result;
					
				}

				if(identity.equals("SickPatient")) {
					sql = "SELECT " + 
						"MAX(Weight_bool) AS WBOOL, " +				//0
						"MIN(Wfreq) AS WMINF, " +
						"MIN(Wuplimit_lb) AS WMINUP, "+
						"MAX(Wlowlimit_lb) AS WMAXLOW, "+
						
						"MAX(BP_bool) AS BPBOOL, " +				//4
						"MIN(BPfreq) AS BPMINF, " +
						"MIN(BPSuplimit_mmHg) AS BPSMINUP, "+
						"MAX(BPSlowlimit_mmHg) AS BPSMAXLOW, "+
						"MIN(BPDuplimit_mmHg) AS BPDMINUP, "+
						"MAX(BPDlowlimit_mmHg) AS BPDMAXLOW, "+
						
						"MAX(Pain_bool) AS PBOOL, " +				//10
						"MIN(Pfreq) AS PMINF, " +
						"MIN(Pvalue) AS PMINUP, "+
						
						"MAX(Mood_bool) AS MBOOL, " +				//13
						"MIN(Mfreq) AS MMINF, " +
						"MIN(Mvalue) AS MMINUP, "+
						
						"MAX(Oxy_bool) AS OBOOL, " +				//16
						"MIN(Ofreq) AS OMINF, " +
						"MIN(Ouplimit_pct) AS OMINUP, "+
						"MAX(Olowlimit_pct) AS OMAXLOW, "+
						
						"MAX(Temp_bool) AS TBOOL, " +				//20
						"MIN(Tfreq) AS TMINF, " +
						"MIN(Tuplimit_F) AS TMINUP, "+
						"MAX(Tlowlimit_F) AS TMAXLOW "+

						"FROM Recommendation R " + 
						"WHERE R.Rid IN " +
							"(SELECT DDR.Rid " +
							"FROM Disease_DRecomm DDR "+
							"WHERE DDR.Did IN " + 
								"(SELECT PD.Did " +
								"FROM PatientDisease PD "+
								"WHERE PD.UserID= ?))";

					pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, input.get("UserID"));
					rs = pstmt.executeQuery();
					
					rsmd = rs.getMetaData();
					result.schema.clear();
					result.results.clear();
					saveResult(rs, rsmd, result);
					
					result.status = 3;
					result.message = "Successfully retrieved patient's disease recommendation.";
					return result;
					
				}
				
				

			} finally {
	            close(rs);
	            // close(rs2);
	            close(pstmt);
	            close(stmt);
	            close(conn);
	        }
	        

		} catch(Throwable oop) {
		oop.printStackTrace();
		}
		return result;
    }

    /*
	 * @input: key: all fields in recommendation table, UserID
	 * @status: 1: successfully saved patient's doctor recommendation.
	 			-1: exception
	 * @message: 1: Successfully saved patient's doctor recommendation.
	 			 -1: Exception message
	 * @schema
	 * @data
	 */
	public Query saveRecomm (Map<String, String> input) {
    	Query result = new Query();
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
	    	PreparedStatement pstmt = null;
	    	Statement stmt = null;

			ResultSet rs = null;
			String sql = null;

			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);
				
				//get the maximum rid
				stmt = conn.createStatement();
				sql = "SELECT MAX(Rid) AS Max FROM Recommendation";
				rs = stmt.executeQuery(sql);
				int rid = 0;
				if(rs.next()) {
					rid = rs.getInt("Max") + 1;
				}

				String values = "" + rid;
				String columns = "Rid";
				for(String key : input.keySet()) {
					if(key.equals("Rid") || key.equals("UserID") || key.equals("identity")) {
						continue;
					}
					columns += ", " + key;
					if(input.get(key).equals("null")) {
						values += ", null";
					} else {
						values += ", " + input.get(key);
					}
				}
				// System.out.println("columns " + columns);
				// System.out.println("values " + values);

				sql = "BEGIN " +
						"savepoint saveBeforeDelete; " +
														
							"begin " +
							"DELETE FROM Recommendation R " + 
							"WHERE R.Rid IN " + 
							"(SELECT D.Rid FROM DoctorRecomm D WHERE D.UserID = ?);" +

							"DELETE FROM DoctorRecomm " +
							"WHERE UserID = ?;" +

							"INSERT INTO Recommendation (" +
							columns + 
							") " +
							"VALUES (" +
							values +
							");" +

							"INSERT INTO DoctorRecomm VALUES (?, ?);" +
							"exception "+
					        "when others "+
					        "then ROLLBACK to saveBeforeDelete; "+
					        "raise_application_error(-20001,'Supporter edit Failure, please check your input.'); "+

					        "end; " +
					    "COMMIT; " +
					    "END; ";

				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				pstmt.setString(2, input.get("UserID"));
				pstmt.setString(3, input.get("UserID"));
				pstmt.setInt(4, rid);
				pstmt.executeUpdate();

				result.status = 1;
				result.message = "Successfully saved patient's doctor recommendation.";
				return result;

			} finally {
	            close(rs);
	            close(pstmt);
	            close(stmt);
	            close(conn);
	        }
        	

		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
		
    	// return result;
    }

    /*
	 * @input: key: UserID, identity
	 * @status: 
	 * @message: 
	 * @schema: recommended columns
	 * @data
	 */
    public Query getRecommendedColumns (Map<String, String> input) {
    	Query result = new Query();
    	Query recomm = getRecomm(input);
    	for(int i = 0; i < recomm.schema.size(); i++) {
    		if (recomm.results.get(0).get(i) != null && recomm.results.get(0).get(i).equals("1")) {
				switch (i) {
					case 0:	result.schema.add("Weight_lb");
							break;
					case 4: result.schema.add("BPS_mmHg");
							result.schema.add("BPD_mmHg");
							break;
					case 10:result.schema.add("Pain_level");
							break;
					case 13:result.schema.add("Mood_level");
							break;
					case 16:result.schema.add("Oxy_pct"); 
							break;
					case 20:result.schema.add("Temperature_F"); 
							break;
				}
			}
		}
		return result;
    }

    /*
	 * @input: key: UserID
	 * @status: 1: Successfully retrieved patient's supporters.
	 			-1: exception
	 * @message: 1: Successfully retrieved patient's supporters."
	 			 -1: Exception message
	 * @schema: priority, supporter name
	 * @data
	 */
    public Query getSupporters (Map<String, String> input) {
    	Query result = new Query();
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
	    	PreparedStatement pstmt = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;
			String sql = null;
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);

				sql = "SELECT A.Priority, S.UserID, S.Name FROM Assists A, Supporter S WHERE A.P_UserID = ? AND A.S_UserID = S.UserID";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				rs = pstmt.executeQuery();
				rsmd = rs.getMetaData();
				saveResult(rs, rsmd, result);
				result.status = 1;
				result.message = "Successfully retrieved patient's supporters.";
				return result;

			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
        	

		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
    	// return result;
    }

    /*
	 * @input: key: UserID, Primary Supporter UserID, Effective Date1(mm/dd/yyyy), Secondary Supporter UserID, Effective Date2(mm/dd/yyyy)
	 * @status: 1: invalid supporter id.
	 			2: Userid does not exist
	 			3: for sick patient, two supporters are both null
	 			4: Successfully added two supporters.
	 			5: Successfully added one supporters.
	 			6: Successfully deleted all supporters.
	 			-1: exception
	 * @message: 1: Error: invalid supporter id.
	 			 2: Error: Userid does not exist!
	 			 3: Error: At least one supporter should be added for sick patient!
	 			 4: Successfully added two supporters.
	 			 5: Successfully added one supporters.
	 			 6: Successfully deleted all supporters.
	 			 -1: Exception message
	 * @schema: 
	 * @data
	 */
    public Query saveSupporters (Map<String, String> input) {
    	Query result = new Query();
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
	    	PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = null;
			int sickornot = 0;
			String s1 = input.get("Primary Supporter UserID");
			String s2 = input.get("Secondary Supporter UserID");
			String d1 = input.get("Effective Date1(mm/dd/yyyy)");
			String d2 = input.get("Effective Date2(mm/dd/yyyy)");

			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);
				
				//check valid supporter id
				if(!s1.equals("null")) {
					sql = "SELECT * FROM Supporter WHERE UserID = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, s1);
					rs = pstmt.executeQuery();
					if(!rs.next()) {
						result.message = "Error: invalid supporter id.";
						result.status = 1;
						return result;
					}
				}
				if(!s2.equals("null")) {
					sql = "SELECT * FROM Supporter WHERE UserID = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, s2);
					rs = pstmt.executeQuery();
					if(!rs.next()) {
						result.message = "Error: invalid supporter id.";
						result.status = 1;
						return result;
					}
				}

				//check supporter requirements
				sql = "SELECT SickOrNot FROM Patient WHERE UserID = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				rs = pstmt.executeQuery();
				if(!rs.next()) {
					result.message = "Error: Userid does not exist!";
					result.status = 2;
					return result;
				}
				sickornot = rs.getInt("SickOrNot");
				// System.out.println(sickornot);
				if(sickornot > 0) {
					
					if(s1.equals("null") && s2.equals("null")) {
						result.message = "Error: At least one supporter should be added for sick patient!";
						result.status = 3;
						return result;
					}
				}

				System.out.println("s1 " + s1);
				System.out.println("s2 " + s2);
				System.out.println("d1 " + d1);
				System.out.println("d2 " + d2);

				if(!s1.equals("null") && !s2.equals("null")) {
					sql = "begin "+
						"savepoint saveBeforeDelete; "+
							
						"begin "+
						"DELETE FROM Assists " + 
						"WHERE P_UserID = ?; " +
						
						"INSERT INTO Assists " + 
						"VALUES (?, ?, TO_DATE(?, 'MM/dd/yyyy'), 1); " +
						
				        "INSERT INTO Assists " + 
						"VALUES (?, ?, TO_DATE(?, 'MM/dd/yyyy'), 2); " +
//												 "raise_application_error(-20001,'You now have two supporters.'); "+
//												 
				      "exception "+
				      "when others "+
				      " then ROLLBACK to saveBeforeDelete; "+
				      "raise_application_error(-20001,'Supporter edit Failure, please check your input.'); "+
//												
						"end; "+
				      "commit; "+
				      "end; ";

				    pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, input.get("UserID"));
					pstmt.setString(2, input.get("UserID"));
					pstmt.setString(3, s1);
					pstmt.setString(4, d1);
					pstmt.setString(5, input.get("UserID"));
					pstmt.setString(6, s2);
					pstmt.setString(7, d2);
					pstmt.executeUpdate();

					result.status = 4;
					result.message = "Successfully added two supporters.";
					return result;
				} else if(!s1.equals("null")) {
					sql = "begin "+
						"savepoint saveBeforeDelete; "+
							
						"begin "+
						"DELETE FROM Assists " + 
						"WHERE P_UserID = ?; " +
						
						"INSERT INTO Assists " + 
						"VALUES (?, ?, TO_DATE(?, 'MM/dd/yyyy'), 1); " +
						
				  //       "INSERT INTO Assists " + 
						// "VALUES (?, ?, TO_DATE(?, 'MM/dd/yyyy'), 2); " +
//												 "raise_application_error(-20001,'You now have two supporters.'); "+
//												 
				      "exception "+
				      "when others "+
				      " then ROLLBACK to saveBeforeDelete; "+
				      "raise_application_error(-20001,'Supporter edit Failure, please check your input.'); "+
//												
						"end; "+
				      "commit; "+
				      "end; ";

				    pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, input.get("UserID"));
					pstmt.setString(2, input.get("UserID"));
					pstmt.setString(3, s1);
					pstmt.setString(4, d1);
					// pstmt.setString(5, input.get("UserID"));
					// pstmt.setString(6, s2);
					// pstmt.setString(7, d2);
					pstmt.executeUpdate();

					result.status = 5;
					result.message = "Successfully added one supporters.";
					return result;
				} else if(!s2.equals("null")) {
					sql = "begin "+
						"savepoint saveBeforeDelete; "+
							
						"begin "+
						"DELETE FROM Assists " + 
						"WHERE P_UserID = ?; " +
						
						"INSERT INTO Assists " + 
						"VALUES (?, ?, TO_DATE(?, 'MM/dd/yyyy'), 1); " +
						
				  //       "INSERT INTO Assists " + 
						// "VALUES (?, ?, TO_DATE(?, 'MM/dd/yyyy'), 2); " +
//												 "raise_application_error(-20001,'You now have two supporters.'); "+
//												 
				      "exception "+
				      "when others "+
				      " then ROLLBACK to saveBeforeDelete; "+
				      "raise_application_error(-20001,'Supporter edit Failure, please check your input.'); "+
//												
						"end; "+
				      "commit; "+
				      "end; ";

				    pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, input.get("UserID"));
					pstmt.setString(2, input.get("UserID"));
					pstmt.setString(3, s2);
					pstmt.setString(4, d2);
					// pstmt.setString(5, input.get("UserID"));
					// pstmt.setString(6, s2);
					// pstmt.setString(7, d2);
					pstmt.executeUpdate();

					result.status = 5;
					result.message = "Successfully added one supporters.";
					return result;
				} else {
					sql = "begin "+
						"savepoint saveBeforeDelete; "+
							
						"begin "+
						"DELETE FROM Assists " + 
						"WHERE P_UserID = ?; " +
						
						
//												 
				      "exception "+
				      "when others "+
				      " then ROLLBACK to saveBeforeDelete; "+
				      "raise_application_error(-20001,'Supporter edit Failure, please check your input.'); "+
//												
						"end; "+
				      "commit; "+
				      "end; ";

				    pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setString(1, input.get("UserID"));
					pstmt.executeUpdate();

					result.status = 6;
					result.message = "Successfully delete all supporters.";
					return result;
				}
				



			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
	        
	        

		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
    	// return result;
    }

    /*
	 * @input: key: all fileds in Observation table
	 * @status: 1: Successfully saved observation.
	 			-1: exception
	 * @message: 1: Successfully saved observation.
	 			 -1: Exception message
	 * @schema: 
	 * @data
	 */
    public Query insertObservations (Map<String, String> input) {
    	Query result = new Query();
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
	    	PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);
				
				System.out.println(input.get("UserID"));

				//get observation values
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
				java.util.Date date = sdf.parse(input.get("ObserveTime"));
				Timestamp observetime = new java.sql.Timestamp(date.getTime());

				sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
				date = sdf.parse(input.get("RecTime"));
				Timestamp rectime = new java.sql.Timestamp(date.getTime());

				
				//save observation
				sql = "INSERT INTO Observation_ForPatient (UserID, ObserveTime, RecTime) VALUES (?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				pstmt.setTimestamp(2, observetime);
				pstmt.setTimestamp(3, rectime);
				pstmt.executeUpdate();
				for(String key : input.keySet()) {
					if(key.equals("UserID") || key.equals("ObserveTime") || key.equals("RecTime") || key.equals("identity")) {
						continue;
					}
					sql = "UPDATE Observation_ForPatient SET " + key + " = ? WHERE UserID = ? AND ObserveTime = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setInt(1, Integer.parseInt(input.get(key)));
					pstmt.setString(2, input.get("UserID"));
					pstmt.setTimestamp(3, observetime);
					pstmt.executeUpdate();
				}

				result.status = 1;
				result.message = "Successfully saved observation.";
				return result;
				

			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
	        
		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
        // return result;
    }

    /*
	 * @input: key: UserID
	 * @status: 1: Successfully retrieved observations.
	 			-1: exception
	 * @message: 1: Successfully retrieved observations.
	 			 -1: Exception message
	 * @schema: observation table
	 * @data
	 */
    public Query getObservations (Map<String, String> input) {
    	Query result = new Query();
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
	    	PreparedStatement pstmt = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;
			String sql = null;
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);
				
				sql = "SELECT * FROM Observation_ForPatient WHERE UserID = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				rs = pstmt.executeQuery();
				rsmd = rs.getMetaData();
				saveResult(rs, rsmd, result);
				result.status = 1;
				result.message = "Successfully retrieved observations.";
				return result;

			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
	        
		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
        // return result;
    }	

    /*
	 * @input: key: UserID
	 * @status: 1: Successfully retrieved supported patient id.
	 			-1: exception
	 * @message: 1: Successfully retrieved supported patient id.
	 			 -1: Exception message
	 * @schema: P_UserID
	 * @data
	 */
    public Query getSupportedPatients (Map<String, String> input) {
    	Query result = new Query();
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
	    	PreparedStatement pstmt = null;
	    	ResultSet rs = null;
	    	ResultSetMetaData rsmd = null;
	    	String sql = null;
	    	
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);
				
				//get patient ids
				sql = "SELECT A.P_UserID, P.Name FROM Assists A, Patient P WHERE A.S_UserID = ? AND A.P_UserID = P.UserID";
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				rs = pstmt.executeQuery();
				rsmd = rs.getMetaData();
				saveResult(rs, rsmd, result);
				result.status = 1;
				result.message = "Successfully retrieved supported patient.";
				return result;
				

			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
	        
		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
    	// return result;
    }

    /*
	 * @input: key: UserID, PatientID
	 * @status: 0: no authority
	 			1: Success
	 			-1: exception
	 * @message: 0: You don't have authority to access this patient's information.
	 			 1: Successfully access the patient's information.
	 			 -1: Exception message
	 * @schema: P_UserID
	 * @data
	 */
    public Query getSupportedPatientsInfo (Map<String, String> input) {
    	Query result = new Query();
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
	    	PreparedStatement pstmt = null;
	    	ResultSet rs = null;
	    	ResultSetMetaData rsmd = null;
	    	String sql = null;
	    	
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);
				
				

				sql = "SELECT * FROM Assists WHERE P_UserID = ? AND S_UserID = ?";

				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("PatientID"));
				pstmt.setString(2, input.get("UserID"));
				rs = pstmt.executeQuery();
				if(!rs.next()) {
					result.status = 0;
					result.message = "You don't have authority to access this patient's acount.";
					return result;
				}

				sql = "SELECT EXTRACT (DAY FROM " + 
						"(SELECT TO_TIMESTAMP" + 
						"(TO_CHAR(SYSDATE, 'mm/dd/yyyy hh24:mi'), " + 
						"'mm/dd/yyyy hh24:mi') " + 
						"FROM DUAL)" + 
						" - " + 
						"(SELECT Since FROM Assists WHERE  P_UserID = ? AND S_UserID = ?)) " + 
						"AS Diff FROM DUAL";

				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("PatientID"));
				pstmt.setString(2, input.get("UserID"));
				rs = pstmt.executeQuery();
				if(rs.next()) {
					int diff = rs.getInt("Diff");
					if(diff < 0) {
						result.status = 0;
						result.message = "You don't have authority to access this patient's information.";
						return result;
					} else {
						result.status = 1;
						result.message = "Successfully access the patient's information.";
						return result;
					}
				}


				} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
	        
		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
    	return result;
    }

    /*
	 * @input: key: UserID
	 * @status: 1: No observations for this patient.
	 			2: Successfully retrieved alerts
	 			-1: exception
	 * @message: 1: No observations for this patient.
	 			 2: Successfully retrieved alerts
	 			 -1: Exception message
	 * @schema: P_UserID
	 * @data
	 */
    public Query genAndShowAlert(Map<String, String> input) {
    	Query result = new Query();
    	Query alertRec = getRecomm(input);
    	Query freqObs = new Query();
    	Query alertObs = new Query();
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
	    	PreparedStatement pstmt = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;
			String sql = null;
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);

				//select last observation
				sql = "SELECT * " + 
					"FROM " + 
						"(SELECT " +
						"Weight_lb, " + 
						"BPS_mmHg, " + 
						"BPD_mmHg, " +
						"Pain_level, " + 
						"Mood_level, " + 
						"Oxy_pct, " +
						"Temperature_F " + 
						"FROM Observation_ForPatient " + 
						"WHERE UserID = ? " + 
						"ORDER BY ObserveTime DESC) " + 
					"WHERE ROWNUM = 1 ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				rs = pstmt.executeQuery();
				rsmd = rs.getMetaData();
				saveResult(rs, rsmd, alertObs);
				// return alertRec;

				sql = "SELECT EXTRACT (DAY FROM " + 
					"(SELECT TO_TIMESTAMP" + 
						"(TO_CHAR(SYSDATE, 'mm/dd/yyyy hh24:mi'), " + 
						"'mm/dd/yyyy hh24:mi') " + 
					"FROM DUAL)" + 
					" - " + 
					"(SELECT ObserveTime " + 
					"FROM " + 
						"(SELECT " +
						"ObserveTime, " + 
						"Weight_lb, " + 
						"BPS_mmHg, " + 
						"BPD_mmHg, " + 
						"Pain_level, " + 
						"Mood_level, " + 
						"Oxy_pct, " +
						"Temperature_F " + 
						"FROM Observation_ForPatient " + 
						"WHERE UserID = ? " + 
						"ORDER BY ObserveTime DESC) " + 
					"WHERE ROWNUM = 1 ))" + 
				"FROM DUAL"; 

				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				rs = pstmt.executeQuery();
				rsmd = rs.getMetaData();
				saveResult(rs, rsmd, freqObs);

				int[] index2 = new int[] {0, 4, 10, 13, 16, 20};
				// prepare for the insertion to observation table
				String[] freq = new String[7];
				String[] limitUp = new String[7];
				String[] limitLow = new String[7];

				if (alertObs.results.size() == 0) {
					result.status = 1;
					result.message = "No observations for this patient.";
					return result;
				}

				int k = 0;
				for (int i : index2) {
					if (alertRec.results.get(0).get(i).equals("1")) {
						switch (i) {
							case 0:	if (alertObs.results.get(0).get(0)==null){
										freq[k] = String.format("Low frequency alert! The observation of weight is missing. ");
									}else{
											int day_W = (Integer.parseInt(freqObs.results.get(0).get(0)) - 
													Integer.parseInt(alertRec.results.get(0).get(1)));
											int weightU = (Integer.parseInt(alertObs.results.get(0).get(0)) - 
													Integer.parseInt(alertRec.results.get(0).get(2)));
											int weightL = (Integer.parseInt(alertRec.results.get(0).get(3)) - 
													Integer.parseInt(alertObs.results.get(0).get(0)));
											if (day_W > 0) {freq[k] = String.format("Low frequency alert! The observation of weight has EXPIRED for %d days. ", day_W);}
											if (weightU > 0) {limitUp[k] = String.format("Out of Limit alert! The observation of weight OVER limit for %d lbs. ", weightU);}
											if (weightL > 0) {limitLow[k] = String.format("Out of Limit alert! The observation of weight UNDER limit for %d lbs. ", weightL);}
									}
									k++;
									break;
							case 4: if (alertObs.results.get(0).get(1)==null||alertObs.results.get(0).get(2)==null){
										freq[k] = String.format("Low frequency alert! The observation of BP is missing. ");
									}else{
										int day_B = (Integer.parseInt(freqObs.results.get(0).get(0)) - 
													Integer.parseInt(alertRec.results.get(0).get(5)));
											int bps_U = (Integer.parseInt(alertObs.results.get(0).get(1)) - 
													Integer.parseInt(alertRec.results.get(0).get(6)));
											int bps_L = (Integer.parseInt(alertRec.results.get(0).get(7)) - 
													Integer.parseInt(alertObs.results.get(0).get(1)));											
											if (day_B > 0) {freq[k] = String.format("Low frequency alert! The observation of BP has EXPIRED for %d days. ", day_B);}
											if (bps_U > 0) {limitUp[k] = String.format("Out of Limit alert! The observation of systolic OVER limit for %d mmHg. ", bps_U);}
											if (bps_L > 0) {limitLow[k] = String.format("Out of Limit alert! The observation of systolic BP UNDER limit for %d mmHg. ", bps_L);}
											k++;
											int bpd_U = (Integer.parseInt(alertObs.results.get(0).get(2)) - 
													Integer.parseInt(alertRec.results.get(0).get(8)));
											int bpd_L = (Integer.parseInt(alertRec.results.get(0).get(9)) - 
													Integer.parseInt(alertObs.results.get(0).get(2)));	
											if (bpd_U > 0) {limitUp[k] = String.format("Out of Limit alert! The observation of diastolic BP OVER limit for %d mmHg. ", bpd_U);}
											if (bpd_L > 0) {limitLow[k] = String.format("Out of Limit alert! The observation of diastolic BP UNDER limit for %d mmHg. ", bpd_L);}
									}
									k++; 
									break;
							case 10:if (alertObs.results.get(0).get(3)==null){
										freq[k] = String.format("Low frequency alert! The observation of pain level is missing. ");
									}else{
										int day_P = (Integer.parseInt(freqObs.results.get(0).get(0)) - 
													Integer.parseInt(alertRec.results.get(0).get(11)));
											int pain_U = (Integer.parseInt(alertObs.results.get(0).get(3)) - 
													Integer.parseInt(alertRec.results.get(0).get(12)));
											if (day_P > 0) {freq[k] = String.format("Low frequency alert! The observation of pain level has EXPIRED for %d days. ", day_P);}
											if (pain_U > 0) {limitUp[k] = String.format("Out of Limit alert! The observation of pain level OVER limit for %d. ", pain_U);}
									}
									k++; 
									break;
							case 13:if (alertObs.results.get(0).get(4)==null){
										freq[k] = String.format("Low frequency alert! The observation of mood level is missing. ");
									}else{
										int day_M = (Integer.parseInt(freqObs.results.get(0).get(0)) - 
													Integer.parseInt(alertRec.results.get(0).get(14)));
											int mood_L = (Integer.parseInt(alertRec.results.get(0).get(15)) - 
													Integer.parseInt(alertObs.results.get(0).get(4)));
											if (day_M > 0) {freq[k] = String.format("Low frequency alert! The observation of mood level has EXPIRED for %d days. ", day_M);}
											if (mood_L > 0) {limitLow[k] = String.format("Out of Limit alert! The observation of mood level UNDER limit for %d. ", mood_L);}
									}
									k++; 
									break;
							case 16:if (alertObs.results.get(0).get(5)==null){
										freq[k] = String.format("Low frequency alert! The observation of Oxygen Saturation is missing. ");
									}else{
										int day_S = (Integer.parseInt(freqObs.results.get(0).get(0)) - 
													Integer.parseInt(alertRec.results.get(0).get(17)));
											int oxs_U = (Integer.parseInt(alertObs.results.get(0).get(5)) - 
													Integer.parseInt(alertRec.results.get(0).get(18)));
											int oxs_L = (Integer.parseInt(alertRec.results.get(0).get(19)) - 
													Integer.parseInt(alertObs.results.get(0).get(5)));
											if (day_S > 0) {freq[k] = String.format("Low frequency alert! The observation of Oxygen Saturation has EXPIRED for %d days. ", day_S);}
											if (oxs_U > 0) {limitUp[k] = String.format("Out of Limit alert! The observation of Oxygen Saturation OVER limit for %d%%. ", oxs_U);}
											if (oxs_L > 0) {limitLow[k] = String.format("Out of Limit alert! The observation of Oxygen Saturation UNDER limit for %d%%. ", oxs_L);}
									}
									k++; 
									break;
							case 20:if (alertObs.results.get(0).get(6)==null){
										freq[k] = String.format("Low frequency alert! The observation of Temperature is missing. ");
									}else{
										int day_T = (Integer.parseInt(freqObs.results.get(0).get(0)) - 
													Integer.parseInt(alertRec.results.get(0).get(21)));
											int temp_U = (Integer.parseInt(alertObs.results.get(0).get(6)) - 
													Integer.parseInt(alertRec.results.get(0).get(22)));
											int temp_L = (Integer.parseInt(alertRec.results.get(0).get(23)) - 
													Integer.parseInt(alertObs.results.get(0).get(6)));
											if (day_T > 0) {freq[k] = String.format("Low frequency alert! The observation of Temperature has EXPIRED for %d days. ", day_T);}
											if (temp_U > 0) {limitUp[k] = String.format("Out of Limit alert! The observation of Temperature OVER limit for %d F. ", temp_U);}
											if (temp_L > 0) {limitLow[k] = String.format("Out of Limit alert! The observation of Temperature UNDER limit for %d F. ", temp_L);}
									}
									k++; 
									break;
						}
					}
				}

				//add alert
				for (int i = 0; i < 7; i++) {
					if (freq[i] != null) {
						sql = "begin "+
								
						    "INSERT INTO Alert VALUES (?, " +
							"(SELECT ObserveTime " + 
							"FROM " + 
								"(SELECT " +
								"ObserveTime " +											
								"FROM Observation_ForPatient " + 
								"WHERE UserID = ? " + 
								"ORDER BY ObserveTime DESC) " + 
							"WHERE ROWNUM = 1), "+ 
							"TO_TIMESTAMP(TO_CHAR(SYSDATE, 'MM/dd/yyyy hh24:mi'), " + 
							"'MM/dd/yyyy hh24:mi'), " + 
							
							"1, ?, 1)" +
						      "; "+
						      "commit; "+
						      "exception "+
						      "when DUP_VAL_ON_INDEX "+
						      " then ROLLBACK; "+
						      "end; ";

						pstmt = conn.prepareStatement(sql);
						pstmt.clearParameters();
						pstmt.setString(1, input.get("UserID"));
						pstmt.setString(2, input.get("UserID"));
						pstmt.setString(3, freq[i]);
						pstmt.executeUpdate();

					}

					if (limitUp[i] != null) {
						sql = "begin "+
							"INSERT INTO Alert VALUES (?, " +
							"(SELECT ObserveTime " + 
							"FROM " + 
								"(SELECT " +
								"ObserveTime " +											
								"FROM Observation_ForPatient " + 
								"WHERE UserID = ? " + 
								"ORDER BY ObserveTime DESC) " + 
							"WHERE ROWNUM = 1), "+ 
							"TO_TIMESTAMP(TO_CHAR(SYSDATE, 'MM/dd/yyyy hh24:mi'), " + 
							"'MM/dd/yyyy hh24:mi'), " + 
							
							"2, ?, 1)" +
							"; "+
						      "commit; "+
						      "exception "+
						      "when DUP_VAL_ON_INDEX "+
						      " then ROLLBACK; "+
						      "end; ";

						pstmt = conn.prepareStatement(sql);
						pstmt.clearParameters();
						pstmt.setString(1, input.get("UserID"));
						pstmt.setString(2, input.get("UserID"));
						pstmt.setString(3, limitUp[i]);
						pstmt.executeUpdate();
					}

					if (limitLow[i] != null) {
						sql = "begin "+
							"INSERT INTO Alert VALUES (?, " +
							"(SELECT ObserveTime " + 
							"FROM " + 
								"(SELECT " +
								"ObserveTime " +											
								"FROM Observation_ForPatient " + 
								"WHERE UserID = ? " + 
								"ORDER BY ObserveTime DESC) " + 
							"WHERE ROWNUM = 1), "+ 
							"TO_TIMESTAMP(TO_CHAR(SYSDATE, 'MM/dd/yyyy hh24:mi'), " + 
							"'MM/dd/yyyy hh24:mi'), " + 
							
							"2, ?, 1) "+
									"; "+
								      "commit; "+
								      "exception "+
								      "when DUP_VAL_ON_INDEX "+
								      " then ROLLBACK; "+
								      "end; ";

						pstmt = conn.prepareStatement(sql);
						pstmt.clearParameters();
						pstmt.setString(1, input.get("UserID"));
						pstmt.setString(2, input.get("UserID"));
						pstmt.setString(3, limitLow[i]);
						pstmt.executeUpdate();
					}
				}

				//show alert
				sql = "SELECT * " + 
					"FROM Alert " + 
					"WHERE UserID = ? " + 
					"AND Active = 1";

				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				rs = pstmt.executeQuery();
				rsmd = rs.getMetaData();
				saveResult(rs, rsmd, result);
				result.status = 2;
				result.message = "Successfully retrieved alerts.";
				return result;

								// return alertRec;


			} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
	        
		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
    	// return result;
    }

    /*
	 * @input: key: UserID, identity
	 * @status: 1: Successfully deactive alerts
	 			-1: exception
	 * @message: 1: Successfully deactive alerts
	 			 -1: Exception message
	 * @schema: 
	 * @data
	 */
    public Query ackAlert(Map<String, String> input) {
    	Query result = new Query();
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (USE_LOCAL_ORCL)	{
		   		 user = "schen35";	// For example, "jsmith"
		   		 passwd = "000791";
		    	}else{
			    	if (USE_MY_ORCL){	
			    		 user = "schen35";	// For example, "jsmith"
			    		 passwd = "200111596";	// Your 9 digit student ID number
			    	}else{
			    		 user = "mwang22";	// For example, "jsmith"
					     passwd = "200109693";	// Your 9 digit student ID number
			    	}
		    	}
		    Connection conn = null;
	    	PreparedStatement pstmt = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;
			String sql = null;
			
			try {
				conn = DriverManager.getConnection(jdbcURL, user, passwd);

				if(input.get("identity").equals("Supporter")) {
					sql = "begin "+
						"UPDATE Alert " + 
						"SET Active = 0 " + 
						"WHERE UserID = ?; "+
						"commit; "+
						"exception "+
						"when DUP_VAL_ON_INDEX "+
						" then ROLLBACK; "+
						"end; ";

				} else {
					sql = "begin "+
						"UPDATE  Alert " + 
						"SET Active = 0 " + 
						"WHERE UserID = ?" + 
						" AND AType = 2; "+
						"commit; "+
						"exception "+
						"when DUP_VAL_ON_INDEX "+
						" then ROLLBACK; "+
						"end; ";
				}
				pstmt = conn.prepareStatement(sql);
				pstmt.clearParameters();
				pstmt.setString(1, input.get("UserID"));
				pstmt.executeUpdate();
				result.status = 1;
				result.message = "Successfully deactive alerts";
				return result;

				} finally {
	            close(rs);
	            close(pstmt);
	            close(conn);
	        }
	        
		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			return result;
		}
    }

    
    private void saveResult(ResultSet rs, ResultSetMetaData rsmd, Query result) {
    	try {
			for (int i = 1; i <= rsmd.getColumnCount(); i++ ) {
				result.schema.add(i-1, rsmd.getColumnName(i));
			}
			for (int row = 0; rs.next(); row++) {
				ArrayList<String> record = new ArrayList<String>();
				for (int col = 0; col < result.schema.size(); col++) {
					record.add(col, rs.getString(result.schema.get(col)));
				}
				result.results.add(row, record);
			}
		} catch(Throwable oop) {
			result.status = -1;
			result.message = oop.getMessage();
			// return result;
		}
	}

	/****************************************************************************/

	

	static void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); } catch(Throwable whatever) {}
        }
    }

    static void close(Statement st) {
        if(st != null) {
            try { st.close(); } catch(Throwable whatever) {}
        }
    }

    static void close(ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } catch(Throwable whatever) {}
        }
    }

    // static void close(ResultSetMetaData rsmd) {
    //     if(rsmd != null) {
    //         try { rsmd.close(); } catch(Throwable whatever) {}
    //     }
    // }

}