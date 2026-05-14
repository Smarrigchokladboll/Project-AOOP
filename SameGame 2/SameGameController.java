package SameGame;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Framework.Controller;

public class SameGameController extends Controller {

    private static final int BOARD_X = 20;
    private static final int BOARD_Y = 40;

    @Override
    public void init() {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        SameGame sameGame = (SameGame) game;

        System.out.println(e.getX() + ", " + e.getY());

        int col = (e.getX() - BOARD_X) / SameGame.TILE_SIZE;
        int row = (e.getY() - BOARD_Y) / SameGame.TILE_SIZE;

        sameGame.selectTile(row, col);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        SameGame sameGame = (SameGame) game;

        if (e.getKeyCode() == KeyEvent.VK_R) {
            sameGame.resetGame();
        }
    }
}