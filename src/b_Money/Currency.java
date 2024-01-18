package b_Money;

public class Currency {
	private String name;
	private Double rate;

	// Constructor
	Currency(String name, Double rate) {
		this.name = name;
		this.rate = rate;
	}

	// Convert an amount of this Currency to its value in the general "universal currency"
	public Integer universalValue(Integer amount) {
		return (int) Math.round(rate * amount);
	}

	// Get the name of this Currency.
	public String getName() {
		return name;
	}

	// Get the rate of this Currency.
	public Double getRate() {
		return rate;
	}

	// Set the rate of this currency.
	public void setRate(Double rate) {
		this.rate = rate;
	}

	// Convert an amount from another Currency to an amount in this Currency
	public Integer valueInThisCurrency(Integer amount, Currency otherCurrency) {
		return (int) Math.round((amount * otherCurrency.rate) / this.rate);
	}
}
