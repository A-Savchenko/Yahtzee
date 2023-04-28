package com.company;


import java.util.Arrays;

/**
 *	Describe the scorecard here.
 *
 *	@author	Artem Savchenko 
 *	@since	9/29/22
 */
public class YahtzeeScoreCard {

	 private int [] scores = new int [13];
	 private int [] scores1 = new int [13];

	/**
	 * returns the score of a given value in the score array
	 * @param i index in the score array
	 * @return the value under the index i
	 */
	public int getScore(int i) {
		return scores[i-1];
	}

	/**
	 * returns the score of a given value in the score1 array
	 * @param i index in the score1 array
	 * @return the value under the index i
	 */
	public int getScore2(int i) {
		return scores1[i-1];
	}
	
	/**
	 *  Print the scorecard header
	 */
	public void printCardHeader() {
		System.out.println("\n");
		System.out.printf("\t\t\t\t\t\t\t\t               3of  4of  Fll Smll Lrg\n");
		System.out.printf("  NAME\t\t\t  1    2    3    4    5    6   Knd  Knd  Hse " +
						"Strt Strt Chnc Ytz!\n");
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}
	
	/**
	 *  Prints the player 1's score
	 */
	public void printPlayerScore(YahtzeePlayer player) {
		System.out.printf("| %-12s |", player.getName());
		for (int i = 1; i < 14; i++) {
			if (getScore(i) > 0)
				System.out.printf(" %2d |", getScore(i));
			else System.out.printf("    |");
		}
		System.out.println();
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}
	/**
	 *  Prints the player 2's score
	 */
	public void printPlayerScore1(YahtzeePlayer player) {
		System.out.printf("| %-12s |", player.getName());
		for (int i = 1; i < 14; i++) {
			if (getScore2(i) > 0)
				System.out.printf(" %2d |", getScore2(i));
			else System.out.printf("    |");
		}
		System.out.println();
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}


	/**
	 *  Change the scorecard based on the category choice 1-13.
	 *
	 *  @param choice The choice of the player 1 to 13
	 *  @param dg  The DiceGroup to score
	 *  @return  true if change succeeded. Returns false if choice already taken.
	 */
	public boolean changeScore(int choice, DiceGroup dg, int id) {
		int [] holdArr;
		if(id == 1) {
			holdArr = scores;
		} else
		{
			holdArr = scores1;
		}
		switch(choice)
		{
			case 1: case 2: case 3: case 4: case 5: case 6:
				holdArr[choice-1] =  numberScore(choice, dg,holdArr);
			break;
			case 7:
				holdArr[choice-1]=  threeOfAKind(dg);
			break;
			case 8:
				holdArr[choice-1] = fourOfAKind(dg);
			break;
			case 9 :
				holdArr[choice-1] = fullHouse(dg);
			break;
			case 10:
				holdArr[choice-1] =  smallStraight(dg);
			break;
			case 11:
				holdArr[choice-1] = largeStraight(dg);
			break;
			case 12:
				holdArr[choice-1] =  chance(dg);
			break;
			case 13:
				holdArr[choice-1] = yahtzeeScore(dg);
			break;
		}
		if(id  == 1) scores = holdArr;
		else scores1 = holdArr;
		return false;
	}
	
	/**
	 *  Returns a value if the choice was between 1-6
	 * @param choice the column that the user chose
	 * @param dg contains all the dice
	 * @param arr array of scores/scores1 to check if the square is taken
	 * @return int value (number of numbers) * (column number)
	 */
	public int numberScore(int choice, DiceGroup dg,int [] arr) {
		int num = 0;
		if(arr[choice-1]!=0) return arr[choice-1];
		else{
			for(int i = 0; i <5; i++)
			{
				Dice d = dg.getDie(i);
				if(d.getLastRollValue()==choice) num++;
			}
			return num*choice;
		}
	}
	
	/**
	 *	Returns the score depending on if the set of dice has a 3 of a kind
	 *
	 *	@param dg	The DiceGroup to score
	 * 	@return int value which is the score which will be put into the score/score1 array
	 */	
	public int threeOfAKind(DiceGroup dg) {
		int max;
		int one , two, three, four, five, six;
		one = two = three = four = five = six = 0;
		for(int i = 0; i < 5; i ++) {
			Dice d = dg.getDie(i);
			switch (d.getLastRollValue())
			{
				case 1: one++;
				break;
				case 2: two++;
				break;
				case 3: three++;
				break;
				case 4: four++;
				break;
				case 5: five++;
				break;
				case 6: six++;
				break;
			}
		}
		max = Math.max(Math.max(Math.max(Math.max(Math.max(one,two),three),four),five),six);
		if(max >= 3) return 12;
		return 0;
	}

	/**
	 * Returns a score depending on if the set of dice has a 4 of a kind
	 * @param dg The DiceGroup to score
	 * @return int value which is the score that will be put into the score/score1 array
	 */
	public int fourOfAKind(DiceGroup dg) {
		int max;
		int one , two, three, four, five, six;
		one = two = three = four = five = six = 0;
		for(int i = 0; i < 5; i ++) {
			Dice d = dg.getDie(i);
			switch (d.getLastRollValue())
			{
				case 1: one++;
					break;
				case 2: two++;
					break;
				case 3: three++;
					break;
				case 4: four++;
					break;
				case 5: five++;
					break;
				case 6: six++;
					break;
			}
		}
		max = Math.max(Math.max(Math.max(Math.max(Math.max(one,two),three),four),five),six);
		if(max >= 4) return 14;
		return 0;
	}

	/**
	 * Returns a score depending on if the set of dice has a full house
	 * @param dg The DiceGroup to score
	 * @return int value that is the score that will be put into the score/score1 array
	 */
	public int fullHouse(DiceGroup dg) {
		int max;
		int min;
		int [] nums = new int [6];
		for(int i = 0; i < 5; i ++) {
			Dice d = dg.getDie(i);
			switch (d.getLastRollValue())
			{
				case 1: nums[0]++;
					break;
				case 2: nums[1]++;
					break;
				case 3: nums[2]++;
					break;
				case 4: nums[3]++;
					break;
				case 5: nums[4]++;
					break;
				case 6: nums[5]++;
					break;
			}
		}
		max = Math.max(Math.max(Math.max(Math.max(Math.max(nums[0],nums[1]),nums[2]),nums[3]),nums[4]),nums[5]);
		for(int i = 0; i < 6; i ++)
		{
			if(nums[i]==0 ) nums[i] = 999;
		}
		min = Math.min(Math.min(Math.min(Math.min(Math.min(nums[0],nums[1]),nums[2]),nums[3]),nums[4]),nums[5]);
		if(max == 3 && min == 2) return 25;
		return 0;
	}

	/**
	 * checks the set of dice for a small straight and returns an int value varying on the outcome of the search
	 * @param dg The DiceGroup to score
	 * @return int value 30/0 depending on if the set of dice has a small straight in it
	 */
	public int smallStraight(DiceGroup dg) {
		Dice d;
		boolean b = true;
		int [] arr = new int [5];
		for(int i = 0; i < 5;i++)
		{
			d = dg.getDie(i);
			arr[i] = d.getLastRollValue();
		}
		Arrays.sort(arr);
		int min = arr[0];
		for(int i = min; i < arr.length-1; i++)
		{
			if(arr[i]+1!=arr[i+1]) b = false;
		}
		if(b) return 30;
		return 0;
	}

	/**
	 * checks the set of dice for a large straight and returns an int value varying on the outcome of the search
	 * @param dg The DiceGroup to score
	 * @return int value 30/0 depending on if the set of dice has a large straight in it
	 */
	public int largeStraight(DiceGroup dg) {
		Dice d;
		boolean b = true;
		int [] arr = new int [5];
		for(int i = 0; i < 5;i++)
		{
			d = dg.getDie(i);
			arr[i] = d.getLastRollValue();
		}
		Arrays.sort(arr);
		for(int i = 0; i < arr.length-1; i++)
		{
			if(arr[i]+1!=arr[i+1]) b = false;
		}
		if(b) return 40;
		return 0;
	}

	/**
	 * adds all the dice together and returns the sum
	 * @param dg The DiceGroup to score
	 * @return sum of all the dice
	 */
	public int chance(DiceGroup dg) {
		return dg.getTotal();
	}

	/**
	 * checks if all the dice are the same/ for yahtzee
	 * @param dg The DiceGroup to score
	 * @return score depending on if there was a yahtzee or not
	 */
	public int yahtzeeScore(DiceGroup dg) {
		boolean f = false;
		Dice d ;
		Dice d2 ;
		for(int i = 1; i < 5;i++)
		{
			d = dg.getDie(i);
			d2 = dg.getDie(i-1);
			if(d.getLastRollValue() == d2.getLastRollValue()) f = true;
 		}
		if(f)return 50;
		else return 0;
	}

}
