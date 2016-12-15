
// import java.sql.*;
import java.util.*;


public class Query {
	// variabls
	public int status;
	public String message;
	public ArrayList<String> schema = new ArrayList<String>();
	public ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();

	// constructors
	public Query() {

	}

	// methods
	public void printM() {
		System.out.print(this.message + "\n");
	}

	public void printH() {
		for (int col = 0; col < this.schema.size(); col++) {
			System.out.print(this.schema.get(col) + "\t\t");
		}
		System.out.print("\n");
		for (int row = 0; row < this.results.size(); row++) {
			for (int col = 0; col < this.schema.size(); col++) {
				System.out.print(this.results.get(row).get(col) + "\t\t");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	public void printV() {
		for (int col = 0; col < this.schema.size(); col++) {
			System.out.print(this.schema.get(col) + "\t\t");
			for (int row = 0; row < this.results.size(); row++) {
				System.out.print(this.results.get(row).get(col) + "\t\t");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
}



