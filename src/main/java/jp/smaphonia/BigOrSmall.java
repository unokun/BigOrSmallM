package jp.smaphonia;

import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BigOrSmall {
	private static final Logger LOGGER = LogManager.getLogger(BigOrSmall.class);

	Dealer dealer;
	Player player;

	BigOrSmall() {
		dealer = makeDealer();
		player = makePlayer();

	}

	Dealer makeDealer() {
		return new Dealer();
	}

	Player makePlayer() {
		Player player = new Player();
		player.init();
		return player;
	}

	/**
	 * 出力ストリーム(標準出力)を取得します
	 * 
	 * @return
	 */
	PrintStream getPrintStream() {
		return System.out;
	}

	void println(String message) {
		getPrintStream().println(message);
	}

	void print(String message) {
		getPrintStream().print(message);
	}

	void printCard(Card currentCard, Card drawnCard) {
		println("現在のカード：" + currentCard);
		println("引いたカード：" + drawnCard);

	}

	/*
	 * 勝ちましたメッセージを出力します
	 * 
	 * @param chip
	 * 
	 * @param won
	 * 
	 * @return
	 */
	void printWinMessgae(int won) {
		println("Win!!");
		println("チップを" + won + "枚獲得しました");
	}

	void printChipStatus() {
		println("*****現在のチップ枚数*****");
		println(player.printChipStatus());
		println("************************");

	}

	void printCardAndChipStatus(Card card) {
		println("*****チップ枚数とカード*****");
		println(player.printChipStatus());
		println("現在のカード：" + card);
		println("************************");

	}

	/**
	 * 勝ったかどうか判定します 選択した結果と実際が一致した場合、勝ちと判定します。
	 * 
	 * @param choice
	 *            大きいかちいさいかの選択
	 * @param actual
	 *            引いたカードが表示されているカードより大きいかどうか
	 * @return
	 */
	boolean isWin(int choice, boolean actual) {
		if (choice == Player.CHOICE_BIG && actual) {
			return true;
		}
		if (choice == Player.CHOICE_SMALL && !actual) {
			return true;
		}
		return false;
	}

	/**
	 * カードの大小を比較します
	 * 
	 * @param currentCard
	 * @param drawnCard
	 * @return
	 */
	boolean compareCard(Card currentCard, Card drawnCard) {
		boolean isBigger = drawnCard.isBiggerThan(currentCard);
		if (isBigger) {
			println(currentCard.toString() + "は" + drawnCard.toString() + "よりSmall");

		} else {
			println(currentCard.toString() + "は" + drawnCard.toString() + "よりBig");

		}
		println("************************");
		return isBigger;
	}

	/**
	 * bet枚数を入力する
	 * 
	 * @param scanner
	 * @return
	 */
	private int getBet() {
		int bet = 0;
		while (true) {
			println("");
			println("■BET枚数選択");
			println("BETするチップ数を入力してください(最低1〜20枚)");
			bet = player.betChip();
			if (bet == Player.BET_INVALID) {
				continue;
			}
			return bet;

		}
	}

	/**
	 * 大きいか小さいかの選択を取得します
	 * 
	 * @param scanner
	 * @param currentCard
	 * @param bet
	 * @return
	 */
	int getChoice(Card currentCard, int bet) {
		println("■Big or Small選択");
		println("現在のカード：" + currentCard.toString());

		int choice = getYesNoChoice("[Big or Small]: 0: Big 1:Small");

		println("*****Big or Small*****");
		println("BET数：" + bet);
		print("あなたの選択：");
		if (choice == Player.CHOICE_BIG) {
			print("Big");
		} else {
			print("Small");
		}
		println("");

		LOGGER.info("choice: " + choice);
		return choice;
	}

	/**
	 * 勝ったチップを使ってゲームを継続する?
	 * 
	 * @return
	 */
	boolean continueGame(int won) {
		int choice = getYesNoChoice("[獲得したチップ" + won + "枚でBig or Smallを続けますか？]: 0: Yes 1:No");
		return (choice == Player.CHOICE_YES);
	}

	/**
	 * 新しいゲームとして続けるかどうか？
	 * 
	 * @param chip
	 * @param scanner
	 * @return
	 */
	private boolean playNewGame() {
		int choice = getYesNoChoice("[ゲームを続けますか？]: 0: Yes 1:No");
		return (choice == Player.CHOICE_YES);
	}

	private int getYesNoChoice(String question) {
		int choice = Player.CHOICE_INVALID;
		while (true) {
			println(question);
			choice = player.willPlayNewGame();
			if (choice != Player.CHOICE_INVALID) {
				break;
			}
			println("半角数字の0か1のみを入力してください");

		}
		return choice;
	}
	// カードを比較
	// 勝った場合
	// チップを更新する
	// 勝ったチップを賭けてゲームを継続する？
	// 継続する場合
	// 8回継続すると継続できない
	// ゲーム続行
	// 継続しない場合
	// 新しいゲームとして継続する？
	// 継続する：カードをシャッフルしてゲーム続行
	// 継続しない：ゲーム終了
	// 負けた場合
	// チップを更新する(減らす)
	// チップの枚数チェック
	// 0になったらゲーム終了
	// 0枚より多い場合
	// 新しいゲームとして継続する？
	// 継続する：カードをシャッフルしてゲーム続行
	// 継続しない：ゲーム終了
	void startGame() {
		LOGGER.info("start game");

		while (player.hasChip()) {
			dealer.shuffleTrump();

			Card cardA = dealer.drawCard();
			LOGGER.info("cardA: " + cardA);
			printCardAndChipStatus(cardA);

			int consecutiveWin = 0;
			while (consecutiveWin < 8) {

				// Playerの選択
				int bet = getBet();
				int choice = getChoice(cardA, bet);

				Card cardB = dealer.drawCard();
				LOGGER.info("cardB: " + cardB);

				printCard(cardA, cardB);

				boolean isBigger = compareCard(cardA, cardB);
				if (isWin(choice, isBigger)) {
					int won = bet * 2;
					player.addChip(won);

					printWinMessgae(won);
					LOGGER.info("win: " + won + " chip = " + player.getChipCount());

					// 勝ったチップを使ってゲームを継続する
					if (!continueGame(won)) {
						// 連続勝負ループを抜ける
						break;
					}
					LOGGER.info("continue the game.");
					bet = won;
					cardA = cardB;
					consecutiveWin += 1;
				} else {
					println("Lose...");
					LOGGER.info("lose: " + bet + " chip = " + player.getChipCount());
					
					// 連続勝負ループを抜ける
					break;
				}
			}

			printChipStatus();

			// ゲームを継続するかどうか
			if (!playNewGame()) {
				return;
			}
			LOGGER.info("continue as new game.");
		}
		println("チップがなくなりました");

	}

	public static void main(String[] args) {
		BigOrSmall game = new BigOrSmall();
		game.startGame();
		game.println("END");
	}
}
