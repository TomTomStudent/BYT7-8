package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest {
    Currency SEK, DKK;
    Bank SweBank, Nordea, DanskeBank;

    @Before
    public void setUp() throws Exception {
        DKK = new Currency("DKK", 0.20);
        SEK = new Currency("SEK", 0.15);
        SweBank = new Bank("SweBank", SEK);
        Nordea = new Bank("Nordea", SEK);
        DanskeBank = new Bank("DanskeBank", DKK);
        SweBank.openAccount("Ulrika");
        SweBank.openAccount("Bob");
        Nordea.openAccount("Bob");
        DanskeBank.openAccount("Gertrud");
    }

    @Test
    public void testGetName() {
        assertEquals("SweBank", SweBank.getName());
        assertEquals("Nordea", Nordea.getName());
        assertNotEquals("SweBank", DanskeBank.getName());
    }

    @Test
    public void testGetCurrency() {
        assertEquals(SEK, SweBank.getCurrency());
        assertNotEquals(DKK, Nordea.getCurrency());
        assertEquals(DKK, DanskeBank.getCurrency());
    }

    @Test(expected = AccountExistsException.class)
    public void testOpenAccount() throws AccountExistsException {
        SweBank.openAccount("June");
        assertNotNull(SweBank.findAccount("June"));
        SweBank.openAccount("Ulrika");
    }

    @Test(expected = AccountDoesNotExistException.class)
    public void testDeposit() throws AccountDoesNotExistException {
        assertEquals(0, DanskeBank.getBalance("Gertrud").intValue());
        DanskeBank.deposit("Gertrud", new Money(10000, DKK));
        assertEquals(10000, DanskeBank.getBalance("Gertrud").intValue());
        DanskeBank.deposit("Bob", new Money(10000, DKK));
    }

    @Test(expected = AccountDoesNotExistException.class)
    public void testWithdraw() throws AccountDoesNotExistException {
        assertEquals(0, DanskeBank.getBalance("Gertrud").intValue());
        DanskeBank.withdraw("Gertrud", new Money(10000, DKK));
        assertEquals(-10000, DanskeBank.getBalance("Gertrud").intValue());
        DanskeBank.withdraw("Bob", new Money(10000, DKK));
    }

    @Test(expected = AccountDoesNotExistException.class)
    public void testGetBalance() throws AccountDoesNotExistException {
        assertEquals(0, DanskeBank.getBalance("Gertrud").intValue());
        DanskeBank.deposit("Gertrud", new Money(10000, DKK));
        assertEquals(10000, DanskeBank.getBalance("Gertrud").intValue());
        DanskeBank.deposit("Bob", new Money(10000, DKK));
    }

    @Test(expected = AccountDoesNotExistException.class)
    public void testTransfer() throws AccountDoesNotExistException {
        assertEquals(0, DanskeBank.getBalance("Gertrud").intValue());
        assertEquals(0, SweBank.getBalance("Bob").intValue());
        assertEquals(0, SweBank.getBalance("Ulrika").intValue());
        DanskeBank.transfer("Gertrud",  SweBank, "Bob", new Money(10000, DKK));
        assertEquals(-10000, DanskeBank.getBalance("Gertrud").intValue());
        assertEquals(13333, SweBank.getBalance("Bob").intValue());
        assertEquals(0, SweBank.getBalance("Ulrika").intValue());
        SweBank.transfer("Bob", "Ulrika", new Money(10000, DKK));
        assertEquals(-10000, DanskeBank.getBalance("Gertrud").intValue());
        assertEquals(0, SweBank.getBalance("Bob").intValue());
        assertEquals(13333, SweBank.getBalance("Ulrika").intValue());
        SweBank.transfer("Ulrika", "Johan", new Money(10000, DKK));
    }

    @Test(expected = AccountDoesNotExistException.class)
    public void testTimedPayment() throws AccountDoesNotExistException {
        assertEquals(0, SweBank.getBalance("Bob").intValue());
        assertEquals(0, SweBank.getBalance("Ulrika").intValue());
        SweBank.addTimedPayment("Bob", "Child Support", 10, 5, new Money(25000, SEK),
                SweBank, "Ulrika");
        for (int i = 5; i >= 0; i--)
        {
            SweBank.tick();
        }
        assertEquals(-25000, SweBank.getBalance("Bob").intValue());
        assertEquals(25000, SweBank.getBalance("Ulrika").intValue());
        for (int i = 10; i >= 0; i--)
        {
            SweBank.tick();
        }
        assertEquals(-50000, SweBank.getBalance("Bob").intValue());
        assertEquals(50000, SweBank.getBalance("Ulrika").intValue());
        SweBank.addTimedPayment("Bob", "Child Support", 10, 5, new Money(25000, SEK),
                SweBank, "Gertrud");
    }
}
