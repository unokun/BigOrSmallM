package jp.smaphonia;

/**
 * Playerの選択
 * 
 * @author unokun
 *
 */
public class Choice {
	int bet;
	int choice;
	
	Choice(int bet, int choice) {
		this.bet = bet;
		this.choice = choice;
	}

	public int getBet() {
		return bet;
	}

	public int getChoice() {
		return choice;
	}

}
