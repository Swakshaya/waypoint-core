
package waypoint.model;

import java.time.Instant;
import java.util.Objects;

/**
 * ENTITY. Invariants:
 * I1 entry is always present
 * I2 exit is null, or not before entry
 * I3 fare is null, or not negative
 * I4 fare exists only if exit exists
 * I5 a closed ticket never reopens
 */
public final class Ticket {

    private final TicketId id;
    private final SpotId spot;
    private final Instant entry;

    // Exactly two mutable fields
    private Instant exit;
    private Money fare;

    // Private constructor
    private Ticket(TicketId id, SpotId spot, Instant entry) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.spot = Objects.requireNonNull(spot, "spot cannot be null");
        this.entry = Objects.requireNonNull(entry, "entry cannot be null");
    }

    // Creates a new open ticket
    public static Ticket open(TicketId id, SpotId spot, Instant entry) {
        return new Ticket(id, spot, entry);
    }

    // Checks whether ticket is open
    public boolean isOpen() {
        return exit == null;
    }

    // Returns the fare; null while ticket is open
    public Money fare() {
        return fare;
    }

    // Closes ticket and calculates fare
    public Money close(Instant at, FarePolicy policy) {

        Objects.requireNonNull(at, "exit time cannot be null");
        Objects.requireNonNull(policy, "fare policy cannot be null");

        if (!isOpen()) {
            throw new TicketAlreadyClosed();
        }

        if (at.isBefore(entry)) {
            throw new IllegalArgumentException(
                    "Exit time cannot be before entry time");
        }

        Money calculatedFare = Objects.requireNonNull(
                policy.fareFor(entry, at, spot),
                "Fare policy returned null");

        if (calculatedFare.isNegative()) {
            throw new IllegalArgumentException(
                    "Fare cannot be negative");
        }

        // Update both fields only after successful validation
        this.exit = at;
        this.fare = calculatedFare;

        return fare;
    }
}
