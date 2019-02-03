
public class Player 
{
	private String name;			// player's name
	private String[][] gameBoard = new String [4][11];		// player's gameboard
	
	private int crossedR, crossedY, crossedG, crossedB, negativePoints; 	// crossedR/Y/G/B is for comparing the number that player want to cross off
																		// negativePoints: to store the total negative points a player has
	public Player()				// default constructor
	{
		name = "anonymous";					
		gameBoard = initializeGameboard();
		crossedR = crossedY = negativePoints =0;		// initialize crossedR,crossedY, negativePoints
		crossedG = crossedB = 20; 					// initialize crossedG, crossedB
	}
	
	public Player(String name)	// constructor
	{
		this.name = name;
		gameBoard = initializeGameboard();								
		crossedR = crossedY = negativePoints =0;		// initialize crossedR,crossedY, negativePoints
		crossedG = crossedB = 20; 					// initialize crossedG, crossedB
		
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getCrossedR()
	{
		return crossedR;
	}
	
	public int getCrossedY()
	{
		return crossedY;
	}
	
	public int getCrossedG()
	{
		return crossedG;
	}
	
	public int getCrossedB()
	{
		return crossedB;
	}
	
	public int getNegativePoints()
	{
		return negativePoints;
	}
	
	public String[][] initializeGameboard()		// original gameboard
	{
		for (int c=0; c<=10; c++)
		{
			gameBoard[0][c] = "" + (c+2);  
			gameBoard[1][c] = "" + (c+2);  
			gameBoard[2][c] = "" + (12-c); 
			gameBoard[3][c] = "" + (12-c); 
		}		
		
		return gameBoard;
	}
	
	public void addNegativePoints(int pts)
	{
		negativePoints += pts;
	}
	
	public void printGameBoard()					// print out player's current gameboard
	{
		System.out.println(name + "'s Gameboard:");
		
		System.out.print("    Red:");
		for(int c=0; c<11; c++)
			System.out.printf("%3s", gameBoard[0][c]);
		System.out.println();
		
		System.out.print(" Yellow:");
		for(int c=0; c<11; c++)
			System.out.printf("%3s", gameBoard[1][c]);
		System.out.println();
		
		System.out.print("  Green:");
		for(int c=0; c<11; c++)
			System.out.printf("%3s", gameBoard[2][c]);
		System.out.println();

		System.out.print("   Blue:");
		for(int c=0; c<11; c++)
			System.out.printf("%3s", gameBoard[3][c]);
		System.out.println();

		System.out.println();

	}
	
	public void makeMove(Move m)			// update the crossedR/Y/G/B and set the crossed number to X when player makes a move.
	{
		if (Move.convertColourtoNum(m.getColour()) == 0) 
		{
			crossedR = m.getNumber();		
			gameBoard[Move.convertColourtoNum(m.getColour())][m.getNumber()-2] = "X";			
		}	
		
		if (Move.convertColourtoNum(m.getColour()) == 1)
		{
			crossedY = m.getNumber();
			gameBoard[Move.convertColourtoNum(m.getColour())][m.getNumber()-2] = "X";	
		}
					
		if (Move.convertColourtoNum(m.getColour()) == 2)
		{
			crossedG = m.getNumber();
			gameBoard[Move.convertColourtoNum(m.getColour())][12-m.getNumber()] = "X";
		}
		
		if (Move.convertColourtoNum(m.getColour()) == 3)
		{
			crossedB =m.getNumber();
			gameBoard[Move.convertColourtoNum(m.getColour())][12-m.getNumber()] = "X";
		}
			
	
	}
	
	public int getBoardTotalMethod()				// calculate a player's total points when game is over
	{
		int [] scoreTable = {0,1,3,6,10,15,21,28,36,45,55,66};
		int countR=0, countY=0, countG=0, countB=0;
		
		for (int c=0; c<=10; c++)
		{
			if (gameBoard[0][c].equals("X"))
				countR++;
		}
		
		for (int c=0; c<=10; c++)
		{
			if (gameBoard[1][c].equals("X"))
				countY++;
		}
		
		for (int c=0; c<=10; c++)
		{
			if (gameBoard[2][c].equals("X"))
				countG++;
		}
		
		for (int c=0; c<=10; c++)
		{
			if (gameBoard[3][c].equals("X"))
				countB++;
		}
		
		return (scoreTable[countR] + scoreTable[countY] + scoreTable[countG] + scoreTable[countB] + negativePoints);		
											
	}
	
	
	
}
