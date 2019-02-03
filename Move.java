
public class Move 
{
	private char colour;		// the colour that player wants to cross off on the gameboard
	private int number;		// the number that player wants to cross off on the gameboard
	
	public char getColour()
	{
		return colour;
	}
	
	public void setColour(char colour)
	{
		this.colour = colour;
	}
	
	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}
	
	public Move(char colour, int number)				// constructor
	{
		this.colour = colour;
		this.number = number;			
	}
	
	public static int convertColourtoNum(char colour)		// convert the colour that player choose to the row of the gameboard
	{
		if (colour == 'R')
			return 0;
		if (colour == 'Y')
			return 1;
		if (colour == 'G')
			return 2;
		if (colour == 'B')
			return 3;
		else 
			return -1;
	}
	
}
