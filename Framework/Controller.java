package Framework;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * This class handles all the user inputs and is a part of the Model-View-Controller pattern.
 */
public abstract class Controller implements KeyListener, MouseListener {
    
    protected Game game;
    protected ArrayList<View> views;

    /**
    * Initial function which is called after the essantial local variables have been assigned.
    */
    public abstract void init();

    /**
    * Sets the local game variable to the specified argument.
    * @param game the game to be used.
    * @see         Game
    */
    public void setGame(Game game) {
        this.game = game;
    }
    /**
    * Sets the local view variable to the specified argument.
    * @param view the view to be used.
    * @see         View
    */
    public void setViews(ArrayList<View> views) {
        this.views = views;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e){
    }
    @Override
    public void mouseReleased(MouseEvent e){
    }
    @Override
    public void mouseEntered(MouseEvent e){
    }
    @Override
    public void mouseExited(MouseEvent e){ 
    }
}
