package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;

	@Before
	public void setUp() throws Exception {
		// Setup currencies with exchange rates
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
	}

	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0.0001);
	}

	@Test
	public void testSetRate() {
		assertEquals(0.15, SEK.getRate(), 0.0001);
		SEK.setRate(0.2);
		assertEquals(0.2, SEK.getRate(), 0.0001);
	}

	@Test
	public void testUniversalValue() {
		assertEquals(1, SEK.universalValue(5), 0.0001);
	}

	@Test
	public void testValueInThisCurrency() {
		// Use DKK as the other currency for testing value conversion
		assertEquals(13.0, SEK.valueInThisCurrency(10, DKK), 0.0001);
	}
}
