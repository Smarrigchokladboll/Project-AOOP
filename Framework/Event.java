package Framework;

/**
 * Interface for events that can be invoked by the EventManager.
 */
public interface Event {

    /**
     * Invokes the event.
     *
     * @param eventName the name of the invoked event
     */
    void invoke(String eventName);

    /**
     * Checks if this event matches the given event name.
     *
     * @param eventName the event name to compare with
     * @return true if the event names match, false otherwise
     */
    boolean match(String eventName);
}