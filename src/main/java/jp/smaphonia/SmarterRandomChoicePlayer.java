package jp.smaphonia;

public class SmarterRandomChoicePlayer extends RandomChoicePlayer {
	@Override
	String getBigSmallChoice(Card card) {
		int choice =  (card.getNumber() > 7) ? Player.CHOICE_SMALL : Player.CHOICE_BIG; 
		return String.valueOf(choice);
	}
}
