
package waypoint.model;

/** Exception thrown when attempting to close an already closed ticket. */
public class TicketAlreadyClosed extends IllegalStateException {

    public TicketAlreadyClosed() {
        super("Ticket is already closed");
    }
}
