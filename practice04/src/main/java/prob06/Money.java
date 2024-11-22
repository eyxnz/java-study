package prob06;

import java.util.Objects;

public class Money {
	private int amount;
	
	public Money(int amount) {
		this.amount = amount;
	}

	public Money add(Money money) {
		return new Money(amount + money.amount);
	}

	public Money minus(Money money) {
		return new Money(amount - money.amount);
	}

	public Money multiply(Money money) {
		return new Money(amount * money.amount);
	}

	public Money divide(Money money) {
		return new Money(amount / money.amount);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Money money = (Money) o;
		return amount == money.amount;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(amount);
	}
}
