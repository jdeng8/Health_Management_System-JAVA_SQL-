
import java.sql.*;
import java.util.*;

public class InsertValues {

	// DEV_MODE flag: use both the test data and standard data = true
	// use standard sample data = false
	public static final boolean DEV_MODE = false;
	public static final boolean USE_MY_ORCL = false;

	static final boolean USE_LOCAL_ORCL = false;
	
	static final String jdbcURLLOC 
	= "jdbc:oracle:thin:@localhost:1521:XE";
	static final String jdbcURL 
	= "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";
	public static void main(String[] args) {

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

    		String user = null;
    		String passwd = null;
    		
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
			Statement stmt = null;
			ResultSet rs = null;
			Set<String> set = new HashSet<String>();

			try {
				// Get a connection from the first driver in the
				// DriverManager list that recognizes the URL jdbcURL

				if (USE_LOCAL_ORCL)	{
    				conn = DriverManager.getConnection(jdbcURLLOC, user, passwd);
    			}else{
				conn = DriverManager.getConnection(jdbcURL, user, passwd);
    			}
				// Create a statement object that will be sending your
				// SQL statements to the DBMS

				stmt = conn.createStatement();

				// Get all tables
				if (USE_MY_ORCL){
				rs = stmt.executeQuery("SELECT table_name FROM all_tables WHERE owner='SCHEN35'");
				}else{
				rs = stmt.executeQuery("SELECT table_name FROM all_tables WHERE owner='MWANG22'");
				}
				System.out.println("Existed table names:");
				while (rs.next()) {
					String tableName = rs.getString("table_name");
					System.out.println(tableName);
					set.add(tableName.toLowerCase());
				}

				// Insert values
				if (set.contains("users")) {
					if(!DEV_MODE){
					
					stmt.executeUpdate("INSERT INTO Users " + "VALUES ('P1', GET_MD5('12345'))");

					stmt.executeUpdate("INSERT INTO Users " + "VALUES ('P2', GET_MD5('67890'))");

					stmt.executeUpdate("INSERT INTO Users " + "VALUES ('P3', GET_MD5('12345'))");

					stmt.executeUpdate("INSERT INTO Users " + "VALUES ('P4', GET_MD5('67890'))");
					
					stmt.executeUpdate("INSERT INTO Users " + "VALUES ('P5', GET_MD5('12345'))");
					
					stmt.executeUpdate("INSERT INTO Users " + "VALUES ('P6', GET_MD5('67890'))");
					
					}
					else {
						System.out.println("=====================================");
						System.out.println("Test Data for Users Table");
						System.out.println("=====================================");
						
						
						try {
							stmt.executeUpdate("INSERT INTO Users " +
									  "VALUES ('P1', GET_MD5('12345'))");
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}
						try {
							stmt.executeUpdate("INSERT INTO Users " +
									  "VALUES ('P2', GET_MD5('67890'))");
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}
						try {
							stmt.executeUpdate("INSERT INTO Users " +
									  "VALUES ('P3', GET_MD5('12345'))");
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}
						try {
							stmt.executeUpdate("INSERT INTO Users " +
									  "VALUES ('P4', GET_MD5('67890'))");
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}
						try {
							stmt.executeUpdate("INSERT INTO Users " +
									  "VALUES ('P5', GET_MD5('12345'))");
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}
						try {
							stmt.executeUpdate("INSERT INTO Users " +
									  "VALUES ('P6', GET_MD5('67890'))");
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}
						try {
							stmt.executeUpdate("INSERT INTO Users " +
									  "VALUES ('P7', GET_MD5('12345'))");
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}
						try {
							stmt.executeUpdate("INSERT INTO Users " +
									  "VALUES ('P8', GET_MD5('67890'))");
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}
						try {
							stmt.executeUpdate("INSERT INTO Users " +
									  "VALUES ('P9', GET_MD5('12345'))");
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}
						System.out.println("=====================================");
					}

					System.out.println("Successfully inserted new values into Users.");
				}

				if (set.contains("patient")) {


					if (!DEV_MODE) {
					stmt.executeUpdate("INSERT INTO Patient "
							+ "VALUES ('P1', 'Sheldon Cooper', TO_DATE('05/26/1984', 'mm/dd/yyyy'), 1, "
							+ "'2500 Sacramento, Apt 903, Santa Cruz, CA - 90021', 0)");

					stmt.executeUpdate("INSERT INTO Patient "
							+ "VALUES ('P2', 'Leonard Hofstader', TO_DATE('04/19/1989', 'mm/dd/yyyy'), 1, "
							+ "'2500 Sacramento, Apt 904, Santa Cruz, CA - 90021', 0)");

					stmt.executeUpdate("INSERT INTO Patient "
							+ "VALUES ('P3', 'Penny Hofstader', TO_DATE('12/25/1990', 'mm/dd/yyyy'), 2, "
							+ "'2500 Sacramento, Apt 904, Santa Cruz, CA - 90021', 0)");

					stmt.executeUpdate("INSERT INTO Patient "
							+ "VALUES ('P4', 'Amy Farrahfowler', TO_DATE('06/15/1992', 'mm/dd/yyyy'), 2, "
							+ "'2500 Sacramento, Apt 905, Santa Cruz, CA - 90021', 0)");
					
					stmt.executeUpdate("INSERT INTO Patient "
							+ "VALUES ('P5', 'lala', TO_DATE('06/15/1992', 'mm/dd/yyyy'), 1, "
							+ "'2500 Sacramento, Apt 905, Santa Cruz, CA - 90021', 0)");
					

					}
					
					else {
						System.out.println("=====================================");
						System.out.println("Test Data for Patient Table");
						System.out.println("=====================================");
						try {
						  stmt.executeUpdate("INSERT INTO Patient " +
						  "VALUES ('P1', 'Mike', TO_DATE('08/20/1966', 'mm/dd/yyyy'), 1, 'Raleigh, NC', 0)"
						  );
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try { 
						  stmt.executeUpdate("INSERT INTO Patient " +
						  "VALUES ('P2', 'Dave', TO_DATE('09/12/1976', 'mm/dd/yyyy'), 1, 'Los Angeles, CA', 0)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try {  
						  stmt.executeUpdate("INSERT INTO Patient " +
						  "VALUES ('P3', 'Linda', TO_DATE('12/30/1986', 'mm/dd/yyyy'), 2, 'Charlotte, NC', 0)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try {  
						  stmt.executeUpdate("INSERT INTO Patient " +
						  "VALUES ('P4', 'Alice', TO_DATE('01/24/1996', 'mm/dd/yyyy'), 2, 'Philadelphia, NC', 0)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try {  
						  stmt.executeUpdate("INSERT INTO Patient " +
						  "VALUES ('P5', 'Bob', TO_DATE('02/28/2006', 'mm/dd/yyyy'), 1, 'Orlando, FL', 0)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try {  
						  stmt.executeUpdate("INSERT INTO Patient " +
						  "VALUES ('P6', 'Amy', TO_DATE('03/05/2016', 'mm/dd/yyyy'), 2, 'Atlanta, GA', 0)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}
						  try {
//							// SickOrNot >1							
							stmt.executeUpdate("INSERT INTO Patient "
									+ "VALUES ('P8', 'Amy Farrahfowler', TO_DATE('06/15/1992', 'mm/dd/yyyy'), 2, "
									+ "'2500 Sacramento, Apt 905, Santa Cruz, CA - 90021', 2)");
							
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}
						//add correct test data
						stmt.executeUpdate("INSERT INTO Patient "
								+ "VALUES ('P7', 'Amy Farrahfowler', TO_DATE('06/15/1992', 'mm/dd/yyyy'), 2, "
								+ "'2500 Sacramento, Apt 905, Santa Cruz, CA - 90021', 0)");
					
						System.out.println("=====================================");
					}

					System.out.println("Successfully inserted new values into Patient.");
				}

				if (set.contains("supporter")) {


					if (!DEV_MODE) {
					stmt.executeUpdate("INSERT INTO Supporter " + "VALUES ('P2', 'Leonard Hofstader', '9191234567')");

					stmt.executeUpdate("INSERT INTO Supporter " + "VALUES ('P3', 'Penny Hofstader', '9192345678')");

					stmt.executeUpdate("INSERT INTO Supporter " + "VALUES ('P4', 'Amy Farrahfowler', '9193456789')");

					stmt.executeUpdate("INSERT INTO Supporter " + "VALUES ('P5', 'lala', '9193456789')");


					System.out.println("Successfully inserted new values into Supporter.");
					}
					else{
						
						System.out.println("=====================================");
						System.out.println("Test Data for supporter Table");
						System.out.println("=====================================");
						  try {
						  stmt.executeUpdate("INSERT INTO Supporter " +
						  "VALUES ('P2', 'Dave', '9191234567')");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try {  
						  stmt.executeUpdate("INSERT INTO Supporter " +
						  "VALUES ('P6', 'Amy', '9192345678')");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try {  
						  stmt.executeUpdate("INSERT INTO Supporter " +
						  "VALUES ('P7', 'Lisa', '9193456789')");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try {  
						  stmt.executeUpdate("INSERT INTO Supporter " +
						  "VALUES ('P8', 'Tom', '9194567890')");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try { 
						  stmt.executeUpdate("INSERT INTO Supporter " +
						  "VALUES ('P9', 'Amanda', '9190987654')");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

							System.out.println("=====================================");
							System.out.println("Successfully inserted new values into supporter.");
					}
				}

				if (set.contains("observation_forpatient")) {
					
					if (!DEV_MODE) {
					stmt.executeUpdate("INSERT INTO Observation_ForPatient "
							+ "VALUES ('P2', TO_TIMESTAMP('10/10/2016 00:00', 'mm/dd/yyyy hh24:mi'), TO_TIMESTAMP('10/11/2016 00:00', 'mm/dd/yyyy hh24:mi'), 180, null, null, null, null, null, null)");

					stmt.executeUpdate("INSERT INTO Observation_ForPatient "
							+ "VALUES ('P2', TO_TIMESTAMP('10/17/2016 00:00', 'mm/dd/yyyy hh24:mi'), TO_TIMESTAMP('10/17/2016 00:00', 'mm/dd/yyyy hh24:mi'), 95, null, null, null, null, null, null)");

					}
					else {
						System.out.println("=====================================");
						System.out.println("Test Data for Observation_ForPatient Table");
						System.out.println("=====================================");
						
						try{
						  stmt.executeUpdate("INSERT INTO Observation_ForPatient "
						  +
						  "VALUES ('P1', TO_TIMESTAMP('09/01/2016 18:00', 'mm/dd/yyyy hh24:mi'), TO_TIMESTAMP('09/01/2016 19:00', 'mm/dd/yyyy hh24:mi'), 150, 130, 80, null, null, null, 94)"
						  );
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{ 
						  stmt.executeUpdate("INSERT INTO Observation_ForPatient "
						  +
						  "VALUES ('P4', TO_TIMESTAMP('09/05/2016 18:00', 'mm/dd/yyyy hh24:mi'), TO_TIMESTAMP('09/05/2016 19:00', 'mm/dd/yyyy hh24:mi'), null, 110, 80, null, 2, 93, null)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  
						  try{
						  stmt.executeUpdate("INSERT INTO Observation_ForPatient "
						  +
						  "VALUES ('P3', TO_TIMESTAMP('09/02/2016 10:00', 'mm/dd/yyyy hh24:mi'), TO_TIMESTAMP('09/03/2016 10:00', 'mm/dd/yyyy hh24:mi'), null, 140, 70, null, null, 85, 97)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  						  
						 
//						try{
//							// negative weight
//							stmt.executeUpdate("INSERT INTO Observation_ForPatient "
//									+ "VALUES ('P8', TO_TIMESTAMP('10/17/2016 00:00', 'mm/dd/yyyy hh24:mi'), TO_TIMESTAMP('10/17/2016 00:00', 'mm/dd/yyyy hh24:mi'), -2, null, null, null, null, null, null)");
//						} catch (Throwable oops) {oops.printStackTrace();}
//						try{
//							// Oxy_pct out of range
//							stmt.executeUpdate("INSERT INTO Observation_ForPatient "
//									+ "VALUES ('P8', TO_TIMESTAMP('10/17/2016 00:01', 'mm/dd/yyyy hh24:mi'), TO_TIMESTAMP('10/17/2016 00:01', 'mm/dd/yyyy hh24:mi'), null, null, null, null, null, 101, null)");
//						} catch (Throwable oops) {oops.printStackTrace();}
						
							System.out.println("----");
						
						System.out.println("=====================================");	
					}
					
					
					System.out.println("Successfully inserted new values into Observation_ForPatient.");
				}

				if (set.contains("recommendation")) {
					
					if (!DEV_MODE) {
					stmt.executeUpdate("INSERT INTO Recommendation "
							+ "VALUES (0, 1, 7, 200, 120, 0, null, null, null, null, null, 0, null, null, 0, null, null, 0, null, null, null, 0, null, null, null)");

					stmt.executeUpdate("INSERT INTO Recommendation "
							+ "VALUES (1, 1, 7, 200, 120, 1, 1, 159, 140, 99, 90, 0, null, null, 1, 7, 3, 0, null, null, null, 0, null, null, null)");

					stmt.executeUpdate("INSERT INTO Recommendation "
							+ "VALUES (2, 1, 7, 200, 120, 0, null, null, null, null, null, 1, 1, 5, 0, null, null, 0, null, null, null, 0, null, null, null)");

					stmt.executeUpdate("INSERT INTO Recommendation "
							+ "VALUES (3, 0, null, null, null, 0, null, null, null, null, null, 0, null, null, 0, null, null, 1, 1, 99, 90, 1, 1, 100, 95)");

					stmt.executeUpdate("INSERT INTO Recommendation "
							+ "VALUES (4, 1, 7, 190, 120, 0, null, null, null, null, null, 1, 1, 5, 0, null, null, 0, null, null, null, 0, null, null, null)");
					}
					else {
						System.out.println("=====================================");
						System.out.println("Test Data for Recommendation Table");
						System.out.println("=====================================");	
						
						try{
						  stmt.executeUpdate("INSERT INTO Recommendation " +
						  "VALUES (0, 1, 7, 200, 120, 1, 7, 120, 100, 80, 60, 0, null, null, 0, null, null, 0, null, null, null, 1, 7, 100, 96)"
						  );
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}


						try{
						  stmt.executeUpdate("INSERT INTO Recommendation " +
						  "VALUES (1, 1, 7, 200, 100, 1, 7, 120, 100, 80, 60, 1, 2, 5, 1, 2, 3, 1, 2, 99, 85, 1, 7, 100, 96)"
						  );
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO Recommendation " +
						  "VALUES (2, 0, null, null, null, 1, 7, 120, 100, 80, 60, 0, null, null, 1, 2, 1, 1, 2, 90, 80, 0, null, null, null)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO Recommendation " +
						  "VALUES (3, 0, null, null, null, 0, null, null, null, null, null, 0, null, null, 0, null, null, 1, 2, 95, 75, 0, null, null, null)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO Recommendation " +
						  "VALUES (4, 1, 7, 200, 100, 1, 7, 120, 100, 80, 60, 0, null, null, 0, null, null, 1, 2, 99, 85, 1, 7, 100, 96)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
							 
						  stmt.executeUpdate("INSERT INTO Recommendation " +
						  "VALUES (5, 0, null, null, null, 1, 7, 130, 110, 90, 70, 0, null, null, 0, null, null, 1, 2, 100, 90, 1, 7, 102, 96)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  		 
						
//						try{
//							// Wuplimit_lb <= Wlowlimit_lb
//							stmt.executeUpdate("INSERT INTO Recommendation "
//									+ "VALUES (7, 1, 7, 120, 130, 0, null, null, null, null, null, 1, 1, 5, 0, null, null, 0, null, null, null, 0, null, null, null)");
//						} catch (Throwable oops) {oops.printStackTrace();}
//						try{
//							// Mvalue_Out_Of_Range out of range
//							stmt.executeUpdate("INSERT INTO Recommendation "
//									+ "VALUES (8, 1, 7, 180, 130, 0, null, null, null, null, null, 1, 1, 5, 1, 1, 4, 0, null, null, null, 0, null, null, null)");
//
//						} catch (Throwable oops) {oops.printStackTrace();}
//						try{
//							// BP_bool is 0 but BPfreq still has value
//							stmt.executeUpdate("INSERT INTO Recommendation "
//									+ "VALUES (9, 1, 7, 180, 130, 0, 1, null, null, null, null, 1, 1, 5, 1, 1, 3, 0, null, null, null, 0, null, null, null)");
//
//						} catch (Throwable oops) {oops.printStackTrace();}
							System.out.println("----");
						
						System.out.println("=====================================");	
					}
					
					
					System.out.println("Successfully inserted new values into Recommendation.");
				}

				if (set.contains("disease_drecomm")) {
					
					if (!DEV_MODE) {
					stmt.executeUpdate("INSERT INTO Disease_Drecomm " + "VALUES ('1', 'Heart Disease', 1)");

					stmt.executeUpdate("INSERT INTO Disease_Drecomm " + "VALUES ('2', 'HIV', 2)");

					stmt.executeUpdate("INSERT INTO Disease_Drecomm " + "VALUES ('3', 'COPD', 3)");
					}
					else{
						System.out.println("=====================================");
						System.out.println("Test Data for disease_drecomm Table");
						System.out.println("=====================================");	
						
						try{
						  stmt.executeUpdate("INSERT INTO Disease_Drecomm " +
						  "VALUES ('1', 'HIV', 1)");
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO Disease_Drecomm " +
						  "VALUES ('2', 'Heart Disease', 2)");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO Disease_Drecomm " +
						  "VALUES ('3', 'COPD', 3)");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  
						 
						
						System.out.println("=====================================");
					}

					System.out.println("Successfully inserted new values into Disease_Drecomm.");
				}

				// Combine wellpatient and sickpatient 
				// (We divided into wellpatient and sickpatient again)
				if (set.contains("assists")) {

					if (!DEV_MODE){
					stmt.executeUpdate(
							"INSERT INTO Assists " + "VALUES ('P1', 'P2', TO_DATE('10/21/2016', 'mm/dd/yyyy'), 1)");

					stmt.executeUpdate(
							"INSERT INTO Assists " + "VALUES ('P1', 'P4', TO_DATE('10/21/2016', 'mm/dd/yyyy'), 2)");

					stmt.executeUpdate(
							"INSERT INTO Assists " + "VALUES ('P2', 'P3', TO_DATE('10/09/2016', 'mm/dd/yyyy'), 1)");

					stmt.executeUpdate(
							"INSERT INTO Assists " + "VALUES ('P3', 'P4', TO_DATE('10/21/2016', 'mm/dd/yyyy'), 1)");
					}
					else {
						System.out.println("=====================================");
						System.out.println("Test Data for Assists Table");
						System.out.println("=====================================");						
//						try{
//							// p2 have the same user as 1st and 2nd supporter 
//							stmt.executeUpdate(
//									"INSERT INTO Assists " + "VALUES ('P2', 'P3', TO_DATE('10/21/2016', 'mm/dd/yyyy'), 2)");
//						} catch (Throwable oops) {oops.printStackTrace();}
//						try{
//							// null weight
//							} catch (Throwable oops) {oops.printStackTrace();}
						 try{	  
							  stmt.executeUpdate("INSERT INTO Assists " +
							  "VALUES ('P3', 'P2', TO_DATE('02/01/2016', 'mm/dd/yyyy'), 1)"
							  );
					  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

					  try{  
					  stmt.executeUpdate("INSERT INTO Assists " +
					  "VALUES ('P4', 'P6', TO_DATE('03/01/2016', 'mm/dd/yyyy'), 1)"
					  );
					  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

					  try{
					  stmt.executeUpdate("INSERT INTO Assists " +
					 "VALUES ('P5', 'P7', TO_DATE('02/15/2016', 'mm/dd/yyyy'), 1)"
					  );
					  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

					  try{
					  stmt.executeUpdate("INSERT INTO Assists " +
					  "VALUES ('P5', 'P8', TO_DATE('03/15/2016', 'mm/dd/yyyy'), 2)"
					  );
					  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

					  try{
					  stmt.executeUpdate("INSERT INTO Assists " +
					  "VALUES ('P6', 'P8', TO_DATE('03/30/2016', 'mm/dd/yyyy'), 1)"
					  );
					  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

					  try{
					  stmt.executeUpdate("INSERT INTO Assists " +
					  "VALUES ('P6', 'P9', TO_DATE('04/30/2016', 'mm/dd/yyyy'), 2)"
					  );
					  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}
					  
				 
				
				 				//correct data  
					
				  try{
				  stmt.executeUpdate("INSERT INTO Assists " +
							  "VALUES ('P2', 'P9', TO_DATE('01/01/2016', 'mm/dd/yyyy'), 1)"
							  );
					} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						
						
							System.out.println("----");
						
						System.out.println("=====================================");	
					}
					
					
					System.out.println("Successfully inserted new values into Assists.");
				}
				
				// (We divided into wellpatient and sickpatient again)
				if (set.contains("assistss")) {
					

					if (!DEV_MODE){
					stmt.executeUpdate(
							"INSERT INTO AssistsS " + "VALUES ('P1', 'P2', TO_DATE('10/21/2016', 'mm/dd/yyyy'), 1)");

					stmt.executeUpdate(
							"INSERT INTO AssistsS " + "VALUES ('P1', 'P4', TO_DATE('10/21/2016', 'mm/dd/yyyy'), 2)");

					stmt.executeUpdate(
							"INSERT INTO AssistsS " + "VALUES ('P2', 'P3', TO_DATE('10/09/2016', 'mm/dd/yyyy'), 1)");

					}
					else {
						System.out.println("=====================================");
						System.out.println("Test Data for AssistsS Table");
						System.out.println("=====================================");	
						

						
						  try{	  
								  stmt.executeUpdate("INSERT INTO AssistsS " +
								  "VALUES ('P3', 'P2', TO_DATE('02/01/2016', 'mm/dd/yyyy'), 1)"
								  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{  
						  stmt.executeUpdate("INSERT INTO AssistsS " +
						  "VALUES ('P4', 'P6', TO_DATE('03/01/2016', 'mm/dd/yyyy'), 1)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO AssistsS " +
						 "VALUES ('P5', 'P7', TO_DATE('02/15/2016', 'mm/dd/yyyy'), 1)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO AssistsS " +
						  "VALUES ('P5', 'P8', TO_DATE('03/15/2016', 'mm/dd/yyyy'), 2)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO AssistsS " +
						  "VALUES ('P6', 'P8', TO_DATE('03/30/2016', 'mm/dd/yyyy'), 1)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO AssistsS " +
						  "VALUES ('P6', 'P9', TO_DATE('04/30/2016', 'mm/dd/yyyy'), 2)"
						  );
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

								 
								 
//						try{
//							// p2 have the same user as 1st and 2nd supporter 
//							stmt.executeUpdate(
//									"INSERT INTO AssistsS " + "VALUES ('P3', '', TO_DATE('10/21/2016', 'mm/dd/yyyy'), 2)");
//						} catch (Throwable oops) {oops.printStackTrace();}
//						try{
//							// P2 be the supporter of themselves
//							stmt.executeUpdate(
//									"INSERT INTO AssistsS " + "VALUES ('P3', 'P3', TO_DATE('10/21/2016', 'mm/dd/yyyy'), 2)");
//
//							} catch (Throwable oops) {oops.printStackTrace();}
						
							System.out.println("----");
						
						System.out.println("=====================================");	
					}
					
					
					System.out.println("Successfully inserted new values into AssistsS.");
				}
				if (set.contains("assistsw")) {
					

					if (!DEV_MODE){
					stmt.executeUpdate(
							"INSERT INTO AssistsW " + "VALUES ('P3', 'P4', TO_DATE('10/21/2016', 'mm/dd/yyyy'), 1)");
					}
					
					else {
						System.out.println("=====================================");
						System.out.println("Test Data for AssistsW Table");
						System.out.println("=====================================");
						try{
							//correct data  
							stmt.executeUpdate("INSERT INTO AssistsW " +
									  "VALUES ('P2', 'P9', TO_DATE('01/01/2016', 'mm/dd/yyyy'), 1)"
									  );
							} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

//						try{
//							// P3 be the supporter of themselves
//							stmt.executeUpdate(
//									"INSERT INTO AssistsW " + "VALUES ('P2', 'P2', TO_DATE('10/21/2016', 'mm/dd/yyyy'), 2)");
//						} catch (Throwable oops) {oops.printStackTrace();}
						
						try{
							// 
							} catch (Throwable oops) {oops.printStackTrace();}
						
							System.out.println("----");
						
						System.out.println("=====================================");	
					}
					
					
					System.out.println("Successfully inserted new values into AssistsS.");
				}

				if (set.contains("patientdisease")) {
					

					if (!DEV_MODE){
					stmt.executeUpdate(
							"INSERT INTO PatientDisease " + "VALUES ('P1', '1', TO_DATE('10/22/2016', 'mm/dd/yyyy'))");

					stmt.executeUpdate(
							"INSERT INTO PatientDisease " + "VALUES ('P2', '2', TO_DATE('10/10/2016', 'mm/dd/yyyy'))");
					}
					
					else{
						System.out.println("=====================================");
						System.out.println("Test Data for patientdisease Table");
						System.out.println("=====================================");
						
						try{
						  stmt.executeUpdate("INSERT INTO PatientDisease " +
						  "VALUES ('P3', '1', TO_DATE('10/22/2016', 'mm/dd/yyyy'))");
						} catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO PatientDisease " +
						  "VALUES ('P4', '2', TO_DATE('10/20/2016', 'mm/dd/yyyy'))");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO PatientDisease " +
						  "VALUES ('P5', '3', TO_DATE('10/21/2016', 'mm/dd/yyyy'))");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO PatientDisease " +
						  "VALUES ('P6', '2', TO_DATE('10/12/2016', 'mm/dd/yyyy'))");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO PatientDisease " +
						  "VALUES ('P3', '2', TO_DATE('11/22/2016', 'mm/dd/yyyy'))");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO PatientDisease " +
						  "VALUES ('P4', '3', TO_DATE('09/22/2016', 'mm/dd/yyyy'))");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}


//						  try{
//							  // disease not exists
//						  stmt.executeUpdate("INSERT INTO PatientDisease " +
//						  "VALUES ('P4', '13', TO_DATE('09/22/2016', 'mm/dd/yyyy'))");
//						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						
						System.out.println("=====================================");	
					}
					System.out.println("Successfully inserted new values into PatientDisease.");

				}

				if (set.contains("doctorrecomm")) {
					

					if (!DEV_MODE){
					stmt.executeUpdate("INSERT INTO DoctorRecomm " + "VALUES ('P2', 4)");
					}
					else{
						System.out.println("=====================================");
						System.out.println("Test Data for DoctorRecomm Table");
						System.out.println("=====================================");
						
						try{
						  stmt.executeUpdate("INSERT INTO DoctorRecomm " + 
						  //"VALUES ('1', '4', null)"); 
								  "VALUES ('P1', 4)");
						 } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  try{
						  stmt.executeUpdate("INSERT INTO DoctorRecomm " + 
						  // "VALUES ('3', '5', '1')"); 
								  "VALUES ('P3', 5)");
						  } catch (Throwable oops) {oops.printStackTrace();System.out.println("----");}

						  
						
						System.out.println("=====================================");
					}
					System.out.println("Successfully inserted new values into DoctorRecomm.");
				}

				/*******************
				 * These tables won't be used
				 **********************/
				/*
				 * if(set.contains("wellpatient")) {
				 * 
				 * stmt.executeUpdate("INSERT INTO WellPatient " +
				 * "VALUES ('1')");
				 * 
				 * stmt.executeUpdate("INSERT INTO WellPatient " +
				 * "VALUES ('2')");
				 * 
				 * System.out.
				 * println("Successfully inserted new values into WellPatient."
				 * ); }
				 * 
				 * if(set.contains("sickpatient")) {
				 * 
				 * stmt.executeUpdate("INSERT INTO SickPatient " +
				 * "VALUES ('3')");
				 * 
				 * stmt.executeUpdate("INSERT INTO SickPatient " +
				 * "VALUES ('4')");
				 * 
				 * stmt.executeUpdate("INSERT INTO SickPatient " +
				 * "VALUES ('5')");
				 * 
				 * stmt.executeUpdate("INSERT INTO SickPatient " +
				 * "VALUES ('6')");
				 * 
				 * System.out.
				 * println("Successfully inserted new values into SickPatient."
				 * ); }
				 * 
				 * if(set.contains("assistsw")) {
				 * 
				 * stmt.executeUpdate("INSERT INTO AssistsW " +
				 * "VALUES ('9', '2', TO_DATE('01/01/2016', 'mm/dd/yyyy'), 1)");
				 * 
				 * System.out.
				 * println("Successfully inserted new values into AssistsW."); }
				 * 
				 * if(set.contains("assistss")) {
				 * 
				 * stmt.executeUpdate("INSERT INTO AssistsS " +
				 * "VALUES ('2', '3', TO_DATE('02/01/2016', 'mm/dd/yyyy'), 1)");
				 * 
				 * stmt.executeUpdate("INSERT INTO AssistsS " +
				 * "VALUES ('6', '4', TO_DATE('03/01/2016', 'mm/dd/yyyy'), 1)");
				 * 
				 * stmt.executeUpdate("INSERT INTO AssistsS " +
				 * "VALUES ('7', '5', TO_DATE('02/15/2016', 'mm/dd/yyyy'), 1)");
				 * 
				 * stmt.executeUpdate("INSERT INTO AssistsS " +
				 * "VALUES ('8', '5', TO_DATE('03/15/2016', 'mm/dd/yyyy'), 2)");
				 * 
				 * stmt.executeUpdate("INSERT INTO AssistsS " +
				 * "VALUES ('8', '6', TO_DATE('03/30/2016', 'mm/dd/yyyy'), 1)");
				 * 
				 * stmt.executeUpdate("INSERT INTO AssistsS " +
				 * "VALUES ('9', '6', TO_DATE('04/30/2016', 'mm/dd/yyyy'), 2)");
				 * 
				 * System.out.
				 * println("Successfully inserted new values into AssistsS."); }
				 * 
				 * 
				 * if(set.contains("spatientdisease")) {
				 * 
				 * stmt.executeUpdate("INSERT INTO SPatientDisease " +
				 * "VALUES ('3', '1')");
				 * 
				 * stmt.executeUpdate("INSERT INTO SPatientDisease " +
				 * "VALUES ('4', '2')");
				 * 
				 * stmt.executeUpdate("INSERT INTO SPatientDisease " +
				 * "VALUES ('5', '3')");
				 * 
				 * stmt.executeUpdate("INSERT INTO SPatientDisease " +
				 * "VALUES ('6', '2')");
				 * 
				 * stmt.executeUpdate("INSERT INTO SPatientDisease " +
				 * "VALUES ('3', '2')");
				 * 
				 * stmt.executeUpdate("INSERT INTO SPatientDisease " +
				 * "VALUES ('4', '3')");
				 * 
				 * System.out.
				 * println("Successfully inserted new values into SPatientDisease."
				 * ); }
				 */

			} finally {
				close(rs);
				close(stmt);
				close(conn);
			}
		} catch (Throwable oops) {
			oops.printStackTrace();
		}
	}

	static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Throwable whatever) {
			}
		}
	}

	static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Throwable whatever) {
			}
		}
	}

	static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Throwable whatever) {
			}
		}
	}
}