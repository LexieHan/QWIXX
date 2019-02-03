import java.util.Random;

public class Dice 
{
	private String colour;		// colour of the die
	private int currentSide;		// store the side of the die
	
	public Dice()				// default constructor
	{
		colour = "white";
		currentSide = rollDice();
	}

	public Dice(String colour)	// constructor
	{
		setColour(colour);
		currentSide = rollDice();
	}
	
	public String getColour()
	{
		return colour;
	}
	
	public void setColour(String colour)
	{
		this.colour = colour;
	}
	
	public int getCurrentSide()
	{
		return currentSide;
	}
	
	public String toString()
	{
		return colour + " dice: " + currentSide + " | ";
	}
	
	public int rollDice()					// produce random number 1 to 6
	{
		Random dice = new Random();
		currentSide = dice.nextInt(6) + 1;	
		return currentSide;
	}
		
	
}
