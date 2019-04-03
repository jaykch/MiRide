package app;

import components.Car;

public class MiRidesSystem {
	
	//Add a fleet array to store all cars for booking
	private Car[] fleet = new Car[100];

	public MiRidesSystem() {

	}

	public void getFleet() {
		for (int i = 0; i < fleet.length; i++) {
			if (fleet[i] != null) {
				System.out.println(fleet[i].getDetails());
			}
		}

	}

	public void addCarToFleet(Car car) {
		for (int i = 0; i < fleet.length; i++) {
			if (fleet[i] == null) {
				fleet[i] = car;
				break;
			}
		}
	}
}
