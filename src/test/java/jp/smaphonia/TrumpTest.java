package jp.smaphonia;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TrumpTest {

	Trump trump;
	
	@Before
	public void setUp() throws Exception {
		trump = new Trump();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTrump() {
		try {
			// 最初はカードの枚数は52枚。
			assertThat(trump.countDeck()).isEqualTo(Trump.TOTAL_CARD);
			Card card = trump.draw();
			// カードを引くと残り枚数が1枚少なくなっていること
			assertThat(trump.countDeck()).isEqualTo(Trump.TOTAL_CARD - 1);

			// 引いたカードの値(範囲)チェック
			int number = card.getNumber();
			int suitId = card.getCardSuit().getId();
			assertThat(number).isBetween(0, (Card.NUM_OF_CARDS - 1));
			assertThat(suitId).isBetween(Card.Suit.SPADES.getId(), Card.Suit.CLUBS.getId());
			
		} catch (Exception e) {
			fail();
			
		}
	}
	@Test
	public void testSwapCard() {
		try {
			int[] cards = new int[2];
			cards[0] = 1;
			cards[1] = 2;
			
			Method method = Trump.class.getDeclaredMethod("swapCard", int.class, int.class, int[].class);
			method.setAccessible(true);
			method.invoke(trump, 0, 1, cards);
			
			assertThat(cards[0]).isEqualTo(2);
			assertThat(cards[1]).isEqualTo(1);
			
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test() {
		try {
			// @Option
			Method method = Trump.class.getDeclaredMethod("test");
			for (Annotation annotation : method.getAnnotations()) {
				System.out.println(annotation);
			}
			
//			for (Method m : Trump.class.getMethods()) {
//				System.out.println("name: " + m.getName());
//				System.out.println("return type: " + m.getReturnType());
//				for (Parameter p : m.getParameters()) {
//					System.out.println("name: " + p.getName());
//					System.out.println("type: " + p.getType());
//				}
//			}
		} catch (Exception e) {
			fail();
		}
	}
}
