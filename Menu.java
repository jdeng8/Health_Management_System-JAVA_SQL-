// import Scanner class
import java.util.Scanner;

public class Menu
{	
	// Instance variables
	public String name = "myname";
	public String desc = "description";
	private String s1 = "============================================================";
	private String s2 = "------------------------------------------------------------";
	private int mlen = 15;
	private String[] menu = new String[mlen];
	public String [] state = new String[mlen];

	// Constructors
	public Menu() {
		
	}
	
	public Menu(String[] m, String[] s) {
		if (m.length != s.length) {
			System.out.println("A mismatch has occured!");
			//this();
		} else {
			for (int i=0; i < m.length; i++) {
				this.menu[i] = m[i];
				this.state[i] = s[i];
			}
		}
	}
	
	// Methods
	public int get() {
		// create a scanner so we can read the command-line input
		Scanner scanner = new Scanner(System.in);

		// print header
		System.out.println(s1);
		System.out.println(this.name);
		System.out.println(this.desc);
		System.out.println(s2);

		// loop over menu
		for (int i=0; menu[i] != null; i++) {
			System.out.println(String.format("%d. %s", i, this.menu[i]));
		}
		// get user choice
		System.out.print("Please enter your choice here: ");

		// return user choice
		return Integer.parseInt(scanner.next());	
	}
}
