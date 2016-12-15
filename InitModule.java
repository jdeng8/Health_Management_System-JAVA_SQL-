// import package for HashMap
import java.util.*;

public class InitModule
{
	// Variables
	public String state = "M_start";
	public String userID = null;
	public String pID = null;
	public String identity = null;
	public Map<String, Menu> menus = new HashMap<String, Menu>();
	public Map<String, Connect> connects = new HashMap<String, Connect>();

	// Constructors
	public InitModule() {
		initMenu();
		initConnect();
	}

	// Methods
	public void initMenu() {
		// init start menu
		this.menus.put("M_start", 
			new Menu(new String[] {
						"Log in", 
						"Sign up", 
						"Exit"},
					new String[] {
						"C_login", 
						"C_signup", 
						"C_exit"}));
		this.menus.get("M_start").name = "Start Page";
		this.menus.get("M_start").desc = 
			"Login or Sign up a new account to get access to our services!";

		// init home menu
		this.menus.put("M_home", 
			new Menu(new String[] {
						"Back", 
						"As a Patient", 
						"As a Supporter", 
						"Log Out"}, 
					new String[] {
						"M_start", 
						"C_is_patient", 
						"C_is_supporter", 
						"M_start"}));
		this.menus.get("M_home").name = "Home Page";
		this.menus.get("M_home").desc = 
			"Proceed as a Patient or a Supporter.";

		// init patient menu
		this.menus.put("M_patient", 
			new Menu(new String[] {
						"Back", 
						"Show My Profile", 
						"Edit My Profile", 
						"Show My Health Supporters", 
						"Edit My Health Supporters", 
						"Show Diagnosis for Me", 
						"Edit Diagnosis for Me", 
						"See Recommandations for Me", 
						"Show Observations", 
						"Type in New Observations", 
						"Show Active Alerts", 
						"Acknowledge and Deactivate Alerts", 
						"Log Out"}, 
					new String[] {
						"M_home",
						"C_P_show_profile",
						"C_P_edit_profile",
						"C_show_supporter",
						"C_edit_supporter",
						"C_show_diagnosis",
						"M_P_diagnosis",
						"C_show_recomm",
						"C_show_observ",
						"C_edit_observ",
						"C_show_alert",
						"C_ack_alert",						
						"M_start"}));
		this.menus.get("M_patient").name = "Patient Page";
		this.menus.get("M_patient").desc = 
			"Enjoy our care and services for you!";

		// init supporter menu
		this.menus.put("M_supporter", 
			new Menu(new String[] {
						"Back", 
						"Show My Profile", 
						"Edit My Profile", 
						"Show Patients under My Support", 
						"Log Out"}, 
					new String[] {
						"M_home",
						"C_Sup_show_profile",
						"C_Sup_edit_profile",
						"C_Sup_patient_list",
						"M_start"}));
		this.menus.get("M_supporter").name = "Supporter Page";
		this.menus.get("M_supporter").desc = 
			"Be Responsible as a Health Supporter!";

		// init individual patient under support menu
		this.menus.put("M_individual", 
			new Menu(new String[] {
						"Back", 
						"Show Recommandations for This Patient", 
						"Edit Recommandations for This Patient", 
						"Type in New Observations for This Patient",
						"Show Active Alerts for This Patient", 
						"Acknowledge and Deactivate Alerts", 
						"Log Out"}, 
					new String[] {
						"M_supporter", 
						"C_show_recomm",
						"C_sup_edit_doc_recom",
						"C_edit_observ",
						"C_show_alert",
						"C_ack_alert",
						"M_start"}));
		this.menus.get("M_individual").name = 
			"Individual Patient under My Support Page";
		this.menus.get("M_individual").desc = 
			"Show special care to someone special!";

		// init patient diagnosis menu
		this.menus.put("M_P_diagnosis", 
			new Menu(new String[] {
						"Back", 
						"Add Diagnosis", 
						"Delete Diagnosis",
						"Log Out"}, 
					new String[] {
						"M_patient", 
						"C_add_diagnosis", 
						"C_delete_diagnosis", 
						"M_start"}));
		this.menus.get("M_P_diagnosis").name = "Diagnosis Page";
		this.menus.get("M_P_diagnosis").desc =
			"Here are the diagnosis for you.";

		// init individual patient diagnosis under support menu
		this.menus.put("M_Sup_diagnosis", 
			new Menu(new String[] {
						"Back", 
						"Add Diagnosis", 
						"Delete Diagnosis",
						"Log Out"}, 
					new String[] {
						"M_supporter", 
						"C_add_diagnosis", 
						"C_delete_diagnosis", 
						"M_start"}));
		this.menus.get("M_Sup_diagnosis").name = "Diagnosis Page";
		this.menus.get("M_P_diagnosis").desc =
			"Here are the diagnosis for the patient.";
	}

	public void initConnect() {
		// init login connect
		this.connects.put("C_login",
			new Connect(new String[] {
							"UserID", 
							"Password"}));
		this.connects.get("C_login").name = "Login Page";
		this.connects.get("C_login").desc = 
			"Type in your UserID and Password to access your information!";

		// init sign up connect
		this.connects.put("C_signup",
			new Connect(new String[] {
							"UserID", 
							"Password"}));
		this.connects.get("C_signup").name = "Signup Page";
		this.connects.get("C_signup").desc = 
			"Sign up an account for the benifit of your personal health!";

		// // init exit connect
		// this.connects.put("C_exit",
		// 	new Connect(new String[] {}));

		// init decide whether user is a patient connect
		this.connects.put("C_is_patient",
			new Connect(new String[] {}));

		// init decide whether user is a supporter connect
		this.connects.put("C_is_supporter",
			new Connect(new String[] {}));

		// init show patient profile connect
		this.connects.put("C_P_show_profile",
			new Connect(new String[] {}));
		this.connects.get("C_P_show_profile").name = "Show Patient Profile Page";
		this.connects.get("C_P_show_profile").desc = "Here's your profile!";

		// init edit patient profile connect
		this.connects.put("C_P_edit_profile",
			new Connect(new String[] {
							"Name", 
							"D.O.B.(mm/dd/yyyy)", 
							"Gender", 
							"Address"}));
		this.connects.get("C_P_edit_profile").name = "Edit Patient Profile Page";
		this.connects.get("C_P_edit_profile").desc = 
			"Follow instructions to type in basic profile information!";

		// init show supporter profile connect
		this.connects.put("C_Sup_show_profile",
			new Connect(new String[] {}));
		this.connects.get("C_Sup_show_profile").name = "Show Supporter Profile Page";
		this.connects.get("C_Sup_show_profile").desc = "Here's your profile!";

		// init edit supporter profile connect
		this.connects.put("C_Sup_edit_profile",
			new Connect(new String[] {
							"Name", 
							"Mobile"}));
		this.connects.get("C_Sup_edit_profile").name = "Edit Supporter Profile Page";
		this.connects.get("C_Sup_edit_profile").desc = 
			"Follow instructions to type in basic profile information!";

		// init patient list under support connect
		this.connects.put("C_Sup_patient_list",
			new Connect(new String[] {}));
		this.connects.get("C_Sup_patient_list").name = 
			"List of Patient Under My Support Page";
		this.connects.get("C_Sup_patient_list").desc = 
			"Here are the patients under your support.";

		// init show my supporters connect
		this.connects.put("C_show_supporter",
			new Connect(new String[] {}));
		this.connects.get("C_show_supporter").name = "Show Supporters Page";
		this.connects.get("C_show_supporter").desc = 
			"Here are the health supporters for you.";

		// init edit my supporters connect
		this.connects.put("C_edit_supporter",
			new Connect(new String[] {
							"Primary Supporter UserID", 
							"Effective Date1(mm/dd/yyyy)", 
							"Secondary Supporter UserID",
							"Effective Date2(mm/dd/yyyy)"}));
		this.connects.get("C_edit_supporter").name = "Edit Supporters Page";
		this.connects.get("C_edit_supporter").desc = 
			"Follow the instructions to edit your health supporters.";

		// init show diagnosis connect
		this.connects.put("C_show_diagnosis",
			new Connect(new String[] {}));
		this.connects.get("C_show_diagnosis").name = "Show Diagnosis Page";
		this.connects.get("C_show_diagnosis").desc = 
			"Here are the diagnosis for you.";

		// init add diagnosis connect
		this.connects.put("C_add_diagnosis",
			new Connect(new String[] {
							"Disease ID", 
							"Diagnose Time"}));
		this.connects.get("C_add_diagnosis").name = "Add Diagnosis Page";
		this.connects.get("C_add_diagnosis").desc = 
			"Type in disease ID to add a new diagnosis";

		// init delete diagnosis connect
		this.connects.put("C_delete_diagnosis",
			new Connect(new String[] {"Disease ID"}));
		this.connects.get("C_delete_diagnosis").name = "Delete Diagnosis Page";
		this.connects.get("C_delete_diagnosis").desc = 
			"Type in disease ID to delete the diagnosis";

		// init show recommendations for me/patient connect
		this.connects.put("C_show_recomm",
			new Connect(new String[] {}));
		this.connects.get("C_show_recomm").name = "Show Recommandations Page";
		this.connects.get("C_show_recomm").desc = 
			"Here are the recommendations for you.";

		// init show observations for me/patient connect
		this.connects.put("C_show_observ",
			new Connect(new String[] {}));
		this.connects.get("C_show_observ").name = "Show Observations Page";
		this.connects.get("C_show_observ").desc = 
			"Here are the observations for you.";

		// init edit my/patient's observations connect
		this.connects.put("C_edit_observ",
			new Connect(new String[] {}));
		this.connects.get("C_edit_observ").name = "Edit Observations Page";
		this.connects.get("C_edit_observ").desc = 
			"Follow the instructions to type in new observations.";

		// init show alerts for me/patient connect
		this.connects.put("C_show_alert",
			new Connect(new String[] {}));
		this.connects.get("C_show_alert").name = "Show Alerts Page";
		this.connects.get("C_show_alert").desc = 
			"Be careful with these alerts!";

		// init acknoeledge alerts connect
		this.connects.put("C_ack_alert",
			new Connect(new String[] {}));
		this.connects.get("C_ack_alert").name = "Acknowledge Recommandations Page";
		this.connects.get("C_ack_alert").desc = 
			"These alerts have been deactivated.";

		// init supporter edit doctor recommendation connect
		this.connects.put("C_sup_edit_doc_recom",
			new Connect(new String[] {
							"Weight_bool",
							"Wfreq",
							"Wuplimit_lb",
							"Wlowlimit_lb",
							"BP_bool",
							"BPfreq",
							"BPSuplimit_mmHg",
							"BPSlowlimit_mmHg",
							"BPDuplimit_mmHg",
							"BPDlowlimit_mmHg",
							"Pain_bool",
							"Pfreq",
							"Pvalue",
							"Mood_bool",
							"Mfreq",
							"Mvalue",
							"Oxy_bool",
							"Ofreq",
							"Ouplimit_pct",
							"Olowlimit_pct",
							"Temp_bool",
							"Tfreq",
							"Tuplimit_F",
							"Tlowlimit_F"}));
	}
}