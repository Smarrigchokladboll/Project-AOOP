package SameGame;

import java.util.ArrayList;
import java.util.Random;

import Framework.AudioEvent;
import Framework.Controller;
import Framework.Game;
import Framework.View;

public class SameGame extends Game {

    public static final int TILE_EMPTY = 0;
    public static final int TILE_RED = 1;
    public static final int TILE_BLUE = 2;
    public static final int TILE_GREEN = 3;
    public static final int TILE_YELLOW = 4;

    public static final int ROWS = 10;
    public static final int COLS = 20;
    public static final int TILE_SIZE = 36;
    public static final int COLORS = 4;

    private int[][] board;
    private int score;

    public SameGame() {
        super("SameGame");
    }

    @Override
    protected void init() {
        board = new int[ROWS][COLS];
        score = 0;

        generateBoard();

        /*
         * Lägg bara till ljud om ljudfilerna faktiskt finns.
         * Annars kan du kommentera bort dessa tre rader.
         */

        //addEventListener(new AudioEvent("tiles-removed", "res/SameGame/remove.wav"));
        //addEventListener(new AudioEvent("invalid-move", "res/SameGame/invalid.wav"));
        //addEventListener(new AudioEvent("game-over", "res/SameGame/game-over.wav"));
        
        //sameGame.invokeEvent("tiles-removed"); 
    }

    @Override
    protected Controller createController() {
        return new SameGameController();
    }

    @Override
    protected View[] createViews() {
        return new View[] {
            new SameGameView(),
            new SameGameConsoleView()
        };
    }

    private void generateBoard() {
        Random random = new Random();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = 1 + random.nextInt(COLORS);
            }
        }
    }

    public void selectTile(int row, int col) {
        if (!isInside(row, col)) {
            return;
        }

        int tile = board[row][col];

        if (tile == TILE_EMPTY) {
            invokeEvent("invalid-move");
            return;
        }

        ArrayList<int[]> group = findGroup(row, col, tile);

        if (group.size() < 2) {
            invokeEvent("invalid-move");
            return;
        }

        removeGroup(group);
        score += calculateScore(group.size());

        applyGravity();
        shiftColumnsLeft();

        invokeEvent("tiles-removed");
        repaintViews();

        if (!hasMovesLeft()) {
            invokeEvent("game-over");
        }
    }

    private ArrayList<int[]> findGroup(int startRow, int startCol, int tile) {
        ArrayList<int[]> group = new ArrayList<>();
        boolean[][] visited = new boolean[ROWS][COLS];

        findGroupRecursive(startRow, startCol, tile, visited, group);

        return group;
    }

    private void findGroupRecursive(
            int row,
            int col,
            int tile,
            boolean[][] visited,
            ArrayList<int[]> group
    ) {
        if (!isInside(row, col)) {
            return;
        }

        if (visited[row][col]) {
            return;
        }

        if (board[row][col] != tile) {
            return;
        }

        visited[row][col] = true;
        group.add(new int[] { row, col });

        findGroupRecursive(row - 1, col, tile, visited, group);
        findGroupRecursive(row + 1, col, tile, visited, group);
        findGroupRecursive(row, col - 1, tile, visited, group);
        findGroupRecursive(row, col + 1, tile, visited, group);
    }

    private void removeGroup(ArrayList<int[]> group) {
        for (int[] position : group) {
            int row = position[0];
            int col = position[1];

            board[row][col] = TILE_EMPTY;
        }
    }

    private int calculateScore(int removedTiles) {
        return removedTiles * removedTiles;
    }

    private void applyGravity() {
        for (int col = 0; col < COLS; col++) {
            int writeRow = ROWS - 1;

            for (int row = ROWS - 1; row >= 0; row--) {
                if (board[row][col] != TILE_EMPTY) {
                    board[writeRow][col] = board[row][col];

                    if (writeRow != row) {
                        board[row][col] = TILE_EMPTY;
                    }

                    writeRow--;
                }
            }
        }
    }

    private void shiftColumnsLeft() {
        int writeCol = 0;

        for (int col = 0; col < COLS; col++) {
            if (!isColumnEmpty(col)) {
                if (writeCol != col) {
                    moveColumn(col, writeCol);
                    clearColumn(col);
                }

                writeCol++;
            }
        }
    }

    private boolean isColumnEmpty(int col) {
        for (int row = 0; row < ROWS; row++) {
            if (board[row][col] != TILE_EMPTY) {
                return false;
            }
        }

        return true;
    }

    private void moveColumn(int fromCol, int toCol) {
        for (int row = 0; row < ROWS; row++) {
            board[row][toCol] = board[row][fromCol];
        }
    }

    private void clearColumn(int col) {
        for (int row = 0; row < ROWS; row++) {
            board[row][col] = TILE_EMPTY;
        }
    }

    private boolean hasMovesLeft() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int tile = board[row][col];

                if (tile == TILE_EMPTY) {
                    continue;
                }

                if (isInside(row + 1, col) && board[row + 1][col] == tile) {
                    return true;
                }

                if (isInside(row, col + 1) && board[row][col + 1] == tile) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isInside(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public void resetGame() {
        score = 0;
        generateBoard();
        repaintViews();
    }
}