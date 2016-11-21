package jp.smaphonia;

public abstract class AbstractPlayer implements Player {

	int chip;

	AbstractPlayer() {
		chip = INIT_CHIP_COUNT;

	}

	@Override
	public void init() {

	}

	@Override
	public int makeChoice(Card card) {
		try {
			int choice = Integer.parseInt(getBigSmallChoice(card));
			if (choice != CHOICE_BIG && choice != CHOICE_SMALL) {
				return CHOICE_INVALID;
			}
			return choice;
		} catch (NumberFormatException e) {
			return CHOICE_INVALID;
		}
	}
	
	abstract String getBigSmallChoice(Card card);

	@Override
	public int willContinueGame() {
		return makeYesNoChoice();
	}

	@Override
	public int willPlayNewGame() {
		return makeYesNoChoice();
	}

	int makeYesNoChoice() {
		try {
			int choice = Integer.parseInt(getYesNoChoice());
			if (choice != CHOICE_YES && choice != CHOICE_NO) {
				return CHOICE_INVALID;
			}
			return choice;
		} catch (NumberFormatException e) {
			return CHOICE_INVALID;
		}	
	}
	
	abstract String getYesNoChoice();
	
	@Override
	public void addChip(int bet) {
		chip += bet;
		if (chip < 0) {
			chip = 0;
		}
	}

	@Override
	public int countChip() {
		return chip;
	}

	@Override
	public boolean hasChip() {
		return (chip > 0);
	}
	
	@Override
	public int betChip() {
		try {
			int bet = Integer.parseInt(betChipCount());
			if (bet < BET_MIN || bet > BET_MAX) {
				return BET_INVALID;
			}
			if (bet > chip) {
				return BET_INVALID;
			}
			chip -= bet;
			return bet;
		} catch (NumberFormatException e) {
			return BET_INVALID;
		}
	}

	abstract String betChipCount();
	
	@Override
	public String chipStatus() {
		StringBuilder builder = new StringBuilder();
		builder.append("総計： " + chip)
			   .append("(")
			   .append("[10]" + chip / 10 + "枚")
			   .append(", ")
			   .append("[1]" + chip % 10 + "枚")
			  .append(")");
		return builder.toString();		
	}
}
