package jp.smaphonia;

public class Dealer {

	Trump trump;

	Dealer() {
		trump = new Trump();
	}
	
	/**
	 * カードをシャッフルする
	 * 
	 */
	void shuffleTrump() {
		trump.shuffle();
	}
	
	/**
	 * カードを引く
	 * 
	 * @return
	 */
	Card drawCard() {
		return trump.draw();
	}
}
