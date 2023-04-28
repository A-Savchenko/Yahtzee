package com.company;

/**
 *	Game of yahtzee with two players, competing to fill out the table first, with whoever has the most points, wins.
 *  Different columns have different scores and the player chooses which column to use for their advantage
 *
 *	@author	Artem Savchenko
 *	@since	9/29/22
 */

public class Yahtzee {

	private YahtzeePlayer p1, p2;
	private Prompt p;
	private boolean tie;
	private String name1, name2, winner, loser,choice;
	private int category, rolls ;
	private final YahtzeeScoreCard YSC;
	private DiceGroup roll;

	public static void main(String[] args) {
		Yahtzee yz = new Yahtzee();
		yz.printHeader();
		yz.run();
	}

	/**
	 * constructor for initializing
	 * the field vars
	 */
	public Yahtzee()
	{
		roll = new DiceGroup();
		rolls = 3;
		p = new Prompt();
		choice = "999";
		YSC = new YahtzeeScoreCard();
		tie = false;
	}

	/**
	 * Prints the header and the rules of the game
	 */
	public void printHeader() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!                                                    |");
		System.out.println("|                                                                                    |");
		System.out.println("| There are 13 rounds in a game of Yahtzee. In each turn, a player can roll his/her  |");
		System.out.println("| dice up to 3 times in order to get the desired combination. On the first roll, the |");
		System.out.println("| player rolls all five of the dice at once. On the second and third rolls, the      |");
		System.out.println("| player can roll any number of dice he/she wants to, including none or all of them, |");
		System.out.println("| trying to get a good combination.                                                  |");
		System.out.println("| The player can choose whether he/she wants to roll once, twice or three times in   |");
		System.out.println("| each turn. After the three rolls in a turn, the player must put his/her score down |");
		System.out.println("| on the scorecard, under any one of the thirteen categories. The score that the     |");
		System.out.println("| player finally gets for that turn depends on the category/box that he/she chooses  |");
		System.out.println("| and the combination that he/she got by rolling the dice. But once a box is chosen  |");
		System.out.println("| on the score card, it can't be chosen again.                                       |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME YAHTZEE!                                                           |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n\n");
	}

	/**
	 * Calls all the methods, outputs the winner, asks for names
	 */
	public void run() {

		p1 = new YahtzeePlayer();
		p2 = new YahtzeePlayer();
		name1 = p.getString("Player 1, please enter your first name : ");
		name2 = p.getString("Player 2, please enter your first name : ");
		initialRoll();

		YSC.printCardHeader();
		YSC.printPlayerScore(p1);
		YSC.printPlayerScore1(p2);

		for (int i = 0; i < 13; i++)
		{
			System.out.println("\n\nRound " + (i + 1) + " out of 13 rounds.\n\n");
			player1Roll();
			player2Roll();
		}

		int player1 = 0, player2 = 0;

		for (int i = 1; i < 14; i++)
		{
			player1 += YSC.getScore(i);
			player2 += YSC.getScore2(i);
		}

		System.out.println("\n\n");

		if (player1 > player2)
		{
			System.out.println(winner + " won! With " + player1 + " points");
			System.out.println(loser + " lost with " + player2 + " points");
		}
		else if (player1 == player2)
		{
			System.out.println("Tie! Both have " + player1 + " points");
		}
		else
		{
			System.out.println(loser + " won! With " + player2 + " points");
			System.out.println(winner + " lost with " + player1 + " points");
		}
	}

	/**
	 * Rolls the initial dice to determine whoever is going to be going first
	 */
	public void initialRoll()
	{
		while (!tie)
		{
			p.getString("\nLet's see who will go first. " + name1 + ", please hit enter to roll the dice :  ");
			DiceGroup play1 = new DiceGroup();
			play1.rollDice();
			play1.printDice();
			int p1FirstRoll = play1.getTotal();
			p.getString(name2 + ", it's your turn. Please hit enter to roll the dice :  ");
			DiceGroup play2 = new DiceGroup();
			play2.rollDice();
			play2.printDice();
			int p2FirstRoll = play2.getTotal();
			if (p2FirstRoll != p1FirstRoll)
			{

				tie = !tie;
				System.out.println(name1 + ", you rolled a sum of " + p1FirstRoll + ", and " + name2 + ", you rolled a"+
						" sum of " + p2FirstRoll + ". ");
				if (Math.max(p1FirstRoll, p2FirstRoll) == p1FirstRoll)
				{
					winner = name1;
					loser = name2;
					p1.setName(winner);
					p2.setName(loser);
				} else
				{
					winner = name2;
					loser = name1;
					p2.setName(loser);
					p1.setName(winner);
				}
				System.out.println(winner + ", since your sum was higher, you'll roll first. ");
			}
			else
			{
				System.out.println("Whoops, we have a tie (both rolled " + p1FirstRoll + "). Looks like we'll have to "+
						"try that again . . . ");
			}
		}
	}

	/**
	 * First player's turn
	 */
	public void player1Roll()
	{
		rolls = 3;
		roll.rollDice();
		choice = "999";

		p.getString(winner + ", it's your turn to play. Please hit enter to roll the dice :  ");

		roll.printDice();
		while (Integer.parseInt(choice) > -1 && rolls > 0)
		{
			choice = p.getString("Which di(c)e would you like to keep?  Enter the values you'd like to 'hold' "+
					"without\n" +
					"spaces.  For examples, if you'd like to 'hold' die 1, 2, and 5, enter 125\n" +
					"(enter -1 if you'd like to end the turn) : ");
			if(Integer.parseInt(choice) == -1) rolls = 0;
			else if (choice.equals(""))
			{
				roll.rollDice();
				roll.printDice();
			}
			else if (Integer.parseInt(choice) != -1)
			{
				roll.rollDice(choice);
				roll.printDice();
			}
			rolls--;
			choice = "9999";
		}
		YSC.printCardHeader();
		YSC.printPlayerScore(p1);
		YSC.printPlayerScore1(p2);
		System.out.printf("      \t\t      1    2    3    4    5    6    7    8    9" +
				"%5d%5d%5d%5d\n", 10, 11, 12, 13);
		 category = p.getInt(winner + ", now you need to make a choice. Pick a valid integer from the list above : ");
		YSC.changeScore(category, roll, 1);

		YSC.printCardHeader();
		YSC.printPlayerScore(p1);
		YSC.printPlayerScore1(p2);
	}

	/**
	 * Second player's turn
	 */
	public void player2Roll()
	{
		rolls = 3;
		choice = "999";
		roll.rollDice();
		System.out.println("\n\n");
		p.getString(loser + ", it's your turn to play. Please hit enter to roll the dice :  ");

		roll.printDice();
		while (Integer.parseInt(choice) > -1 && rolls > 0)
		{
			choice = p.getString("Which di(c)e would you like to keep?  Enter the values you'd like to 'hold' "+
					"without\n" +
					"spaces.  For examples, if you'd like to 'hold' die 1, 2, and 5, enter 125\n" +
					"(enter -1 if you'd like to end the turn) : ");
			if(Integer.parseInt(choice) == -1) rolls = 0;
			else if (choice.equals(""))
			{
				roll.rollDice();
				roll.printDice();
			}
			else if (Integer.parseInt(choice) != -1)
			{
				roll.rollDice(choice);
				roll.printDice();
			}
			rolls--;
			choice = "9999";
		}
		YSC.printCardHeader();
		YSC.printPlayerScore(p1);
		YSC.printPlayerScore1(p2);
		System.out.printf("      \t\t      1    2    3    4    5    6    7    8    9" +
				"%5d%5d%5d%5d\n", 10, 11, 12, 13);
		System.out.println("\n");
		category = p.getInt(loser + ", now you need to make a choice. Pick a valid integer from the list above : ");
		YSC.changeScore(category, roll, 2);

		YSC.printCardHeader();
		YSC.printPlayerScore(p1);
		YSC.printPlayerScore1(p2);
	}
}