package main;

import app.MiRidesSystem;
import userInterface.Menu;
import components.Car;
import components.Booking;

public class Driver {

	public static void main(String[] args) {

		Menu menu = new Menu();
		menu.run();

		// -------Seed data---------------->
		
		MiRidesSystem system = new MiRidesSystem();


//		Car beetle = new Car("STD999", "Volkswagen", "Beetle", "Justin Beiber", 2);
//		Car mustang = new Car("STD199", "Ford", "Mustang", "Justin Beiber", 2);
//		Car ferrari = new Car("STD199", "ferarri", "458", "Justin Beiber", 2);
//
//		system.addCarToFleet(beetle);
//		system.addCarToFleet(mustang);
//		system.addCarToFleet(ferrari);
//
////		system.getFleetDetails();
//
//		beetle.book("Anthony", "Hopkins", 5);
//		beetle.book("Brad", "Pitt", 5);
//		beetle.book("Brad1", "Pitt", 5);
//		beetle.book("Brad2", "Pitt", 5);
//		beetle.book("Brad3", "Pitt", 5);
//		
//		beetle.book.completeBooking(10);
//
//		
//		Booking[] bookings =  beetle.getCurrentBookings();
//		Booking[] pastBookings =  beetle.getPastBookings();
//		
//		System.out.println(bookings[1].getDetails());
//		System.out.println(pastBookings[0].getDetails());

	}

}