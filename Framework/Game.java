package Framework;

import java.util.ArrayList;

/**
 * Base class for all games using the framework.
 * 
 * The Game class connects the game logic with one controller, multiple views,
 * an event system and a serialization manager.
 * 
 * Concrete games should extend this class and implement:
 * - init()
 * - createController()
 * - createViews()
 */
public abstract class Game {

    protected Controller controller;
    protected ArrayList<View> views;

    private EventManager eventManager;
    private SerializationManager serializationManager;

    private String title;

    /**
     * Called after the basic framework objects have been created.
     * Use this method to initialize game-specific data.
     */
    protected abstract void init();

    /**
     * Creates the controller used by the game.
     *
     * @return the controller for this game
     */
    protected abstract Controller createController();

    /**
     * Creates all views used by the game.
     * 
     * A game can have several views, for example:
     * - Swing view
     * - Console view
     * - Debug view
     *
     * @return an array of views
     */
    protected abstract View[] createViews();

    /**
     * Creates a new game and connects controller, views, event manager
     * and serialization manager.
     *
     * @param title the title of the game
     */
    public Game(String title) {
        this.title = title;

        views = new ArrayList<>();
        eventManager = new EventManager();
        serializationManager = new SerializationManager();

        controller = createController();
        controller.setGame(this);

        init();

        View[] createdViews = createViews();

        for (View view : createdViews) {
            addView(view);
        }

        controller.init();
        controller.setViews(views);

        for (View view : views) {
            view.addKeyListener(controller);
            view.addMouseListener(controller);
        }
    }

    /**
     * Adds a view to the game.
     * This makes it possible to register views dynamically.
     *
     * @param view the view to add
     */
    public void addView(View view) {
        views.add(view);
        view.setGame(this);
        view.setTitle(title);
        view.init();
    }

    /**
     * Removes a view from the game.
     * This makes it possible to de-register views dynamically.
     *
     * @param view the view to remove
     */
    public void removeView(View view) {
        views.remove(view);
    }

    /**
     * Repaints all registered views.
     * This is used when the game state has changed.
     */
    public void repaintViews() {
        for (View view : views) {
            view.paint();
        }
    }

    /**
     * Invokes all events matching the given event name.
     *
     * @param eventName the event name
     */
    public void invokeEvent(String eventName) {
        eventManager.invokeEvent(eventName);
    }

    /**
     * Adds an event listener.
     *
     * @param event the event listener
     * @return the added event
     */
    public Event addEventListener(Event event) {
        return eventManager.addEventListener(event);
    }

    /**
     * Removes an event listener.
     *
     * @param event the event listener to remove
     */
    public void removeEventListener(Event event) {
        eventManager.removeEventListener(event);
    }

    /**
     * Returns the serialization manager.
     *
     * @return the serialization manager
     */
    public SerializationManager getSerializationManager() {
        return serializationManager;
    }

    /**
     * Returns the game title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the active controller.
     *
     * @return the controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Returns all registered views.
     *
     * @return the list of views
     */
    public ArrayList<View> getViews() {
        return views;
    }

    /**
     * Returns the first registered view.
     * Useful when the game only needs one main graphical view.
     *
     * @return the first view
     */
    public View getMainView() {
        if (views.isEmpty()) {
            return null;
        }

        return views.get(0);
    }
}