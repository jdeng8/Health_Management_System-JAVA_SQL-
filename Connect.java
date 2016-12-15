// import Scanner class and HashMap
import java.util.*;

public class Connect
{	
	// Instance variables	
	public String name = null;
	public String desc = null;
	private String s1 = "============================================================";
	private String s2 = "------------------------------------------------------------";
	private int plen = 25;
	public String[] prompt = new String[plen];
	public String[] data = new String[plen];
	private Map<String, String> input = new HashMap<String, String>();
	private int jump = -1;

	// Constructors
	public Connect() {
		
	}

	public Connect(String[]p) {
		// get prompts
		for (int i=0; i < p.length; i++) {
			this.prompt[i] = p[i];
		}
	}
	
	// Methods
	public void header() {
		// print header
		System.out.println(s1);
		System.out.println(this.name);
		System.out.println(this.desc);
		System.out.println(s2);		
	}

	public void clear() {
		for (int i=0; i < this.plen; i++) {
			this.prompt[i] = null;
			this.data[i] = null;
			// this.input.clear();
		}
	}

	public Map<String, String> get() {
		// create a scanner so we can read the command-line input
		Scanner scanner = new Scanner(System.in);

		// loop over prompt and get data from scanner
		for (int i=0; this.prompt[i] != null; i++) {
				// prompt for input
				System.out.print(String.format("%s: ", this.prompt[i]));
				// get data from scanner
				this.data[i] = scanner.nextLine();
				this.input.put(prompt[i],data[i]);
		}
		//this.clear();
		return this.input;		
	}
}
