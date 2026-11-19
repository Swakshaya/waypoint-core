
package waypoint.model;

import java.util.Objects;

/** Value object representing a unique vehicle ID. */
public record VehicleId(String value) {

    public VehicleId {
        value = Objects.requireNonNull(
                value, "VehicleId cannot be null").trim();

        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    "VehicleId cannot be blank");
        }
    }
}
