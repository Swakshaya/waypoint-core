package waypoint.model;

public class CurrencyMismatch extends IllegalArgumentException {

    public CurrencyMismatch() {
        super("Cannot perform operation with different currencies");
    }
}