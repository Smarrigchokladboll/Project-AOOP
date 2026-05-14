package Framework;

import java.util.ArrayList;

/**
 * Manages events.
 * 
 * Events can be registered, removed and invoked by name.
 */
public class EventManager {

    private ArrayList<Event> eventListeners;

    /**
     * Creates an empty EventManager.
     */
    public EventManager() {
        eventListeners = new ArrayList<>();
    }

    /**
     * Invokes all events that match the given event name.
     *
     * @param eventName the name of the event to invoke
     */
    public void invokeEvent(String eventName) {
        for (Event event : eventListeners) {
            if (event.match(eventName)) {
                event.invoke(eventName);
            }
        }
    }

    /**
     * Adds an event listener.
     *
     * @param event the event listener
     * @return the added event listener
     */
    public Event addEventListener(Event event) {
        eventListeners.add(event);
        return event;
    }

    /**
     * Removes an event listener.
     *
     * @param event the event listener to remove
     */
    public void removeEventListener(Event event) {
        eventListeners.remove(event);
    }
}