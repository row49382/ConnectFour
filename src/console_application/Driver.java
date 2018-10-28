package console_application;

import java.util.Scanner;

import application.models.ConnectFour;

/**
 * Prints the connect four game to the console.
 * @author Robert Wroblewski
 *
 */
public class Driver
{
	public static void main(String [] args)
	{
		System.out.println("========================");
		System.out.println("Let's Play Connect Four!");
		System.out.println("========================");
		
		int column = -1;
		ConnectFour game = new ConnectFour("R", "B", "-");
		Scanner input = new Scanner(System.in);
		System.out.println(game);

		do
		{
			System.out.print("Enter in a column number to place the token (0-6) ");
			
			if (game.isFirstPlayer())
				System.out.print("RED: ");
			else
				System.out.print("BLK: ");
				
			column = input.nextInt();
			
			try
			{
				game.makeMove(column);
			}
			catch (Exception e)
			{
				System.out.println("Error: " + e.getMessage());
			}
			System.out.println(game);
		}
		while(!game.gameOver());
		
		if (game.isTie())
			System.out.println("It's a tie");
		else if (game.isFirstPlayer())
			System.out.println("The winner is Black!");
		else 
			System.out.println("The winner is Red!");
	}


}
