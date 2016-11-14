package jp.smaphonia;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BigOrSmallTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}



	@Test
	public void testPrint() {
		try {
			try (ByteArrayOutputStream bas = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(bas)) {
				BigOrSmall game = spy(new BigOrSmall());
				doReturn(ps).when(game).getPrintStream();
				String message = "test";
				game.print(message);

//				assertEquals(message, new String(bas.toByteArray()));
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
				BigOrSmall game = spy(new BigOrSmall());
				doReturn(ps).when(game).getPrintStream();
				String message = "test";
				game.println(message);
				byte[] bytes = bas.toByteArray();
				String lineSSeparator = System.getProperty("line.separator");
				assertEquals(message, new String(bytes, 0, bytes.length - lineSSeparator.length()));
				assertEquals(new String(bytes, bytes.length - lineSSeparator.length(), lineSSeparator.length()), lineSSeparator);
			}

		} catch (Exception e) {
			fail();
		}
	}

}
