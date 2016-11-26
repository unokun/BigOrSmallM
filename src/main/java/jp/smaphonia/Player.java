package jp.smaphonia;

public interface Player {
	// 初期チップ数
	static final int INIT_CHIP_COUNT = 100;

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
	public int willContinueGame(Card card);
	public int willPlayNewGame(Card card);
	
	public void addChip(int bet);
	public int countChip();
	public boolean hasChip();
	public int betChip(Card card);
	public String chipStatus();
}
