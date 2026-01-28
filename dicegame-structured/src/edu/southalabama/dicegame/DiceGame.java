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

	private String name;
	private int sides = 6;
	private int die1Value;
	private int die2Value;
	private int result;
	private int wins;
	private int losses;
	private boolean continuePlaying = true;

	private void getName() {
		name = commandLine.nextLine();
		System.out.println("Welcome, " + name + "!");
	}

	private void rollDice() {
		Random rng = getRandomNumberGenerator();
		die1Value = rng.nextInt(sides) + 1;
		die2Value = rng.nextInt(sides) + 1;
		result = die1Value + die2Value;
		System.out.println("Die 1: " + die1Value);
		System.out.println("Die 2: " + die2Value);
		System.out.println("Result: " + result);
	}
	private void determineWinOrLoss() {
			if (result >= 7) {
				System.out.println("You win!");
				wins++;
			} else if (result < 7) {
				System.out.println("You lose!");
				losses++;
			}

		}

	private void displayStats() {
		System.out.println("Wins: " + wins);
		System.out.println("Losses: " + losses);
	}

	private void continueGame() {
		System.out.println("Do you want to play again? (y/n): ");
		String response = commandLine.nextLine();
		if (response.equalsIgnoreCase("n")) {
			continuePlaying = false;
			System.out.println("Thanks for playing, " + name + "!");
		}
			else if (response.equalsIgnoreCase("y")) {
			continuePlaying = true;
		}
	}



	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		DiceGame dg = new DiceGame();
		dg.play();
	}
	
	public void play() throws IOException {
		System.out.print("Please enter your name: ");
		getName();
		while (continuePlaying) {
			rollDice();
			determineWinOrLoss();
			displayStats();
			continueGame();
		}

		
		
	}
	

}