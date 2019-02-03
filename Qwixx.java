import java.util.Scanner;

public class Qwixx 
{
	private Dice[] dice;			// an array to store all the Dice[]
	private Player[] player;		// an array to store all the Player[]
	
	// record whether the row is locked. Initialize them to "false" when game start
	private static boolean lockedR = false, lockedY = false, lockedG = false, lockedB = false;  
	public static final int NEGPTS = -5;		//get negative points when player pass a coloured round.		
	
	public Qwixx(Player[] gamer)				// constructor
	{
		player = gamer;								
		
		dice = new Dice[6];
		dice[0] = new Dice("Red");
		dice[1] = new Dice("Yellow");
		dice[2] = new Dice("Green");
		dice[3] = new Dice("Blue");
		dice[4] = new Dice("White1");
		dice[5] = new Dice("White2");
		
	}
	
	
	public void play()
	{
		do
		{
			for(int k=0; k<player.length;k++)	
			{
				System.out.println("---- New Round ----");
			
				rollDice();
				printRolledDice();
				playWhiteDiceMove();				// every player has the chance to use the white dice total every round
				playColourDiceMove(player[k]);	// only one player has the chance to play the coloured round.
			}
		
		}while (!checkGameFinished());	// continue the game until it's finished.
	}
	
	// rollDice()
	public void rollDice()
	{
		for (int i=0; i<6; i++)
			dice[i].rollDice();							
	}
	
	// printRolledDice()
	public void printRolledDice()
	{	
		for(int i=0; i<6; i++)
			System.out.print(dice[i]); 							
		System.out.println();
	}
	
	// white round
	public void playWhiteDiceMove()
	{
		for(int i=0; i<player.length; i++)
		{
			Scanner keyIn = new Scanner(System.in);	
			System.out.println("\n***** Move on white dice *****");
			System.out.println("The total for the white dice is " + getWhiteDiceTotal() + "\n");					
			
			System.out.println(player[i].getName() + " it's your turn...");
			player[i].printGameBoard();
			
			String whetherUseWhiteDice;
			boolean validMove=false;
			boolean colourFinished=false;
			
			do
			{
				System.out.print("Would you like to cross off a number on the game board using the white dice total?"
					           + "(anything other than 'yes' is taken to mean no): ");
				whetherUseWhiteDice = keyIn.next();
				
				if (whetherUseWhiteDice.equals("yes"))
				{
					System.out.print("What colour would you like to cross out?(R=red, Y=yellow, G=green, B=blue): ");
					String color = keyIn.next();							
					Move m = new Move(color.charAt(0), getWhiteDiceTotal());
					
					validMove = checkValidMove(player[i], m);
					
					if (validMove)													
					{
						player[i].makeMove(m);		// make the move when the intended move is valid
						player[i].printGameBoard();
						
						colourFinished = checkColourFinished(player[i], color.charAt(0));
						
						if(colourFinished)
						{
							if(color.equals("R"))
								System.out.println("Red is no longer playable. Player " + player[i].getName() + " has locked it.");
							if(color.equals("Y"))
								System.out.println("Yellow is no longer playable. Player " + player[i].getName() + " has locked it.");
							if(color.equals("G"))
								System.out.println("Green is no longer playable. Player " + player[i].getName() + " has locked it.");
							if(color.equals("B"))
								System.out.println("Blue is no longer playable. Player " + player[i].getName() + " has locked it.");
						
							if(checkGameFinished())									// check if the game is finished when a colour has just been locked.
							{
								determineWinner(player);
								System.exit(0);										// end the program
							}
						} 
					
					}				
				
				}
				
			}while(whetherUseWhiteDice.equals("yes") && (!validMove));			// repeat when the intended move is invalid and the player doesn't want to pass the round.				
			
			
		}
		

	}
		// helper: getWhiteDiceTotal()
		private int getWhiteDiceTotal()
		{
			return (dice[4].getCurrentSide() + dice[5].getCurrentSide());
		}
		
		// helper: checkValidMove(Player p, Move m)
		
		private static boolean checkValidMove(Player p, Move m)
		{
			if ( Move.convertColourtoNum(m.getColour()) ==0 )		// find out the colour that player intends to cross off 
			{
				if(lockedR)		// check whether the color has been locked
				{
					System.out.println("Can't move on Red, it's locked.");
					return false;
				}
				else if(m.getNumber()<=p.getCrossedR())	// check if the number intended to cross off is on the right side of the last number crossed on the same row
				{
					System.out.println("Invalid move. " + p.getCrossedR() + " is already crossed off in R");
					return false;
				}

				else
					return true;
			}
			
			if ( Move.convertColourtoNum(m.getColour()) ==1 )
			{	
				if(lockedY)
				{
					System.out.println("Can't move on Yellow, it's locked.");
					return false;
				}
				else if(m.getNumber()<=p.getCrossedY())
				{
					System.out.println("Invalid move. " + p.getCrossedY() + " is already crossed off in Y");
					return false;
				}

				else
					return true;
			}
			
			if ( Move.convertColourtoNum(m.getColour()) ==2 )
			{	
				if(lockedG)
				{
					System.out.println("Can't move on Green, it's locked.");
					return false;
				}
				else if(m.getNumber()>=p.getCrossedG())
				{
					System.out.println("Invalid move. " + p.getCrossedG() + " is already crossed off in G");
					return false;
				}

				else
					return true;
			}
			
			if ( Move.convertColourtoNum(m.getColour()) ==3 )
			{
				if(lockedB)
				{
					System.out.println("Can't move on Blue, it's locked.");
					return false;
				}
				else if(m.getNumber()>=p.getCrossedB())
				{
					System.out.println("Invalid move. " + p.getCrossedB() + " is already crossed off in B");
					return false;
				}

				else
					return true;
			}
			
			return false;																	
			
		}
		
		// helper
		private static boolean checkColourFinished(Player p, char colour)	
		{	
			
			if (colour == 'R')
				return lockedR = (p.getCrossedR() == 12);	// lock the red row when 12 has been just crossed off
			if (colour == 'Y')
				return lockedY = (p.getCrossedY() == 12);	// lock the yellow row when 12 has just been crossed off
			if (colour == 'G')
				return lockedG = (p.getCrossedG() == 2);		// lock the green row when 2 has just been crossed off
			if (colour == 'B')
				return lockedB = (p.getCrossedB() == 2);		// lock the blue row when 2 has just been crossed off
			
			return false;							
						
		}
		
		// helper
		private boolean checkGameFinished()
		{
			for (int i=0; i<player.length; i++)
			{
				if (player[i].getNegativePoints()== -20)		// check whether a player's total negative points has achieved -20.
					return true;
			}
			
			return ((lockedR && lockedY )|| (lockedR && lockedG) || (lockedR && lockedB) || (lockedY && lockedG) || (lockedY && lockedB) || (lockedG && lockedB));
			// the game finish when any two of the colours are locked.
		}
	
	// lists all the players' total points when game is over and find out the winner. Seems not a good algorithm, but it works! :)
	public static void determineWinner(Player[] p)
	{
		for(int i=0; i<p.length; i++)
			System.out.println(p[i].getName() + " has a total of: " + p[i].getBoardTotalMethod() + " points.");
		
		if(p.length==2)		// when there are 2 players
		{	
			if(p[0].getBoardTotalMethod()== Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[0].getName() + " wins the game!");
			if(p[1].getBoardTotalMethod()== Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[1].getName() + " wins the game!");
		}
		if(p.length==3)		// when there are 3 players
		{
			if(p[0].getBoardTotalMethod()== Math.max(Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()),p[2].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[0].getName() + " wins the game!");
			if(p[1].getBoardTotalMethod()== Math.max(Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()),p[2].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[1].getName() + " wins the game!");
			if(p[2].getBoardTotalMethod()== Math.max(Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()),p[2].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[2].getName() + " wins the game!");
		}	
		if(p.length==4)		// when there are 4 players
		{
			if(p[0].getBoardTotalMethod()== Math.max(Math.max(Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()),p[2].getBoardTotalMethod()),p[3].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[0].getName() + " wins the game!");
			if(p[1].getBoardTotalMethod()== Math.max(Math.max(Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()),p[2].getBoardTotalMethod()),p[3].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[1].getName() + " wins the game!");
			if(p[2].getBoardTotalMethod()== Math.max(Math.max(Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()),p[2].getBoardTotalMethod()),p[3].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[2].getName() + " wins the game!");
			if(p[3].getBoardTotalMethod()== Math.max(Math.max(Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()),p[2].getBoardTotalMethod()),p[3].getBoardTotalMethod()))
				System.out.println("That's all folks!" +  p[3].getName() + " wins the game!");
		}
		if(p.length==5)		// when there are 2 players
		{
			if(p[0].getBoardTotalMethod()== Math.max(Math.max(Math.max(Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()),p[2].getBoardTotalMethod()),p[3].getBoardTotalMethod()),p[4].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[0].getName() + " wins the game!");
			if(p[1].getBoardTotalMethod()== Math.max(Math.max(Math.max(Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()),p[2].getBoardTotalMethod()),p[3].getBoardTotalMethod()),p[4].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[1].getName() + " wins the game!");
			if(p[2].getBoardTotalMethod()== Math.max(Math.max(Math.max(Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()),p[2].getBoardTotalMethod()),p[3].getBoardTotalMethod()),p[4].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[2].getName() + " wins the game!");
			if(p[3].getBoardTotalMethod()== Math.max(Math.max(Math.max(Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()),p[2].getBoardTotalMethod()),p[3].getBoardTotalMethod()),p[4].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[3].getName() + " wins the game!");
			if(p[4].getBoardTotalMethod()== Math.max(Math.max(Math.max(Math.max(p[0].getBoardTotalMethod(), p[1].getBoardTotalMethod()),p[2].getBoardTotalMethod()),p[3].getBoardTotalMethod()),p[4].getBoardTotalMethod()))
				System.out.println("That's all folks! " +  p[4].getName() + " wins the game!");
		}	
	}
	
	 // coloured round.
	public void playColourDiceMove(Player p)
	{
		Scanner keyIn = new Scanner(System.in);
		System.out.println("\n" + p.getName()+ " it's your turn.\n");
		System.out.println("***** Move on any colour dice *****");
		p.printGameBoard();
		printRolledDice();
		
		String whetherUseColourDice, colourDice;		
		int whiteDice;	// the white dice player wants to use
		boolean validMove=false;
		boolean colourFinished=false;
		
		do
		{
			System.out.print("Would you like to cross off a number on the game board using one of the colored dice and a white dice?"
			           + "(anything other than 'yes' is taken to mean no): ");
			
			whetherUseColourDice = keyIn.next();
			
			if(whetherUseColourDice.equals("yes"))
			{
				System.out.print("Which white dice would you like to you use? (White = 1, White2 = 2): ");
				whiteDice = keyIn.nextInt();
				System.out.print("What colour would you like to cross out? (R = red, Y = yellow, G = green, B = Blue): ");
				colourDice = keyIn.next();
				
				Move m = null;			
				if(whiteDice==1)
					m = new Move(colourDice.charAt(0), dice[4].getCurrentSide()+ dice[Move.convertColourtoNum(colourDice.charAt(0))].getCurrentSide());
				if(whiteDice==2)
					m = new Move(colourDice.charAt(0), dice[5].getCurrentSide()+ dice[Move.convertColourtoNum(colourDice.charAt(0))].getCurrentSide());
				
				validMove = checkValidMove(p, m);	
				
				if(validMove)
				{
					p.makeMove(m);	// make a move when the intended move is valid
					p.printGameBoard();
					
					colourFinished = checkColourFinished(p, colourDice.charAt(0));
					
					if(colourFinished)
					{
						if(colourDice.equals("R"))
							System.out.println("Red is no longer playable. Player " + p.getName() + " has locked it.\n");
						if(colourDice.equals("Y"))
							System.out.println("Yellow is no longer playable. Player " + p.getName() + " has locked it.\n");
						if(colourDice.equals("G"))
							System.out.println("Green is no longer playable. Player " + p.getName() + " has locked it.\n");
						if(colourDice.equals("B"))
							System.out.println("Blue is no longer playable. Player " + p.getName() + " has locked it.\n");
									
						if(checkGameFinished())						// check whether the game is finished when a colour has just been locked. 			
						{
							determineWinner(player);
							System.exit(0);										// end the program
						}
					}
				}
				
			}
			else
			{
				p.addNegativePoints(NEGPTS);
				System.out.println("For passing you get -5 points. You now have " + p.getNegativePoints() + " points. \n");
				
				if(checkGameFinished())		// check whether the game is finished when a player get negative points.
				{
					determineWinner(player);
					System.exit(0);										// end the program
				}
			}
			
			
		}while(whetherUseColourDice.equals("yes") && (!validMove) );	// repeat when the intended move is invalid and the player doesn't want to pass the round.
		
		keyIn.close();

	}
}

