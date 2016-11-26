package jp.smaphonia;

/**
 * 
 * @author unokun
 *
 */
public class GreedyPlayer extends AbstractPlayer {

	@Override
	String getBigSmallChoice(Card card) {
		int value = card.getValue();
		return (value < Trump.TOTAL_CARD / 2) ? String.valueOf(Player.CHOICE_BIG) : String.valueOf(Player.CHOICE_SMALL);
	}

	@Override
	String getYesNoChoice() {
		return String.valueOf(Player.CHOICE_YES);
	}

	/**
	 * 相手のカードによってダブルアップするかどうか決める。
	 */
	@Override
	public int willContinueGame(Card card) {
		double value = Math.abs(card.getValue() - Trump.TOTAL_CARD / 2) * (Math.random() + 1);
		return (value > Trump.TOTAL_CARD / 2) ? Player.CHOICE_YES : Player.CHOICE_NO;
	}

	/**
	 * 相手のカードのよって賭けるチップ数を決める。
	 */
	@Override
	String betChipCount(Card card) {
		int bet = (int)(Math.abs(card.getValue() - Trump.TOTAL_CARD / 2 ) * 0.8); // 20 / (26 -1)
		if (bet == 0) {
			bet = 1;
		} else if (bet > Player.BET_MAX) {
			bet = Player.BET_MAX;
		}
		int max = countChip();
		if (bet > max) {
			bet = max;
		}
		return String.valueOf(bet);
	}

}
