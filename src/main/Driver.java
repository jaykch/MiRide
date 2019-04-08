/*
* Class: 		Driver
* Description: 	The class represents the initial entry point and 
* 				driver of the system.
* Author: 		Jay Kumar - S3770282
*/

package main;

import userInterface.Menu;

public class Driver {

	public static void main(String[] args) {

		// Create Menu and run it

		Menu menu = new Menu();
		menu.run();
	}

}