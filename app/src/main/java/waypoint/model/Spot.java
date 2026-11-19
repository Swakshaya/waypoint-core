
package waypoint.model;

import java.util.Objects;

/**
 * ENTITY. Identity = id.
 * Invariant: a spot cannot be assigned while occupied.
 */
public final class Spot {

    private final SpotId id;
    private VehicleId occupant; // the ONLY mutable field

    // Constructor validating id
    public Spot(SpotId id) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
    }

    // Assigns a vehicle only if the spot is free
    public boolean tryAssign(VehicleId v) {
        Objects.requireNonNull(v, "vehicle id cannot be null");

        if (occupant != null) {
            return false;
        }

        occupant = v;
        return true;
    }

    // Releases the spot; releasing a free spot is allowed
    public void release(VehicleId v) {
        if (v != null && v.equals(occupant)) {
            occupant = null;
        }
    }

    // Checks whether the spot is free
    public boolean isFree() {
        return occupant == null;
    }

    // Entity identity depends only on id
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Spot other)) {
            return false;
        }

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
