package userInterface;

public class Menu {

	public Menu() {

	}

	public void run() {
		// Use printF to format the menu
		System.out.println("*** MiRides System Menu ***\n");
		System.out.printf("%s			%S\n", "Create Car", "CC");
		System.out.printf("%s			%S\n", "Book Car", "BC");
		System.out.printf("%s		%S\n", "Complete Booking", "CB");
		System.out.printf("%s		%S\n", "Display All Cars", "DA");
		System.out.printf("%s		%S\n", "Search Specific Car", "SS");
		System.out.printf("%s		%S\n", "Search Available Cars", "SA");
		System.out.printf("%s			%S\n", "Seed Data", "SD");
		System.out.printf("%s			%S\n", "Exit Program", "EX");
		
		
	}
}
