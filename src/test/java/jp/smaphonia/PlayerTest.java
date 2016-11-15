package jp.smaphonia;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class PlayerTest {

	InteractivePlayer player;
	@Before
	public void setUp() throws Exception {
		player = spy(new InteractivePlayer());
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testMakeChoiceBig() {
		try {

			try (InputStream in = new ByteArrayInputStream("0".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int choice = player.makeChoice();

				assertThat(choice).isEqualTo(InteractivePlayer.CHOICE_BIG);
			}
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testMakeChoiceSmall() {
		try {

			try (InputStream in = new ByteArrayInputStream("1".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int choice = player.makeChoice();

				assertThat(choice).isEqualTo(InteractivePlayer.CHOICE_SMALL);
			}
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testMakeChoiceInvalid() {
		try {

			try (InputStream in = new ByteArrayInputStream("2".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int choice = player.makeChoice();

				assertThat(choice).isEqualTo(InteractivePlayer.CHOICE_INVALID);
			}
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testMakeChoiceInvalid2() {
		try {

			try (InputStream in = new ByteArrayInputStream("a".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int choice = player.makeChoice();

				assertThat(choice).isEqualTo(InteractivePlayer.CHOICE_INVALID);
			}
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testBetChipOK() {
		try {

			try (InputStream in = new ByteArrayInputStream("1".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int bet = player.betChip();

				assertThat(bet).isEqualTo(1);
			}
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testBetChipOK2() {
		try {

			try (InputStream in = new ByteArrayInputStream("20".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int bet = player.betChip();

				assertThat(bet).isEqualTo(20);
			}
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testBetChipInvalid() {
		try {

			try (InputStream in = new ByteArrayInputStream("0".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int bet = player.betChip();

				assertThat(bet).isEqualTo(InteractivePlayer.BET_INVALID);
			}
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testBetChipInvalid2() {
		try {

			try (InputStream in = new ByteArrayInputStream("21".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int bet = player.betChip();

				assertThat(bet).isEqualTo(InteractivePlayer.BET_INVALID);
			}
		} catch (Exception e) {
			fail();
		}
	}	
	@Test
	public void testBetChipInvalid3() {
		try {

			try (InputStream in = new ByteArrayInputStream("a".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int bet = player.betChip();

				assertThat(bet).isEqualTo(InteractivePlayer.BET_INVALID);
			}
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testWillContinueGameOK() {
		try {

			try (InputStream in = new ByteArrayInputStream("0".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int choice = player.makeChoice();

				assertThat(choice).isEqualTo(InteractivePlayer.CHOICE_YES);
			}
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testWillContinueGameOK2() {
		try {

			try (InputStream in = new ByteArrayInputStream("1".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int choice = player.makeChoice();

				assertThat(choice).isEqualTo(InteractivePlayer.CHOICE_NO);
			}
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testWillContinueGameInvalid() {
		try {

			try (InputStream in = new ByteArrayInputStream("2".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int choice = player.makeChoice();

				assertThat(choice).isEqualTo(InteractivePlayer.CHOICE_INVALID);
			}
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testWillContinueGameInvalid2() {
		try {

			try (InputStream in = new ByteArrayInputStream("a".getBytes())) {
				doReturn(in).when(player).getInputStream();

				player.init();
				int choice = player.makeChoice();

				assertThat(choice).isEqualTo(InteractivePlayer.CHOICE_INVALID);
			}
		} catch (Exception e) {
			fail();
		}
	}
	
}
