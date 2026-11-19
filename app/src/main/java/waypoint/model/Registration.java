
package waypoint.model;

import java.util.Locale;
import java.util.Objects;

/**
 * VALUE OBJECT. Validates and normalises registration numbers.
 */
public record Registration(String value) {

    // Format: two letters, two digits, one or two letters, four digits.
    // Example: MH12AB1234
    public Registration {
        value = Objects.requireNonNull(value, "Registration cannot be null")
                .trim()
                .toUpperCase(Locale.ROOT);

        if (!value.matches("[A-Z]{2}\\d{2}[A-Z]{1,2}\\d{4}")) {
            throw new IllegalArgumentException("Invalid registration number: " + value);
        }
    }
}
