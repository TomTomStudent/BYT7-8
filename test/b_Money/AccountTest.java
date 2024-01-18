package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account HansAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		HansAccount = new Account("Hans", SEK);
		HansAccount.deposit(new Money(10000000, SEK));
		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testAddRemoveTimedPayment() throws AccountDoesNotExistException {
		assertEquals(10000000, HansAccount.getBalance().getAmount().intValue());
		assertEquals(1000000, SweBank.getBalance("Alice").intValue());
		HansAccount.addTimedPayment("C4$H", 10, 5, new Money(1000000, SEK),
				SweBank, "Alice");
		HansAccount.removeTimedPayment("C4$H");
		HansAccount.addTimedPayment("C4$H", 10, 5, new Money(1000000, SEK),
				SweBank, "Hegel");
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		assertEquals(10000000, HansAccount.getBalance().getAmount().intValue());
		assertEquals(1000000, SweBank.getBalance("Alice").intValue());
		HansAccount.addTimedPayment("C4$H", 10, 5, new Money(1000000, SEK),
				SweBank, "Alice");
		for (int i = 5; i >= 0; i--)
		{
			HansAccount.tick();
		}
		assertEquals(9000000, HansAccount.getBalance().getAmount().intValue());
		assertEquals(2000000, SweBank.getBalance("Alice").intValue());
		for (int i = 10; i >= 0; i--)
		{
			HansAccount.tick();
		}
		assertEquals(8000000, HansAccount.getBalance().getAmount().intValue());
		assertEquals(3000000, SweBank.getBalance("Alice").intValue());
		HansAccount.removeTimedPayment("C4$H");
		for (int i = 10; i >= 0; i--)
		{
			HansAccount.tick();
		}
		assertEquals(8000000, HansAccount.getBalance().getAmount().intValue());
		assertEquals(3000000, SweBank.getBalance("Alice").intValue());
	}

	@Test
	public void testAddWithdraw() {
		assertEquals(10000000, HansAccount.getBalance().getAmount().intValue());
		HansAccount.deposit(new Money(10000000, SEK));
		assertEquals(20000000, HansAccount.getBalance().getAmount().intValue());
		HansAccount.withdraw(new Money(40000000, SEK));
		assertEquals(-20000000, HansAccount.getBalance().getAmount().intValue());
	}

	@Test
	public void testGetBalance() {
		assertEquals(10000000, HansAccount.getBalance().getAmount().intValue());
	}
}
