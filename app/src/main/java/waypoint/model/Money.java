package waypoint.model;

import java.util.Currency;
import java.util.List;
import java.util.Objects;

public final class Money implements Comparable<Money> {

    public static final Currency INR = Currency.getInstance("INR");
    public static final Currency USD = Currency.getInstance("USD");

    private final long minor;
    private final Currency currency;

    private Money(long minor, Currency currency) {
        this.minor = minor;
        this.currency = Objects.requireNonNull(currency, "currency cannot be null");
    }

    public static Money ofMinor(long minor, Currency currency) {
        return new Money(minor, currency);
    }

    public static Money rupees(long rupees) {
        return new Money(Math.multiplyExact(rupees, 100), INR);
    }

    public long minor() {
        return minor;
    }

    public Currency currency() {
        return currency;
    }

    public Money plus(Money other) {
        checkCurrency(other);
        return new Money(Math.addExact(minor, other.minor), currency);
    }

    public Money minus(Money other) {
        checkCurrency(other);
        return new Money(Math.subtractExact(minor, other.minor), currency);
    }

    public Money times(int multiplier) {
        return new Money(Math.multiplyExact(minor, multiplier), currency);
    }

    public boolean isNegative() {
        return minor < 0;
    }

    @Override
    public int compareTo(Money other) {
        checkCurrency(other);
        return Long.compare(minor, other.minor);
    }

    public List<Money> allocate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number of shares must be positive");
        }

        long base = minor / n;
        long remainder = minor % n;

        List<Money> shares = new java.util.ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            long share = base;

            if (remainder > 0 && i < remainder) {
                share++;
            } else if (remainder < 0 && i < -remainder) {
                share--;
            }

            shares.add(new Money(share, currency));
        }

        return List.copyOf(shares);
    }

    private void checkCurrency(Money other) {
        Objects.requireNonNull(other, "money cannot be null");

        if (!currency.equals(other.currency)) {
            throw new CurrencyMismatch();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Money other))
            return false;

        return minor == other.minor && currency.equals(other.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minor, currency);
    }

    @Override
    public String toString() {
        return currency.getCurrencyCode() + " " + (minor / 100.0);
    }
}