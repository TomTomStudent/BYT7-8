package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(10000, SEK100.getAmount().intValue());
		assertEquals(1000, EUR10.getAmount().intValue());
		assertEquals(-10000, SEKn100.getAmount().intValue());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(SEK, SEK0.getCurrency());
		assertEquals(SEK, SEKn100.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("10.0 EUR", EUR10.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(1500, SEK100.universalValue().intValue());
		assertEquals(-1500, SEKn100.universalValue().intValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK100.equals(EUR10));
		assertTrue(SEK200.equals(EUR20));
		assertFalse(EUR10.equals(EUR20));
	}

	Money SEK300, EUR30;

	@Test
	public void testAdd() {
		SEK300 = new Money(30000, SEK);
		EUR30 = new Money(3000, EUR);

		assertEquals(0, EUR30.compareTo(EUR10.add(EUR20)));
		assertEquals(-1, EUR20.compareTo(SEK200.add(EUR10)));
		assertEquals(1, SEK200.compareTo(EUR0.add(EUR10)));
	}

	@Test
	public void testSub() {
		SEK300 = new Money(30000, SEK);
		EUR30 = new Money(3000, EUR);

		assertEquals(0, SEKn100.compareTo(SEK100.sub(SEK200)));
		assertEquals(1, EUR20.compareTo(SEK200.sub(EUR10)));;
		assertEquals(-1, SEK200.compareTo(EUR30.sub(SEK0)));
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertFalse(EUR20.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals(0, SEKn100.compareTo(EUR10.negate()));
		assertEquals(-1, SEKn100.compareTo(SEKn100.negate()));
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, SEK100.compareTo(EUR10));
		assertEquals(1, SEK200.compareTo(EUR10));
		assertEquals(-1, SEKn100.compareTo(EUR20));
	}
}
