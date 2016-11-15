package jp.smaphonia;

public abstract class AbstractPlayer implements Player {

	Chip chip;

	AbstractPlayer() {
		chip = new Chip();

	}

	@Override
	public void init() {

	}

	@Override
	public int makeChoice() {
		try {
			int choice = Integer.parseInt(getBigSmallChoice());
			if (choice != CHOICE_BIG && choice != CHOICE_SMALL) {
				return CHOICE_INVALID;
			}
			return choice;
		} catch (NumberFormatException e) {
			return CHOICE_INVALID;
		}
	}
	
	abstract String getBigSmallChoice();

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
		chip.win(bet);
	}

	@Override
	public int getChipCount() {
		return chip.getCount();
	}

	@Override
	public Chip getChip() {
		return chip;
	}

	@Override
	public boolean hasChip() {
		return (chip.getCount() > 0);
	}
	
	@Override
	public int betChip() {
		try {
			int bet = Integer.parseInt(getChipCountForBet());
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

	abstract String getChipCountForBet();
}
