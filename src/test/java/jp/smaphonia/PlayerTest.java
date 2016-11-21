package jp.smaphonia;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.spy;

public class PlayerTest {

	Player player;
	
	@Before
	public void setUp() throws Exception {
		player = spy(new InteractivePlayer());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddChip() {
		try {
			int bet;
			bet = 10;
			assertThat(player.countChip()).isEqualTo(Player.INIT_CHIP_COUNT);
			player.addChip(bet);
			assertThat(player.countChip()).isEqualTo(Player.INIT_CHIP_COUNT + bet);
			
			// チップ数はマイナスにならない
			bet = -2 * Player.INIT_CHIP_COUNT;
			player.addChip(bet);
			assertThat(player.countChip()).isEqualTo(0);
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testHasChip() {
		try {
			assertThat(player.countChip()).isEqualTo(Player.INIT_CHIP_COUNT);
			assertThat(player.hasChip()).isEqualTo(true);

			int bet = -Player.INIT_CHIP_COUNT;
			player.addChip(bet);
			assertThat(player.countChip()).isEqualTo(0);
			assertThat(player.hasChip()).isEqualTo(false);
			
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testChipStatus() {
		try {
			assertThat(player.countChip()).isEqualTo(Player.INIT_CHIP_COUNT);
			String status;
			status = player.chipStatus();
			assertThat(status).isEqualTo("総計： 100([10]10枚, [1]0枚)");
			int bet;
			bet = -14;
			player.addChip(bet);
			status = player.chipStatus();
			assertThat(status).isEqualTo("総計： 86([10]8枚, [1]6枚)");
			bet = -86;
			player.addChip(bet);
			status = player.chipStatus();
			assertThat(status).isEqualTo("総計： 0([10]0枚, [1]0枚)");
		} catch (Exception e) {
			fail();
		}
	}

}
