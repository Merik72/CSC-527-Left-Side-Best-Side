package edu.southalabama.dicegame.textui;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import edu.southalabama.dicegame.model.DiceGame;
import edu.southalabama.dicegame.model.Player;

public class DiceGameUI {
	
	private final static Scanner f_commandLine = new Scanner(System.in);

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		DiceGameUI dg = new DiceGameUI();
		dg.playGame();
	}
	
	private void playGame() throws IOException {
		
		boolean keepPlaying = true;
		int wins = 0;
		int losses = 0;
		
		Player player = getPlayer();
		
		/*
		 * A DiceGame instance can be use to repeatedly throw dice
		 */
		DiceGame game = new DiceGame();
		
		while (keepPlaying) {
			System.out.println ();
			System.out.println (player.getName() + ": Rolling the dice...");
					
			/*
			 * The play() method should "roll" the dice
			 */
			game.play();
			
			
			/*
			 * The getDieValues() method should return a List of Integer values
			 * that represents the current face values of the dice
			 */
			List<Integer> dice = game.getDieValues();
			
			System.out.println ();
			System.out.println ("Die 1: " + dice.get(0));
			System.out.println ("Die 2: " + dice.get(1));
			System.out.println ("Result: " + game.getResult()); 
				
			if (game.getOutcome() == true) {
				wins = wins + 1;
				System.out.println ("You win!");
			}
			else {
				losses = losses + 1;
				System.out.println ("You lose!");
			}
				
			displayStats(wins, losses);
			
			keepPlaying = continueGame();
			
			System.out.println();
			System.out.println();
					
		}
		
		System.out.println("Thank you for playing!");
	}

	private Player getPlayer() throws IOException {
		String name = new String();
		while (name.length() < 1 || name.length() > 50)  {
			System.out.print("Enter player name: ");
			String commandline = readLineFromConsole();
			name = commandline.trim();
		}
		return new Player(name);
	}
	
	private boolean continueGame() throws IOException {
		System.out.println ("Continue (Y/N)?");
		final String response = readLineFromConsole().toLowerCase().trim();
		if (!response.equalsIgnoreCase("Y")) {
			return false;
		}	
		else {
			return true;
		}
	}
	
	private void displayStats(int wins, int losses) {
		System.out.println ("# Wins: " + wins);
		System.out.println ("# Losses: " + losses);
	}
	
	private static String readLineFromConsole() throws IOException {
		String s = f_commandLine.nextLine();
		return s;
	}

}
