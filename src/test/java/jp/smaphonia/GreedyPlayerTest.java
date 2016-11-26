package jp.smaphonia;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GreedyPlayerTest {

	GreedyPlayer player;
	
	@Before
	public void setUp() throws Exception {
		player = new GreedyPlayer();
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testBetChipCount() {
		try {
			Card cardA;
			cardA = Card.createCard(1);
			assertThat(player.betChip(cardA)).isEqualTo(20);

			cardA = Card.createCard(13);
			assertThat(player.betChip(cardA)).isEqualTo(10);

			cardA = Card.createCard(26);
			assertThat(player.betChip(cardA)).isEqualTo(1);
			
			cardA = Card.createCard(52);
			assertThat(player.betChip(cardA)).isEqualTo(20);

			
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testGetBigSmallChoice() {
		try {
			Card cardA;
			cardA = Card.createCard(1);
			assertThat(player.getBigSmallChoice(cardA)).isEqualTo(String.valueOf(Player.CHOICE_BIG));

			cardA = Card.createCard(25);
			assertThat(player.getBigSmallChoice(cardA)).isEqualTo(String.valueOf(Player.CHOICE_BIG));

			cardA = Card.createCard(26);
			assertThat(player.getBigSmallChoice(cardA)).isEqualTo(String.valueOf(Player.CHOICE_SMALL));

			cardA = Card.createCard(52);
			assertThat(player.getBigSmallChoice(cardA)).isEqualTo(String.valueOf(Player.CHOICE_SMALL));

		} catch (Exception e) {
			fail();
		}
	}

}
