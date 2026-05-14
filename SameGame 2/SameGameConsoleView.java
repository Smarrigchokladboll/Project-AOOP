package SameGame;

import Framework.View;

public class SameGameConsoleView extends View {

    @Override
    public void init() {
        // Console view behöver inte visa något fönster.
    }

    @Override
    public void paint() {
        SameGame sameGame = (SameGame) game;
        int[][] board = sameGame.getBoard();

        System.out.println();
        System.out.println("SameGame");
        System.out.println("Score: " + sameGame.getScore());

        for (int row = 0; row < SameGame.ROWS; row++) {
            String line = "";

            for (int col = 0; col < SameGame.COLS; col++) {
                line += board[row][col] + " ";
            }

            System.out.println(line);
        }
    }
}