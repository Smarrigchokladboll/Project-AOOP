package Framework;

import javax.swing.JFrame;

/**
 * Base class for views.
 * 
 * A view displays the current game state.
 * Concrete games can implement several different views, for example
 * a graphical Swing view and a console view.
 */
public abstract class View extends JFrame {

    protected Game game;

    /**
     * Called when the view is added to the game.
     */
    public abstract void init();

    /**
     * Updates or redraws the view.
     */
    public abstract void paint();

    /**
     * Sets the game instance used by this view.
     *
     * @param game the game
     */
    public void setGame(Game game) {
        this.game = game;
    }
}