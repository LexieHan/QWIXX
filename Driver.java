// ------------------------------------------------------- 
// Assignment 4
// Written by: Xue Han 40063347
// For COMP 248 Section W – Winter 2018
// --------------------------------------------------------

// Program Author: Xue Han
// Date: April 12 2018
// Purpose of the Program: write a version of the dice game Qwixx that can be played by 2-5 players.

import java.util.Scanner;

public class Driver 
{

	public static void main(String[] args) 
	{
		
		int numofPlayers;
		boolean validNum = true;
		Scanner keyIn = new Scanner(System.in);	
		
		do	// make sure the the number of the players are between 2-5 inclusive.
		{
			System.out.print("Please enter the number of players(2-5):");
			numofPlayers = keyIn.nextInt();
			
			if (numofPlayers <2 || numofPlayers >5)
			{
				System.out.println("You must have between 2 and 5 players.");
				validNum = false;
			}
			
			else
				validNum = true;
			
		}while(validNum == false);		// repeat when the number is not valid
		
		Player [] gamer = new Player [numofPlayers];					
		
		for (int i=0; i<numofPlayers; i++)
		{
			System.out.print("Please enter the name of player" + (i+1) + ": ");
			gamer[i] = new Player (keyIn.next());	// initialize Player[], and save every player's name.
			gamer[i].printGameBoard();
		}
		
		
		 Qwixx newGame = new Qwixx (gamer);		// create a new game.
		
		 newGame.play();
		 
		keyIn.close();

	}

}
