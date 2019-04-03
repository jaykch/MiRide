package main;
import app.MiRidesSystem;
import userInterface.Menu;
import components.Car;

public class Driver {

	public static void main(String[] args) {
		// TODO Code car class
		// TODO Code booking class
		// TODO Code user class
		// TODO Import Utilities
			
		Menu menu = new Menu();
		menu.run();
		
		//-------Seed data---------------->

		Car beetle =  new Car("STD999","Volkswagen","Beetle", "Justin Beiber",2);
		Car mustang =  new Car("STD199","Ford","Mustang", "Justin Beiber",2);
		Car ferrari =  new Car("STD199","ferarri","458", "Justin Beiber",2);

		
		MiRidesSystem system = new MiRidesSystem();
		
		system.addCarToFleet(beetle);
		system.addCarToFleet(mustang);
		system.addCarToFleet(ferrari);

		//system.getFleet();
		
		beetle.book("Anthony Testing", "Hpkins hpkins", 5);
		try {

			  if( System.getProperty( "os.name" ).startsWith( "Window" ) ) {
			     Runtime.getRuntime().exec("cls");
			  } else {
			     Runtime.getRuntime().exec("clear");
			  }


			} catch ( e) {

			  for(int i = 0; i < 1000; i++) {
			    System.out.println();
			  }

			}
	}

}