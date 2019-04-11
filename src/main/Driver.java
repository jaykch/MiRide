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
		
		// Please do not press CTRL+SHIFT+F anywhere as it will reformat all the pseudo code
		
		/*
		* ALGORITHM
		* BEGIN
		* 	GET menu class
		* 		RUN menu
		* 	DISPLAY menu
		* END
		*/

		// Create Menu and run it

		Menu menu = new Menu();
		menu.run();
	}

}