package edu.southalabama.dicegame;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class DiceGame {

	
	private static volatile long seedQualifier = 432282912137141232L;
	public static Random getRandomNumberGenerator() {
		return new Random(++seedQualifier + System.nanoTime());
	}
	
	private static Scanner commandLine = new Scanner(System.in);

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		DiceGame dg = new DiceGame();
		dg.play();
	}
	
	public void play() throws IOException {
		String name;
		int sides = 6;
		int die1Value;
		int die2Value;
		boolean outcome;
		int wins = 0;
		int losses = 0;

		name = getName();

		while(true) {
			System.out.println(name + ": Rolling the dice...\n");

			die1Value = rollDie(sides);
			die2Value = rollDie(sides);

			System.out.println("Die 1: " + die1Value
					+ "\nDie 2: " + die2Value
					+ "\nResult: " + (die1Value + die2Value));

			outcome = determineWin(die1Value, die2Value);

			if(outcome) {
				wins++;
				System.out.println("You win!");
			} else {
				losses++;
				System.out.println("You lose!");
			}

			displayStats(wins, losses);
			if(!continueGame()) {
				return;
			}
		}
	}

	public static String getName() {
		System.out.print("Enter player name:");
		return commandLine.nextLine();
	}

	public static int rollDie(int sides) {
		return (Math.abs(getRandomNumberGenerator().nextInt()) % (sides)) + 1;
	}

	public static boolean determineWin(int value1, int value2) {
		int sum = value1 + value2;
		return sum % 7 == 0 || sum % 12 == 0;
	}

	public static void displayStats(int wins, int losses) {
		System.out.println("# Wins: " + wins + "\n# Losses: " + losses);
	}

	public static boolean continueGame() {
		while(true){
			System.out.println("Continue (Y/N)?");
			switch (commandLine.nextLine().toLowerCase()) {
				case "y":
					return true;
				case "n":
					return false;
				default:
					System.out.println("Please enter Y/N");
			}
		}
	}
}