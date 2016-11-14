package jp.smaphonia;

import java.io.InputStream;
import java.util.Scanner;

public class Player {
	// 賭けるチップ数
	static final int BET_MIN = 1;
	static final int BET_MAX = 20;
	static final int BET_INVALID = -1;

	// 選択
	static final int CHOICE_YES = 0;
	static final int CHOICE_NO = 1;
	static final int CHOICE_BIG = 0;
	static final int CHOICE_SMALL = 1;
	static final int CHOICE_INVALID = -1;

	Chip chip;
	Scanner scanner;
	
	Player() {
		chip = new Chip();
	}
	
	void init() {
		scanner = new Scanner(getInputStream());
		
	}
	/**
	 * 入力ストリーム(標準入力)を取得します
	 * 
	 * @return
	 */
	InputStream getInputStream() {
		return System.in;
	}
	/**
	 * 大きいか小さいかの選択を取得します
	 * 
	 * @return
	 */
	int makeChoice() {
		try {
			int choice = Integer.parseInt(scanner.next());
			if (choice != CHOICE_BIG && choice != CHOICE_SMALL) {
				return CHOICE_INVALID;
			}
			return choice;
		} catch (NumberFormatException e) {
			return CHOICE_INVALID;
		}
	}

	/**
	 * betするチップ枚数を取得する
	 * 
	 * @param scanner
	 * @return
	 */
	int betChip() {

		try {
			int bet = Integer.parseInt(scanner.next());
			if (bet < BET_MIN || bet > BET_MAX) {
				return BET_INVALID;
			}
			if (bet > chip.getCount()) {
				return BET_INVALID;
			}
			chip.lose(bet);
			return bet;
		} catch (NumberFormatException e) {
			return BET_INVALID;
		}
	}
	/**
	 * チップを増やす
	 * 
	 * @param bet
	 */
	void addChip(int bet) {
		chip.win(bet);
	}
	
	/**
	 * チップの枚数
	 * 
	 * @return
	 */
	int getChipCount() {
		return chip.getCount();
	}
	/**
	 * チップの状態(枚数)
	 * 
	 * @return
	 */
	String printChipStatus() {
		return chip.toString();
	}
	
	/**
	 * チップを持っているかどうか？
	 * @return
	 */
	boolean hasChip() {
		return (chip.getCount() > 0);
	}
	/**
	 * 獲得したチップでゲームを続けるかどうか？
	 * 
	 * @param scanner
	 * @return
	 */
	int willContinueGame() {
		return makeYesNoChoice();
	}
	/**
	 * 新しいゲームをするかどうか？
	 * 
	 * @param scanner
	 * @return
	 */
	int willPlayNewGame() {
		return makeYesNoChoice();
	}
	
	private int makeYesNoChoice() {
		try {
			int choice = Integer.parseInt(scanner.next());
			if (choice != CHOICE_YES && choice != CHOICE_NO) {
				return CHOICE_INVALID;
			}
			return choice;
		} catch (NumberFormatException e) {
			return CHOICE_INVALID;
		}	
	}
}
