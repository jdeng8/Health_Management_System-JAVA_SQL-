/*
 *  7 entity tables: Users, Patient, WellPatient, SickPatient, Supporter, Recommendation, Alert
 *  2 entity_relation tables: Disease_DRecomm, Observation_ForPatient
 *  4 relation tables: AssistsS, AssistsW, SPatientDisease, DoctorRecomm
 */
/*
 *	try adding constrains and triggers.
 */

import java.sql.*;
import java.util.*;

public class CreateTables {
	
	
	
	
	static final boolean USE_MY_ORCL = false;
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
            ResultSet fs = null;
            Set<String> set = new HashSet<String>();
            Set<String> fset = new HashSet<String>();
            
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

				/**************************************************************/
				//Delete existed functions
				
				fs = stmt.executeQuery("SELECT * from user_objects where object_type = 'FUNCTION'");
				System.out.println("Existed function names:");
				while(fs.next()) {
					String functionName = fs.getString("object_name");
					System.out.println(functionName);
					fset.add(functionName.toLowerCase());
				}
				if(fset.contains("is_number")) {
					stmt.executeUpdate("DROP FUNCTION Is_number");
					System.out.println("DROP FUNCTION Is_number");
				}
				
				//Delete existed tables
				if (USE_MY_ORCL){
				rs = stmt.executeQuery("SELECT table_name FROM all_tables WHERE owner='SCHEN35'");
				}else{
				rs = stmt.executeQuery("SELECT table_name FROM all_tables WHERE owner='MWANG22'");
				}
				System.out.println("Existed table names:");
				while(rs.next()) {
					String tableName = rs.getString("table_name");
					System.out.println(tableName);
					set.add(tableName.toLowerCase());
				}

				if(set.contains("assists")) {
					stmt.executeUpdate("DROP TABLE Assists");
					System.out.println("DROP TABLE Assists");
				}

				if(set.contains("assistsw")) {
					stmt.executeUpdate("DROP TABLE AssistsW");
					System.out.println("DROP TABLE AssistsW");
				}

				if(set.contains("assistss")) {
					stmt.executeUpdate("DROP TABLE AssistsS");
					System.out.println("DROP TABLE AssistsS");
				}

				if(set.contains("spatientdisease")) {
					stmt.executeUpdate("DROP TABLE SPatientDisease");
					System.out.println("DROP TABLE SPatientDisease");
				}

				if(set.contains("patientdisease")) {
					stmt.executeUpdate("DROP TABLE PatientDisease");
					System.out.println("DROP TABLE PatientDisease");
				}

				if(set.contains("doctorrecomm")) {
					stmt.executeUpdate("DROP TABLE DoctorRecomm");
					System.out.println("DROP TABLE DoctorRecomm");
				}

				if(set.contains("disease_drecomm")) {
					stmt.executeUpdate("DROP TABLE Disease_DRecomm");
					System.out.println("DROP TABLE Disease_DRecomm");
				}

				if(set.contains("observation_forpatient")) {
					stmt.executeUpdate("DROP TABLE Observation_ForPatient CASCADE CONSTRAINTS");
					System.out.println("DROP TABLE Observation_ForPatient");
				}

				if(set.contains("alert")) {
					stmt.executeUpdate("DROP TABLE Alert");
					System.out.println("DROP TABLE Alert");
				}

				if(set.contains("recommendation")) {
					stmt.executeUpdate("DROP TABLE Recommendation");
					System.out.println("DROP TABLE Recommendation");
				}

				if(set.contains("supporter")) {
					stmt.executeUpdate("DROP TABLE Supporter");
					System.out.println("DROP TABLE Supporter");
				}

				if(set.contains("sickpatient")) {
					stmt.executeUpdate("DROP TABLE SickPatient");
					System.out.println("DROP TABLE SickPatient");
				}

				if(set.contains("wellpatient")) {
					stmt.executeUpdate("DROP TABLE WellPatient");
					System.out.println("DROP TABLE WellPatient");
				}

				if(set.contains("patient")) {
					stmt.executeUpdate("DROP TABLE Patient");
					System.out.println("DROP TABLE Patient");
				}

				if(set.contains("users")) {
					stmt.executeUpdate("DROP TABLE Users");
					System.out.println("DROP TABLE Users");
				}


				/**************************************************************/
				//Create functions for CONSTRAINT and TRIGGER 
				// before all tables are created
				stmt.executeUpdate("CREATE OR REPLACE FUNCTION IS_NUMBER(test_string IN VARCHAR2) " +
				   			"RETURN INT " +
				   		"IS " + 
				   			"v_num NUMBER; " +
				   		"BEGIN " +
				   			"v_num := TO_NUMBER(test_string); " +
				   			"RETURN 1; " +
				   		"EXCEPTION " +
				   		"WHEN VALUE_ERROR THEN " +
				   			"RETURN 0; " +
				   		"END; ");

				System.out.println("NEW FUNCTION Is_number CREATED");
				
				try{
				stmt.executeUpdate("CREATE OR REPLACE FUNCTION GET_MD5"+
                "(p_str in varchar2) "+
                "RETURN varchar2 IS "+
                "BEGIN "+
                "RETURN Utl_Raw.Cast_To_Raw("+
                "DBMS_OBFUSCATION_TOOLKIT.MD5(input_string => P_Str)); "+
                "END; ");
				
				System.out.println("NEW FUNCTION GET_MD5 CREATED");
				} catch (Throwable oops) {oops.printStackTrace();}
				
				
				
				/**************************************************************/
				//Create tables for Entities

				stmt.executeUpdate("CREATE TABLE Users (" +
			   		"UserID VARCHAR2(20) NOT NULL, " +
			   		// "Pw VARCHAR2(20) NOT NULL, " + 
			   		"Pw RAW(200) NOT NULL, " + 
			   		"CONSTRAINT U_UserID_Should_be_Unique PRIMARY KEY (UserID))");

				System.out.println("NEW TABLE Users CREATED");

				

				stmt.executeUpdate("CREATE TABLE Patient (" +
					"UserID VARCHAR2(20) NOT NULL, " +
					"Name VARCHAR2(40) NOT NULL, " +
					"DOB DATE NOT NULL, " +
					"Gender NUMBER(1) NOT NULL, " + 
					"Address VARCHAR2(50) NOT NULL, " + 
					"SickOrNot NUMBER(1) NOT NULL, " + 
					"CONSTRAINT P_UserID_Should_be_Unique PRIMARY KEY (UserID), " + 
					"FOREIGN KEY (UserID) REFERENCES Users ON DELETE CASCADE, " +
					"CONSTRAINT P_SickOrNot_Out_Of_Range CHECK (SickOrNot >= 0))");

				System.out.println("NEW TABLE Patient CREATED");

				
				// stmt.executeUpdate("DROP TABLE Supporter");

				stmt.executeUpdate("CREATE TABLE Supporter (" +
					"UserID VARCHAR2(20) NOT NULL, " + 
					"Name VARCHAR2(40) NOT NULL, " + 
					"Mobile VARCHAR2(20) NOT NULL, " + 
					"CONSTRAINT S_UserID_Should_be_Unique PRIMARY KEY (UserID), " + 
					"FOREIGN KEY (UserID) REFERENCES Users ON DELETE CASCADE)");

				System.out.println("NEW TABLE Supporter CREATED");

				//This table is for entity Observation and relationship ObserveForPatient
				// stmt.executeUpdate("DROP TABLE Observation_ForPatient");

				stmt.executeUpdate("CREATE TABLE Observation_ForPatient (" +
					"UserID VARCHAR2(20) NOT NULL, " + 
					"ObserveTime TIMESTAMP NOT NULL, " +
					"RecTime TIMESTAMP NOT NULL, " +
					"Weight_lb NUMBER(3), " + 
					"BPS_mmHg NUMBER(3), " + 
					"BPD_mmHg NUMBER(3), " +
					"Pain_level NUMBER(2), " + 
					"Mood_level NUMBER(1), " + 
					"Oxy_pct NUMBER(3), " +
					"Temperature_F NUMBER(3), " +
					"CONSTRAINT O_UserID_ObsvTime_Not_Unique PRIMARY KEY (UserID, ObserveTime), " + 
					"FOREIGN KEY (UserID) REFERENCES Patient ON DELETE CASCADE, " +
					
					"CONSTRAINT O_Weight_lb_Out_Of_Range CHECK ((Weight_lb >= 0) OR (Weight_lb IS NULL)), " +
					"CONSTRAINT O_BPS_mmHg_Out_Of_Range CHECK ((BPS_mmHg >= 0) OR (BPS_mmHg IS NULL)), " +
					"CONSTRAINT O_BPD_mmHg_Out_Of_Range CHECK ((BPD_mmHg >= 0) OR (BPD_mmHg IS NULL)), " +
					"CONSTRAINT O_Pain_level_Out_Of_Range CHECK ((Pain_level >= 0 AND Pain_level <= 10) OR (Pain_level IS NULL)), " +
					"CONSTRAINT O_Mood_level_Out_Of_Range CHECK ((Mood_level >= 1 AND Mood_level <= 3) OR (Mood_level IS NULL)), " +
					"CONSTRAINT O_Oxy_pct_Out_Of_Range CHECK ((Oxy_pct >= 0 AND Oxy_pct <= 100) OR (Oxy_pct IS NULL)), " +
					"CONSTRAINT O_Temperature_F_Out_Of_Range CHECK ((Temperature_F >= 0) OR (Temperature_F IS NULL)), " +
					"CONSTRAINT O_BPS_AND_BPD_Shoud_In_Pairs CHECK (((BPS_mmHg >= 0) AND (BPD_mmHg >= 0)) OR ((BPS_mmHg IS NULL) AND (BPD_mmHg IS NULL))) " +
						")");

				System.out.println("NEW TABLE Observation_ForPatient CREATED");

				
				// stmt.executeUpdate("DROP TABLE Recommendation");

				stmt.executeUpdate("CREATE TABLE Recommendation (" +
					"Rid NUMBER(3) NOT NULL, " +
					"Weight_bool NUMBER(1) NOT NULL, " +
					"Wfreq NUMBER(3), " +
					"Wuplimit_lb NUMBER(3), " +
					"Wlowlimit_lb NUMBER(3), " +
					
					"BP_bool NUMBER(1) NOT NULL, " +
					"BPfreq NUMBER(3), " +
					"BPSuplimit_mmHg NUMBER(3), " +
					"BPSlowlimit_mmHg NUMBER(3), " +
					"BPDuplimit_mmHg NUMBER(3), " +
					"BPDlowlimit_mmHg NUMBER(3), " +
					
					"Pain_bool NUMBER(1) NOT NULL, " +
					"Pfreq NUMBER(3), " +
					"Pvalue NUMBER(2), " +
					
					"Mood_bool NUMBER(1) NOT NULL, " +
					"Mfreq NUMBER(3), " +
					"Mvalue NUMBER(1), " +
					
					"Oxy_bool NUMBER(1) NOT NULL, " +
					"Ofreq NUMBER(3), " +
					"Ouplimit_pct NUMBER(3), " +
					"Olowlimit_pct NUMBER(3), " +
					
					"Temp_bool NUMBER(1) NOT NULL, " +
					"Tfreq NUMBER(3), " +
					"Tuplimit_F NUMBER(3), " +
					"Tlowlimit_F NUMBER(3), " +
					// "AlertThres_pct NUMBER(3), " +
					// "ConsecutiveNum NUMBER(2), " +
					"CONSTRAINT R_Rid_Should_be_Unique PRIMARY KEY (Rid), " +
					
					"CONSTRAINT R_Wfreq_Out_Of_Range CHECK ((Wfreq > 0) OR (Wfreq IS NULL)), "+
					"CONSTRAINT R_Wuplimit_lb_Out_Of_Range CHECK ((Wuplimit_lb >= 0) OR (Wuplimit_lb IS NULL)), "+
					"CONSTRAINT R_Wlowlimit_lb_Out_Of_Range CHECK ((Wlowlimit_lb >= 0) OR (Wlowlimit_lb IS NULL)), "+
					"CONSTRAINT R_Wuplimit_LessThan_Wlowlimit CHECK ((Wuplimit_lb >= Wlowlimit_lb) OR ((Wuplimit_lb IS NULL) AND (Wlowlimit_lb IS NULL))), "+
					"CONSTRAINT R_Weight_Recom_illegal CHECK (((Weight_bool =0) AND (Wfreq IS NULL) AND (Wuplimit_lb IS NULL) AND (Wlowlimit_lb IS NULL)) OR ((Weight_bool =1) AND (Wfreq IS NOT NULL) AND (Wuplimit_lb IS NOT NULL) AND (Wlowlimit_lb IS NOT NULL))), "+

					
					"CONSTRAINT R_BPfreq_Out_Of_Range CHECK ((BPfreq > 0) OR (BPfreq IS NULL)), "+
					"CONSTRAINT R_BPSuplimit_Out_Of_Range CHECK ((BPSuplimit_mmHg >= 0) OR (BPSuplimit_mmHg IS NULL)), "+
					"CONSTRAINT R_BPSlowlimit_Out_Of_Range CHECK ((BPSlowlimit_mmHg >= 0) OR (BPSlowlimit_mmHg IS NULL)), "+
					"CONSTRAINT R_BPSuplim_LessThan_lowlimit CHECK ((BPSuplimit_mmHg >= BPSlowlimit_mmHg) OR ((BPSuplimit_mmHg IS NULL) AND (BPSlowlimit_mmHg IS NULL))), "+
					"CONSTRAINT R_BPDuplimit_Out_Of_Range CHECK ((BPDuplimit_mmHg >= 0) OR (BPDuplimit_mmHg IS NULL)), "+
					"CONSTRAINT R_BPDlowlimit_Out_Of_Range CHECK ((BPDlowlimit_mmHg >= 0) OR (BPDlowlimit_mmHg IS NULL)), "+
					"CONSTRAINT R_BPDupLim_LessThan_lowlimit CHECK ((BPDuplimit_mmHg >= BPDlowlimit_mmHg) OR ((BPDuplimit_mmHg IS NULL) AND (BPDlowlimit_mmHg IS NULL))), "+
					"CONSTRAINT R_BP_Recom_illegal CHECK (((BP_bool =0) AND (BPfreq IS NULL) AND (BPSuplimit_mmHg IS NULL) AND (BPSlowlimit_mmHg IS NULL) AND (BPDuplimit_mmHg IS NULL) AND (BPDlowlimit_mmHg IS NULL)) OR ((BP_bool =1) AND (BPfreq IS NOT NULL) AND (BPSuplimit_mmHg IS NOT NULL) AND (BPSlowlimit_mmHg IS NOT NULL) AND (BPDuplimit_mmHg IS NOT NULL) AND (BPDlowlimit_mmHg IS NOT NULL))), "+


					"CONSTRAINT R_Pfreq_Out_Of_Range CHECK ((Pfreq > 0) OR (Pfreq IS NULL)), "+
					"CONSTRAINT R_Pvalue_Out_Of_Range CHECK ((Pvalue >= 0 AND Pvalue <= 10) OR (Pvalue IS NULL)), "+
					"CONSTRAINT R_Pain_Recom_illegal CHECK (((Pain_bool =0) AND (Pfreq IS NULL) AND (Pvalue IS NULL)) OR ((Pain_bool =1) AND (Pfreq IS NOT NULL) AND (Pvalue IS NOT NULL))), "+

					
					"CONSTRAINT R_Mfreq_Out_Of_Range CHECK ((Mfreq > 0) OR (Mfreq IS NULL)), "+
					"CONSTRAINT R_Mvalue_Out_Of_Range CHECK ((Mvalue >= 1 AND Mvalue <= 3) OR (Mvalue IS NULL)), "+
					"CONSTRAINT R_Mood_Recom_illegal CHECK (((Mood_bool =0) AND (Mfreq IS NULL) AND (Mvalue IS NULL)) OR ((Mood_bool =1) AND (Mfreq IS NOT NULL) AND (Mvalue IS NOT NULL))), "+

					
					"CONSTRAINT R_Ofreq_Out_Of_Range CHECK ((Ofreq > 0) OR (Ofreq IS NULL)), "+
					"CONSTRAINT R_Ouplimit_pct_Out_Of_Range CHECK ((Ouplimit_pct >= 0) OR (Ouplimit_pct IS NULL)), "+
					"CONSTRAINT R_Olowlimit_pct_Out_Of_Range CHECK ((Olowlimit_pct >= 0) OR (Olowlimit_pct IS NULL)), "+
					"CONSTRAINT R_Ouplimit_LessThan_Olowlimit CHECK ((Ouplimit_pct >= Olowlimit_pct) OR ((Ouplimit_pct IS NULL) AND (Olowlimit_pct IS NULL))), "+
					"CONSTRAINT R_Oxy_Recom_illegal CHECK (((Oxy_bool =0) AND (Ofreq IS NULL) AND (Ouplimit_pct IS NULL) AND (Olowlimit_pct IS NULL)) OR ((Oxy_bool =1) AND (Ofreq IS NOT NULL) AND (Ouplimit_pct IS NOT NULL) AND (Olowlimit_pct IS NOT NULL))), "+

					
					"CONSTRAINT R_Tfreq_Out_Of_Range CHECK ((Tfreq > 0) OR (Tfreq IS NULL)), "+
					"CONSTRAINT R_Tuplimit_F_Out_Of_Range CHECK ((Tuplimit_F >= 0) OR (Tuplimit_F IS NULL)), "+
					"CONSTRAINT R_Tlowlimit_F_Out_Of_Range CHECK ((Tlowlimit_F >= 0) OR (Tlowlimit_F IS NULL)), "+
					"CONSTRAINT R_Tuplimit_LessThan_Tlowlimit CHECK ((Tuplimit_F >= Tlowlimit_F) OR ((Tuplimit_F IS NULL) AND (Tlowlimit_F IS NULL))), "+
					"CONSTRAINT R_Temp_Recom_illegal CHECK (((Temp_bool =0) AND (Tfreq IS NULL) AND (Tuplimit_F IS NULL) AND (Tlowlimit_F IS NULL)) OR ((Temp_bool =1) AND (Tfreq IS NOT NULL) AND (Tuplimit_F IS NOT NULL) AND (Tlowlimit_F IS NOT NULL))) "+

					
					
					")");

				System.out.println("NEW TABLE Recommendation CREATED");

				//This table is for entity Disease and relationship DiseaseRecomm
				// stmt.executeUpdate("DROP TABLE Disease_DRecomm");

				stmt.executeUpdate("CREATE TABLE Disease_DRecomm (" +
					"Did VARCHAR2(20) NOT NULL, " + 
					"Dname VARCHAR2(40) NOT NULL, " + 
					"Rid NUMBER(3) NOT NULL, " +
					"CONSTRAINT DDR_Did_Should_be_Unique PRIMARY KEY (Did), " +
					"FOREIGN KEY (Rid) REFERENCES Recommendation ON DELETE CASCADE)");

				System.out.println("NEW TABLE Disease_DRecomm CREATED");

				
				// stmt.executeUpdate("DROP TABLE Alert");

				stmt.executeUpdate("CREATE TABLE Alert (" +
					//"Aid VARCHAR2(20) NOT NULL, " +
					"UserID VARCHAR2(20) NOT NULL, " + 
					"ObserveTime TIMESTAMP NOT NULL, " +
					"Time TIMESTAMP WITH LOCAL TIME ZONE NOT NULL, " +
					"AType NUMBER(1) NOT NULL, " + 
					"AMsg VARCHAR2(100) NOT NULL, " +
					"Active NUMBER(1) NOT NULL, " +
					
//					"CONSTRAINT A_Alert_Should_be_Unique PRIMARY KEY (UserID, ObserveTime, AMsg, Active), " + 
					//"FOREIGN KEY (UserID) REFERENCES Patient ON DELETE CASCADE, "+					
					"FOREIGN KEY (UserID, ObserveTime) REFERENCES Observation_ForPatient ON DELETE CASCADE, "+
					
					"CONSTRAINT A_Alert_Should_be_Unique PRIMARY KEY (UserID, ObserveTime, AMsg))" );

				System.out.println("NEW TABLE Alert CREATED");


				/**************************************************************/

				//Create tables for Relationships

				//Combine sickpatient and wellpatient
				//Generate new Assists table and new PatientDisease table
				//Assists for Sick Patient
					stmt.executeUpdate("CREATE TABLE Assists (" +
						"P_UserID VARCHAR2(20) NOT NULL, " +
						"S_UserID VARCHAR2(20) NOT NULL, " +
						"Since DATE NOT NULL, " +
						"Priority NUMBER(1) NOT NULL, " +
						"CONSTRAINT AW_PUID_Prioy_NOT_Unique PRIMARY KEY (P_UserID, Priority), " +
						"FOREIGN KEY (S_UserID) REFERENCES Supporter ON DELETE CASCADE, " +
						"FOREIGN KEY (P_UserID) REFERENCES Patient ON DELETE CASCADE, "+
						"CONSTRAINT AW_PUID_SUID_NOT_Unique UNIQUE (P_UserID, S_UserID), " +
						"CONSTRAINT AW_PUID_SUID_Should_diff CHECK (P_UserID <> S_UserID), "+
						"CONSTRAINT AW_Priority_Out_Of_Range CHECK ((Priority >= 1) OR (Priority <= 2))"+
						")");

					System.out.println("NEW TABLE Assists CREATED");

/*
				stmt.executeUpdate("CREATE TABLE AssistsS (" +
					"P_UserID VARCHAR2(20) NOT NULL, " +
					"S_UserID VARCHAR2(20) NOT NULL, " +
					"Since DATE NOT NULL, " +
					"Priority NUMBER(1) NOT NULL, " +
					"CONSTRAINT AS_PUID_Prioy_NOT_Unique PRIMARY KEY (P_UserID, Priority), " +
					"FOREIGN KEY (S_UserID) REFERENCES Supporter ON DELETE CASCADE, " +
					"FOREIGN KEY (P_UserID) REFERENCES Patient ON DELETE CASCADE, "+
					"CONSTRAINT AS_PUID_SUID_NOT_Unique UNIQUE (P_UserID, S_UserID), " +
					"CONSTRAINT AS_PUID_SUID_Should_diff CHECK (P_UserID <> S_UserID), "+
					"CONSTRAINT AS_Priority_Out_Of_Range CHECK ((Priority >= 1) OR (Priority <= 2))"+
//					"CHECK ( (SELECT COUNT(S.P_UserID)" +
//							"FROM AssistsS S "+
//							"WHERE S.Priority=1 "+
//							"GROUP BY S.P_UserID"+
//							") >= 1 ) "+
					")");

				System.out.println("NEW TABLE AssistsS CREATED");
				
				//Assists for Well patient
				stmt.executeUpdate("CREATE TABLE AssistsW (" +
						"P_UserID VARCHAR2(20) NOT NULL, " +
						"S_UserID VARCHAR2(20) NOT NULL, " +
						"Since DATE NOT NULL, " +
						"Priority NUMBER(1) NOT NULL, " +
						"CONSTRAINT AW_PUID_Prioy_NOT_Unique PRIMARY KEY (P_UserID, Priority), " +
						"FOREIGN KEY (S_UserID) REFERENCES Supporter ON DELETE CASCADE, " +
						"FOREIGN KEY (P_UserID) REFERENCES Patient ON DELETE CASCADE, "+
						"CONSTRAINT AW_PUID_SUID_NOT_Unique UNIQUE (P_UserID, S_UserID), " +
						"CONSTRAINT AW_PUID_SUID_Should_diff CHECK (P_UserID <> S_UserID), "+
						"CONSTRAINT AW_Priority_Out_Of_Range CHECK ((Priority >= 0) OR (Priority <= 2))"+
						")");

					System.out.println("NEW TABLE AssistsW CREATED");
*/
				stmt.executeUpdate("CREATE TABLE PatientDisease (" +
					"UserID VARCHAR2(20) NOT NULL, " +
					"Did VARCHAR2(20) NOT NULL, " +
					"Since DATE NOT NULL, " +
					"CONSTRAINT PD_Did_UserId_NOT_Unique PRIMARY KEY (UserID, Did), " +
					"FOREIGN KEY (UserID) REFERENCES Patient ON DELETE CASCADE, " +
					"FOREIGN KEY (Did) REFERENCES Disease_DRecomm ON DELETE CASCADE)");

				System.out.println("NEW TABLE PatientDisease CREATED");


				// stmt.executeUpdate("DROP TABLE DoctorRecomm");

				stmt.executeUpdate("CREATE TABLE DoctorRecomm (" +
					"UserID VARCHAR2(20) NOT NULL, " +
					"Rid NUMBER(3) NOT NULL, " +
					// "Did VARCHAR2(20), " +
					"CONSTRAINT DR_User_Only_Has_One_DoctorRe UNIQUE (UserID), "+
					"CONSTRAINT DR_Did_UserId_NOT_Unique PRIMARY KEY (UserID, Rid), " +
					"FOREIGN KEY (UserID) REFERENCES Patient ON DELETE CASCADE, " +
					// "FOREIGN KEY (Did) REFERENCES Disease_DRecomm ON DELETE CASCADE, " +
					"FOREIGN KEY (Rid) REFERENCES Recommendation ON DELETE CASCADE)");

				System.out.println("NEW TABLE DoctorRecomm CREATED");

				
				/***************** These tables won't be used ******************/
/*
				stmt.executeUpdate("CREATE TABLE WellPatient (" +
					"UserID VARCHAR2(20), " + 
					"PRIMARY KEY (UserID), " + 
					"FOREIGN KEY (UserID) REFERENCES Patient ON DELETE CASCADE)");

				System.out.println("NEW TABLE WellPatient CREATED");

				
				// stmt.executeUpdate("DROP TABLE SickPatient");

				stmt.executeUpdate("CREATE TABLE SickPatient (" +
					"UserID VARCHAR2(20), " + 
					"PRIMARY KEY (UserID), " + 
					"FOREIGN KEY (UserID) REFERENCES Patient ON DELETE CASCADE)");

				System.out.println("NEW TABLE SickPatient CREATED");

				// stmt.executeUpdate("DROP TABLE AssistsW");

				stmt.executeUpdate("CREATE TABLE AssistsW (" +
					"S_UserID VARCHAR2(20), " +
					"W_UserID VARCHAR2(20), " +
					"Since DATE, " +
					"Priority NUMBER(1), " +
					"PRIMARY KEY (S_UserID, W_UserID), " +
					"FOREIGN KEY (S_UserID) REFERENCES Supporter ON DELETE CASCADE, " +
					"FOREIGN KEY (W_UserID) REFERENCES WellPatient ON DELETE CASCADE)");

				System.out.println("NEW TABLE AssistsW CREATED");

				
				// stmt.executeUpdate("DROP TABLE AssistsS");

				stmt.executeUpdate("CREATE TABLE AssistsS (" +
					"S_UserID VARCHAR2(20), " +
					"Sick_UserID VARCHAR2(20), " +
					"Since DATE, " +
					"Priority NUMBER(1), " +
					"PRIMARY KEY (S_UserID, Sick_UserID), " +
					"FOREIGN KEY (S_UserID) REFERENCES Supporter ON DELETE CASCADE, " +
					"FOREIGN KEY (Sick_UserID) REFERENCES SickPatient ON DELETE CASCADE)");

				System.out.println("NEW TABLE AssistsS CREATED");
				
				
				// stmt.executeUpdate("DROP TABLE SPatientDisease");

				stmt.executeUpdate("CREATE TABLE SPatientDisease (" +
					"UserID VARCHAR2(20), " +
					"Did VARCHAR2(20), " +
					"PRIMARY KEY (UserID, Did), " +
					"FOREIGN KEY (UserID) REFERENCES SickPatient ON DELETE CASCADE, " +
					"FOREIGN KEY (Did) REFERENCES Disease_DRecomm ON DELETE CASCADE)");

				System.out.println("NEW TABLE SPatientDisease CREATED");

*/
				/**********************************************************/
				//Create triggers (after all tables are created)
				try{
				stmt.executeUpdate("CREATE OR REPLACE TRIGGER CheckMobileIsNumber " +
			   			"BEFORE INSERT ON Supporter " +
//			   		"REFERENCING OLD AS old " + 
//			   			"NEW AS new "+
			   		"FOR EACH ROW " +
			   			"DECLARE "+
			   			"v_num NUMBER; "+
			   		"BEGIN " +
			   		"v_num := TO_NUMBER(:new.Mobile); "+
			   		"EXCEPTION " +
			   			"WHEN VALUE_ERROR THEN " +
						"RAISE_APPLICATION_ERROR(-20001, 'Your supporter Mobile included none numeric characters!' ); "+
						"END; ");
    		} catch (Throwable oops) {oops.printStackTrace();}

			System.out.println("NEW TRIGGER CheckMobileIsNumber CREATED");
                
                stmt.executeUpdate("CREATE OR REPLACE TRIGGER sickplus "+
                                   "AFTER INSERT ON PatientDisease "+
                                   "REFERENCING NEW AS n "+
                                   "FOR EACH ROW "+
                                   "DECLARE "+
                                   "sickcount NUMBER(1); "+
                                   "BEGIN "+
                                   
                                   "select sickornot into sickcount from Patient WHERE "+
                                   "Patient.UserID=:n.UserID; "+
                                   "sickcount := sickcount + 1; "+
                                   "dbms_output.put_line(sickcount); "+
                                   "dbms_output.put_line(:n.userid); "+
                                   "UPDATE Patient SET sickornot=sickcount WHERE Patient.UserID=:n.UserID; "+
                                   "END; ");
                System.out.println("TRIGGER CHANGE SICKORNOT increase CREATED!");
                
                stmt.executeUpdate("CREATE OR REPLACE TRIGGER sickminus "+
                                   "AFTER DELETE ON PatientDisease "+
                                   "REFERENCING OLD AS o "+
                                   "FOR EACH ROW "+
                                   "DECLARE "+
                                   "sickcount NUMBER(1); "+
                                   "BEGIN "+
                                   
                                   "select sickornot into sickcount from Patient WHERE "+
                                   "Patient.UserID=:o.UserID; "+
                                   "sickcount := sickcount - 1; "+
                                   "dbms_output.put_line(sickcount); "+
                                   "dbms_output.put_line(:o.userid); "+
                                   "UPDATE Patient SET sickornot=sickcount WHERE Patient.UserID=:o.UserID; "+
                                   "END; ");
                System.out.println("TRIGGER CHANGE SICKORNOT decrease CREATED!");
////
//			try{
//				stmt.executeUpdate("CREATE OR REPLACE TRIGGER Pri " +
//			   			"BEFORE INSERT ON Supporter " +
////			   		"REFERENCING OLD AS old " + 
////			   			"NEW AS new "+
//			   		"FOR EACH ROW " +
//			   			"DECLARE "+
//			   			"v_num NUMBER; "+
//			   		"BEGIN " +
//			   		"v_num := TO_NUMBER(:new.Mobile); "+
//			   		"EXCEPTION " +
//			   			"WHEN VALUE_ERROR THEN " +
//						"RAISE_APPLICATION_ERROR(-20001, 'Your supporter Mobile included none numeric characters!' ); "+
//						"END; ");
//    		} catch (Throwable oops) {oops.printStackTrace();}

//			System.out.println("NEW TRIGGER CheckMobileIsNumber CREATED");
				

    		} finally {
                close(rs);
                close(stmt);
                close(conn);
            }
        } catch(Throwable oops) {
            oops.printStackTrace();
        }
    }

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
}
