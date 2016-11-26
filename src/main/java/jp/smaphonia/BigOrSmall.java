package jp.smaphonia;

import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class BigOrSmall {
	private static final Logger LOGGER = LogManager.getLogger(BigOrSmall.class);
	// 連続勝負可能最大数
	private static final int MAX_CONSECUTIVE = 8;

	@Option(name="-i", usage = "Set an interactive player[default].")
	boolean interractivePlayer = true;

	@Option(name="-rnd", usage = "Set a random player.")
	boolean randomPlayer = false;

	@Option(name="-srnd", usage = "Set a smarter random player.")
	boolean smarterRandomPlayer = false;

	@Option(name="-g", usage = "Set a greedy player.")
	boolean greedyPlayer = false;

	Dealer dealer;
	Player player;
	
	// 賭けているチップ
	// 連続で賭ける場合には、このチップ数が増えていく
	int bettingChips;
	
	// 比較する2枚のカード
	// cardAが最初に引くカード。
	Card cardA;
	Card cardB;
	
	public Card getCardA() {
		return cardA;
	}

	public void setCardA(Card cardA) {
		this.cardA = cardA;
	}

	public Card getCardB() {
		return cardB;
	}

	public void setCardB(Card cardB) {
		this.cardB = cardB;
	}

	public int getBettingChips() {
		return bettingChips;
	}

	public void setBettingChips(int bettingChips) {
		this.bettingChips = bettingChips;
	}


	void initGame() {
		dealer = makeDealer();
		player = makePlayer();
		
	}

	Dealer makeDealer() {
		return new Dealer();
	}

	Player makePlayer() {
		Player player = null;
		if (smarterRandomPlayer) {
			player = new SmarterRandomChoicePlayer();
		} else if (randomPlayer) {
			player = new RandomChoicePlayer();
		} else if (greedyPlayer) {
			player = new GreedyPlayer();
		} else {
			player = new InteractivePlayer();
			
		}
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

	/**
	 * カードの内容を出力する
	 */
	void printCards() {
		println("現在のカード：" + getCardA());
		println("引いたカード：" + getCardB());

	}

	/*
	 * 勝ちましたメッセージを出力します
	 * 
	 * @param chip
	 * @param won
	 * @return
	 */
	void printWinMessgae(int won) {
		println("Win!!");
		println("チップを" + won + "枚獲得しました");
	}

	void printChipStatus() {
		println("*****現在のチップ枚数*****");
		println(player.chipStatus());
		println("************************");

	}

	void printCardAndChipStatus(Card card) {
		println("*****チップ枚数とカード*****");
		println(player.chipStatus());
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
		if (choice == InteractivePlayer.CHOICE_BIG && actual) {
			return true;
		}
		if (choice == InteractivePlayer.CHOICE_SMALL && !actual) {
			return true;
		}
		return false;
	}

	/**
	 * カードの大小を比較します
	 * 
	 * @param cardB
	 * @param cardA
	 * @return
	 */
	boolean compareCard(Card cardB, Card cardA) {
		boolean isBigger = cardB.isBiggerThan(cardA);
		if (isBigger) {
			println(cardB.toString() + "は" + cardA.toString() + "よりBig");

		} else {
			println(cardB.toString() + "は" + cardA.toString() + "よりSmall");

		}
		println("************************");
		return isBigger;
	}

	/**
	 * Playerが賭けるチップ枚数を取得する
	 * 
	 * @param scanner
	 * @return
	 */
	int getPlayerBet(Card card) {
		int bet = 0;
		while (true) {
			println("");
			println("■BET枚数選択");
			println("BETするチップ数を入力してください(最低1〜20枚)");
			bet = player.betChip(card);
			if (bet == InteractivePlayer.BET_INVALID) {
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
	int getPlayerChoice() {
		Card cardA = getCardA();
		println("■Big or Small選択");
		println("現在のカード：" + cardA);

		int choice = makeChoice(cardA, "[Big or Small]: 0: Big 1:Small");

		println("*****Big or Small*****");
		println("BET数：" + getBettingChips());
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
	private int makeChoice(Card card, String question) {
		int choice = InteractivePlayer.CHOICE_INVALID;
		while (true) {
			println(question);
			choice = player.makeChoice(card);
			if (choice != InteractivePlayer.CHOICE_INVALID) {
				break;
			}
			println("半角数字の0か1のみを入力してください");

		}
		return choice;
	}
	/**
	 * 勝ったチップを使ってゲームを継続する?
	 * 
	 * @return
	 */
	boolean continueGame(Card card, int won) {
		int choice = willContinueGame(card, "[獲得したチップ" + won + "枚でBig or Smallを続けますか？]: 0: Yes 1:No");
		return (choice == InteractivePlayer.CHOICE_YES);
	}
	private int willContinueGame(Card card, String question) {
		int choice = InteractivePlayer.CHOICE_INVALID;
		while (true) {
			println(question);
			choice = player.willContinueGame(card);
			if (choice != InteractivePlayer.CHOICE_INVALID) {
				break;
			}
			println("半角数字の0か1のみを入力してください");

		}
		return choice;
	}
	/**
	 * 新しいゲームとして続けるかどうか？
	 * 
	 * @param chip
	 * @param scanner
	 * @return
	 */
	boolean playNewGame(Card card) {
		int choice = willPlayNewGame(card, "[ゲームを続けますか？]: 0: Yes 1:No");
		return (choice == InteractivePlayer.CHOICE_YES);
	}

	private int willPlayNewGame(Card card, String question) {
		int choice = InteractivePlayer.CHOICE_INVALID;
		while (true) {
			println(question);
			choice = player.willPlayNewGame(card);
			if (choice != InteractivePlayer.CHOICE_INVALID) {
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
	/**
	 * 
	 * <h3>ゲームの終了条件</h3>
	 * <ul>
	 * <li>チップがなくなった時</li>
	 * <li>ユーザーが新しいゲームをしないと選択した時</li>
	 * </ul>
	 */
	void playGame() {
		LOGGER.info("start game");

		initGame();
		
		while (player.hasChip()) {
			dealer.shuffleTrump();

			Card cardA = dealer.drawCard();
			printCardAndChipStatus(cardA);
			setCardA(cardA);
			LOGGER.info("draw cardA: " + cardA);

			// 賭けられているチップ枚数
			// 最初はPlayerが選択する
			int bet = getPlayerBet(cardA);
			setBettingChips(bet);
			LOGGER.info("bet: " + bet);

			int consecutiveWin = 0;
			while (consecutiveWin < MAX_CONSECUTIVE) {
				if (!playTurn((consecutiveWin == (MAX_CONSECUTIVE - 1)))) {
					break;
				}
				consecutiveWin += 1;
			}

			printChipStatus();

			if (player.countChip() > 1000) {
				return;
			}
			
			// ゲームを継続するかどうか
			if (!playNewGame(cardA)) {
				return;
			}
			LOGGER.info("continue as new game.");
		}
		println("チップがなくなりました");

	}
	/**
	 * 1回の勝負
	 *  
	 * @param lastTurn 連続勝負の最後かどうか？
	 * @return
	 */
	boolean playTurn(boolean lastTurn) {
		// Playerの選択
		int choice = getPlayerChoice();

		Card cardA = getCardA();
		Card cardB = dealer.drawCard();
		setCardB(cardB);
		LOGGER.info("draw cardB: " + cardB);

		printCards();

		boolean isBigger = compareCard(cardB, cardA);
		if (isWin(choice, isBigger)) {
			int won = getBettingChips() * 2;

			printWinMessgae(won);
			LOGGER.info("win: " + won + " chip = " + player.countChip());

			// 勝ったチップを使ってゲームを継続する
			// 連続勝負最大数になっている場合には継続できない
			if (lastTurn || !continueGame(cardB, won)) {
				// 継続しない場合、場にあるチップをもらって、連続勝負ループを抜ける
				player.addChip(won);
				return false;
			}
			LOGGER.info("continue the game.");
			setBettingChips(won);
			
			// カードBをカードAとする
			setCardA(cardB);
			
			return true;
		}

		println("Lose...");
		LOGGER.info("lose: " + getBettingChips() + " chip = " + player.countChip());

		return false;
	}

	public static void main(String[] args) {
		
		try {
			BigOrSmall game = new BigOrSmall();
			CmdLineParser parser = new CmdLineParser(game);
			parser.parseArgument(args);
			
			game.playGame();
			game.println("END");
			
		} catch (CmdLineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
