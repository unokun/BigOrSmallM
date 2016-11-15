package jp.smaphonia;

import java.io.InputStream;
import java.util.Scanner;

public class InteractivePlayer extends AbstractPlayer {


	Scanner scanner;
	
	InteractivePlayer() {
	}
	@Override
	public void init() {
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

	@Override
	String getBigSmallChoice() {
		return getFromStandardInput();
		
	}

	@Override
	String getYesNoChoice() {
		return getFromStandardInput();
	}

	@Override
	String getChipCountForBet() {
		return getFromStandardInput();
	}

	String getFromStandardInput() {
		return scanner.next();
	}
}
