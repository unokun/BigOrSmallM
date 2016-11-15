package jp.smaphonia;

public interface Player {
	
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
	
	public void init();
	
	public int makeChoice(Card card);
	public int willContinueGame();
	public int willPlayNewGame();
	
	public void addChip(int bet);
	public int getChipCount();
	public Chip getChip();
	public boolean hasChip();
	public int betChip();
}
