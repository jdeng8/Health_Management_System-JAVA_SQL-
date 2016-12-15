import java.util.*;

public class WolfHealth 
{
	public static void main (String[] args) {
		String star = "*";
		String header =	"\n\n" + "*************************************************************\n\n" +
						"     Welcome to WolfHealth, your personal health manager!" +
						"\n\n" + "*************************************************************\n\n";
		System.out.print(header);
		QueriesV2 qs = new QueriesV2();
		Query q = new Query();
		InitModule modules = new InitModule();
		Menu m = modules.menus.get(modules.state);
		Connect c = new Connect();
		String s = new String();
		Map<String, String> input = new HashMap<String, String>();	
		while (m != null) {
			s = m.state[m.get()];
			c = modules.connects.get(s);
			switch (modules.state) {
				case "M_start"	:							
						switch (s) {
							case "C_login"	:	
									c.header();
									q = qs.login(c.get());
									//c.clear();
									System.out.println(q.message);
									if (q.status == 1) {
										modules.state = "M_home";
										modules.userID = c.data[0];
									} else {
										modules.state = "M_start";
									}
									break;
							case "C_signup"	:
									c.header();
									q = qs.signup(c.get());
									//c.clear();
									System.out.println(q.message);
									modules.state = "M_start";
									break;
							case "C_exit"	:	
									return;
							default		:	
									modules.state = "M_start";
						}
						break;

				case "M_home"	:	
						switch (s) {
							case "C_is_patient"	:
									input = c.get();
									//c.clear();
									input.put("UserID", modules.userID);
									q = qs.choosePatient(input);
									input.clear();
									System.out.println(q.message);
									System.out.println(q.status);
									if (q.status == 0) {
										modules.identity = "WellPatient";
										c = modules.connects.get("C_P_edit_profile");
										c.header();
										input = c.get();
										input.put("UserID", modules.userID);
										input.put("identity", modules.identity);
										q = qs.saveProfile(input);
										input.clear();
										System.out.println(q.message);
										modules.state = "M_patient";
									} else if (q.status == 1) {
										modules.identity = "WellPatient";
										modules.state = "M_patient";
									} else if (q.status == 2) {
										modules.identity = "SickPatient";
										modules.state = "M_patient";
									} else if (q.status == 3) {
										modules.identity = "SickPatient";
										c = modules.connects.get("C_edit_supporter");
										c.header();	
										input = c.get();
										input.put("UserID", modules.userID);
										input.put("identity", modules.identity);
										q = qs.saveSupporters(input);
										input.clear();
										// q.printH();
										System.out.println(q.message);
										modules.state = "M_home";
									}else {
										modules.state = "M_start";
									}
									break; 
							case "C_is_supporter"	:	
									input = c.get();
									input.put("UserID", modules.userID);
									q = qs.chooseSupporter(input);
									input.clear();
									System.out.println(q.message);
									modules.identity = "Supporter";
									if (q.status == 0) {
										c = modules.connects.get("C_Sup_edit_profile");
										c.header();
										input = c.get();
										input.put("UserID", modules.userID);
										input.put("identity", modules.identity);
										q = qs.saveProfile(input);
										input.clear();
										System.out.println(q.message);
										modules.state = "M_supporter";
									} else if (q.status == 1) {
										modules.state = "M_supporter";
									} else {
										modules.state = "M_start";
									}
									break;
							case "M_start"	:
							default	:
									modules.state = "M_start";
						}
						break;

				case "M_patient"	:	
						switch (s) {
							case "M_home"	:
									modules.state = "M_home";
									break;
							case "C_P_show_profile"	:
									c.header();	
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.getProfile(input);
									input.clear();
									q.printV();
									System.out.println(q.message);
									break;
							case "C_P_edit_profile"	:	
									c.header();	
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.saveProfile(input);
									input.clear();
									System.out.println(q.message);
									break;
							case "C_show_supporter"	:	
									c.header();	
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.getSupporters(input);
									input.clear();
									q.printH();
									System.out.println(q.message);
									break;
							case "C_edit_supporter"	:	
									c.header();	
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.saveSupporters(input);
									input.clear();
									// q.printH();
									System.out.println(q.message);
									break;
							case "C_show_diagnosis"	:	
									c.header();
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.getDiagnosis(input);
									input.clear();
									q.printH();
									System.out.println(q.message);
									break;
							case "M_P_diagnosis"	:
									modules.state = "M_P_diagnosis";
									break;
							case "C_show_recomm"	:	
									c.header();
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.getRecomm(input);
									input.clear();
									q.printV();
									System.out.println(q.message);
									break;
							case "C_show_observ"	:	
									c.header();
									//c.clear();
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.getObservations(input);
									input.clear();
									q.printH();
									System.out.println(q.message);
									break;
							case "C_edit_observ"	:	
									c.header();
									//c.clear();
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									// get recommeded columns and put it to pormpt
									q = qs.getRecommendedColumns(input);
									input.clear();
									//c.clear();
									int j = 0;
									c.prompt[j++] = "ObserveTime";
									c.prompt[j++] = "RecTime";
									for (int i=0; i < q.schema.size(); i++) {
										c.prompt[j++] = q.schema.get(i);
									}									
									input = c.get();
									input.put("UserID", modules.userID);
									// input.put("identity", modules.identity);
									// for(String key : input.keySet()) {
									// 	System.out.println("key: " + key + "values: " + input.get(key));
									// }
									// System.out.println("I'm here!\n");
									q = qs.insertObservations(input);
									input.clear();
									System.out.println(q.message);
									break;
							case "C_show_alert"		:	
									c.header();
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.genAndShowAlert(input);
									input.clear();
									q.printH();
									System.out.println(q.message);
									break;
							case "C_ack_alert"		:	
									c.header();
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.ackAlert(input);
									input.clear();
									// q.printH();
									System.out.println(q.message);
									break;					
							case "M_start"	:
							default	:
									modules.state = "M_start";
						}
						break;

				case "M_supporter"	:	
						switch (s) {
							case "M_home"	:	
									modules.state = "M_home";
									break;
							case "C_Sup_show_profile"	:	
									c.header();
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.getProfile(input);
									input.clear();
									q.printH();
									System.out.println(q.message);
									break;
							case "C_Sup_edit_profile"	:	
									c.header();
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.saveProfile(input);
									input.clear();
									System.out.println(q.message);
									break;
							case "C_Sup_patient_list"	:	
									c.header();
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.getSupportedPatients(input);
									input.clear();
									q.printH();
									//c.clear();
									c.prompt[0] = "PatientID";
									input.clear();
									input = c.get();
									input.put("UserID", modules.userID);
									modules.pID = input.get("PatientID");
									System.out.println(modules.pID);
									// input.put("PatientID", modules.pID);
									q = qs.getSupportedPatientsInfo(input);
									input.clear();
									System.out.println(q.message);
									if (q.status == 1) {
										modules.state = "M_individual";
									}
									break;
							case "M_start"	:	
							default	:
									modules.state = "M_start";
						}					
						break;

				case "M_individual"	:
						switch (s) {
							case "M_supporter"	:	
									modules.state = "M_supporter";
									break;
							case "C_show_recomm"	:	
									c.header();
									input = c.get();
									input.put("UserID", modules.pID);
									input.put("identity", modules.identity);
									q = qs.getRecomm(input);
									input.clear();
									q.printH();
									System.out.println(q.message);
									break;
							case "C_sup_edit_doc_recom"	:	
									c.header();
									input = c.get();
									input.put("UserID", modules.pID);
									// input.put("identity", modules.identity);
									q = qs.saveRecomm(input);
									input.clear();
									System.out.println(q.message);
									break;
							case "C_edit_observ"	:	
//									c.header();
//									input = c.get();
//									input.put("UserID", modules.pID);
//									input.put("identity", modules.identity);
//									// get recommeded columns and put it to pormpt
//									q = qs.getRecommendedColumns(input);
//									input.clear();
//									for (int i = 0; i < q.schema.size(); i++) {
//										c.data[i] = q.schema.get(i);
//									}
//									input = c.get();
//									input.put("UserID", modules.pID);
//									input.put("identity", modules.identity);
//									q = qs.insertObservations(input);
//									input.clear();
//									System.out.println(q.message);


									c.header();
									//c.clear();
									input = c.get();
									input.put("UserID", modules.pID);
									input.put("identity", modules.identity);
									// get recommeded columns and put it to pormpt
									q = qs.getRecommendedColumns(input);
									input.clear();
									//c.clear();
									int j = 0;
									c.prompt[j++] = "ObserveTime";
									c.prompt[j++] = "RecTime";
									for (int i=0; i < q.schema.size(); i++) {
										c.prompt[j++] = q.schema.get(i);
									}									
									input = c.get();
									input.put("UserID", modules.pID);
									// input.put("identity", modules.identity);
									// for(String key : input.keySet()) {
									// 	System.out.println("key: " + key + "values: " + input.get(key));
									// }
									// System.out.println("I'm here!\n");
									q = qs.insertObservations(input);
									input.clear();
									System.out.println(q.message);
									break;
							case "C_show_alert"		:	
									c.header();
									input = c.get();
									input.put("UserID", modules.pID);
									input.put("identity", modules.identity);
									q = qs.genAndShowAlert(input);
									input.clear();
									q.printH();
									System.out.println(q.message);
									break;
							case "C_ack_alert"		:	
									c.header();
									input = c.get();
									input.put("UserID", modules.pID);
									input.put("identity", modules.identity);
									q = qs.ackAlert(input);
									input.clear();
									// q.printH();
									System.out.println(q.message);
									break;	
							case "M_start"	:	
							default	:
									modules.state = "M_start";						
						}	
						break;

				case "M_P_diagnosis"	:	
						switch (s) {
							case "M_patient"	:	
									modules.state = "M_patient";
									break;
							case "C_add_diagnosis"	:	
									c.header();
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.addDiagnosis(input);
									input.clear();
									// q.printH();
									System.out.println(q.message);
									break;
							case "C_delete_diagnosis"	:	
									c.header();
									input = c.get();
									input.put("UserID", modules.userID);
									input.put("identity", modules.identity);
									q = qs.deleteDiagnosis(input);
									input.clear();
									// q.printH();
									System.out.println(q.message);
									break;
							case "M_start"	:	
							default	:	
									modules.state = "M_start";
						}
						break;

				case "M_Sup_diagnosis"	:	
						switch (s)	{
							case "M_supporter"	:	
									modules.state = "M_supporter";
									break;
							case "C_add_diagnosis"	:	
									c.header();
									input = c.get();
									input.put("UserID", modules.pID);
									input.put("identity", modules.identity);
									q = qs.addDiagnosis(input);
									input.clear();
									// q.printH();
									System.out.println(q.message);
									break;
							case "C_delete_diagnosis"	:	
									c.header();
									input = c.get();
									input.put("UserID", modules.pID);
									input.put("identity", modules.identity);
									q = qs.deleteDiagnosis(input);
									input.clear();
									// q.printH();
									System.out.println(q.message); 
									break;
							case "M_start"	:	
							default	:	
									modules.state = "M_start";
						}
						break;

				// modules.state = m.state[m.get()];			
			}
			m = modules.menus.get(modules.state);
		}
	}
}