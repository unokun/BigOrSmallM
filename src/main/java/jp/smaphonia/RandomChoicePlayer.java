package jp.smaphonia;

public class RandomChoicePlayer extends AbstractPlayer {

	@Override
	String getBigSmallChoice(Card card) {
		return String.valueOf((int)(Math.random() * 2));
	}

	@Override
	String getYesNoChoice() {
		return String.valueOf((int)(Math.random() * 2));
	}

	@Override
	String getChipCountForBet() {
		return String.valueOf((int)(Math.random() * 19 + 1));
	}

}
