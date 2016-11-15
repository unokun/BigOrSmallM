package jp.smaphonia;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BigOrSmallTest {

	String lineSSeparator = System.getProperty("line.separator");

	BigOrSmall game;

	@Before
	public void setUp() throws Exception {
		game = spy(new BigOrSmall());

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	// チップがなくなって終了
	public void testPlayGame() {
		try {
			try (ByteArrayOutputStream bas = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(bas)) {
				doReturn(ps).when(game).getPrintStream();
				Player player = spy(new Player());
				player.init();
				doReturn(false).when(player).hasChip();
				game.player = player;

				game.playGame();

				byte[] bytes = bas.toByteArray();
				String message = new String(bytes, 0, bytes.length - lineSSeparator.length());
				assertThat(message).isEqualTo("チップがなくなりました");
			}
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// playTuenでfalseを返す
	public void testPlayGame2() {
		try {
			try (ByteArrayOutputStream bas = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(bas)) {
				doReturn(ps).when(game).getPrintStream();
				Card cardA = Card.createCard(Card.Suit.CLUBS, 1);
				game.dealer = spy(new Dealer());
				doReturn(cardA).when(game.dealer).drawCard();

				int bet = 10;
				doReturn(bet).when(game).getPlayerBet();
				doReturn(false).when(game).playTurn(anyBoolean());
				doReturn(false).when(game).playNewGame();

				game.playGame();

				assertThat(game.getBettingChips()).isEqualTo(bet);
				byte[] bytes = bas.toByteArray();
				String message = new String(bytes, 0, bytes.length - lineSSeparator.length());
				assertThat(message).isNotEqualTo("チップがなくなりました");
			}
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	// playTuenでtrueを返す
	public void testPlayGame3() {
		try {
			try (ByteArrayOutputStream bas = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(bas)) {
				doReturn(ps).when(game).getPrintStream();
				Card cardA = Card.createCard(Card.Suit.CLUBS, 1);
				game.dealer = spy(new Dealer());
				doReturn(cardA).when(game.dealer).drawCard();

				int bet = 10;
				doReturn(bet).when(game).getPlayerBet();
				doReturn(true).when(game).playTurn(anyBoolean());
				doReturn(false).when(game).playNewGame();

				game.playGame();

				assertThat(game.getBettingChips()).isEqualTo(bet);
				
				// playTuenが8回呼ばれると終了する
				verify(game, times(8)).playTurn(anyBoolean());
				
				byte[] bytes = bas.toByteArray();
				String message = new String(bytes, 0, bytes.length - lineSSeparator.length());
				assertThat(message).isNotEqualTo("チップがなくなりました");
			}
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// Win
	// 継続無し
	// 返値:false
	// Playerのチップは賭けた枚数の2倍増えている
	public void testPlayTurnWin() {
		try {
			// 勝負前のチップ数
			int chipOrg = game.player.getChipCount();

			// 賭けているチップ数
			int bet = 10;
			game.setBettingChips(bet);

			Card cardA = Card.createCard(Card.Suit.CLUBS, 1);
			Card cardB = Card.createCard(Card.Suit.CLUBS, 2);
			game.setCardA(cardA);
			game.dealer = spy(new Dealer());
			doReturn(cardB).when(game.dealer).drawCard();

			// 大きいという選択
			// 継続無し
			doReturn(Player.CHOICE_BIG).when(game).getPlayerChoice();
			doReturn(false).when(game).continueGame(anyInt());

			boolean result = game.playTurn(false);

			assertThat(result).isEqualTo(false);
			assertThat(game.player.getChipCount() - chipOrg).isEqualTo(2 * bet);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// Win
	// 継続無し
	// 連続勝負の最後のターン
	// 返値:true
	// Playerのチップは変化無し
	// 場のチップは賭けたチップの2倍の枚数
	public void testPlayTurnWin2() {
		try {
			// 勝負前のチップ数
			int chipOrg = game.player.getChipCount();

			// 賭けているチップ数
			int bet = 10;
			game.setBettingChips(bet);

			// カード
			Card cardA = Card.createCard(Card.Suit.CLUBS, 1);
			Card cardB = Card.createCard(Card.Suit.CLUBS, 2);
			game.setCardA(cardA);
			game.dealer = spy(new Dealer());
			doReturn(cardB).when(game.dealer).drawCard();

			// 大きいという選択
			// 継続
			doReturn(Player.CHOICE_BIG).when(game).getPlayerChoice();
			doReturn(true).when(game).continueGame(anyInt());

			boolean result = game.playTurn(false);
			assertThat(result).isEqualTo(true);
			assertThat(game.player.getChipCount() - chipOrg).isEqualTo(0);
			assertThat(game.getBettingChips()).isEqualTo(2 * bet);
			assertThat(game.getCardA()).isEqualTo(cardB);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// Win
	// 連続勝負の最後のターン
	// 返値:false
	// Playerのチップは賭けた枚数の2倍増えている
	public void testPlayTurnWin3() {
		try {
			// 勝負前のチップ数
			int chipOrg = game.player.getChipCount();

			// 賭けているチップ数
			int bet = 10;
			game.setBettingChips(bet);

			// カード
			Card cardA = Card.createCard(Card.Suit.CLUBS, 1);
			Card cardB = Card.createCard(Card.Suit.CLUBS, 2);
			game.setCardA(cardA);
			game.dealer = spy(new Dealer());
			doReturn(cardB).when(game.dealer).drawCard();

			// 大きいという選択
			doReturn(Player.CHOICE_BIG).when(game).getPlayerChoice();

			boolean result = game.playTurn(true);
			assertThat(result).isEqualTo(false);
			assertThat(game.player.getChipCount() - chipOrg).isEqualTo(2 * bet);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testPrint() {
		try {
			try (ByteArrayOutputStream bas = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(bas)) {
				doReturn(ps).when(game).getPrintStream();
				String message = "test";
				game.print(message);

				// assertEquals(message, new String(bas.toByteArray()));
				assertThat(new String(bas.toByteArray())).isEqualTo(message);
			}

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testPrintln() {
		try {
			try (ByteArrayOutputStream bas = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(bas)) {
				doReturn(ps).when(game).getPrintStream();
				String message = "test";
				game.println(message);
				byte[] bytes = bas.toByteArray();
				String lineSSeparator = System.getProperty("line.separator");
				assertEquals(message, new String(bytes, 0, bytes.length - lineSSeparator.length()));
				assertEquals(new String(bytes, bytes.length - lineSSeparator.length(), lineSSeparator.length()),
						lineSSeparator);
			}

		} catch (Exception e) {
			fail();
		}
	}

}
