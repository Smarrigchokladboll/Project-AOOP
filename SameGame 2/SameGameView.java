package SameGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Framework.View;

public class SameGameView extends View {

    private JPanel panel;

    private static final int BOTTOM_BAR_HEIGHT = 40;

    @Override
    public void init() {
        setTitle("Same Game");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGame((Graphics2D) g);
            }
        };

        int width = SameGame.COLS * SameGame.TILE_SIZE;
        int height = SameGame.ROWS * SameGame.TILE_SIZE + BOTTOM_BAR_HEIGHT;

        panel.setPreferredSize(new Dimension(width, height));
        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void drawGame(Graphics2D g) {
        SameGame sameGame = (SameGame) game;
        int[][] board = sameGame.getBoard();

        int boardWidth = SameGame.COLS * SameGame.TILE_SIZE;
        int boardHeight = SameGame.ROWS * SameGame.TILE_SIZE;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g);

        drawBoardFrame(g, 0, 0, boardWidth, boardHeight);

        for (int row = 0; row < SameGame.ROWS; row++) {
            for (int col = 0; col < SameGame.COLS; col++) {
                drawBall(g, board[row][col], row, col);
            }
        }

        drawBottomBar(g, sameGame, boardHeight);
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

        g.setColor(new Color(40, 40, 40));

        for (int x = 0; x < panel.getWidth(); x += 16) {
            for (int y = 0; y < panel.getHeight(); y += 16) {
                g.fillOval(x, y, 2, 2);
            }
        }
    }

    private void drawBoardFrame(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);

        g.setColor(new Color(180, 180, 180));
        g.setStroke(new BasicStroke(2));
        g.drawRect(x, y, width, height);
    }

    private void drawBall(Graphics2D g, int tile, int row, int col) {
        if (tile == SameGame.TILE_EMPTY) {
            return;
        }

        Color baseColor = getTileColor(tile);

        int margin = 7;
        int x = col * SameGame.TILE_SIZE + margin;
        int y = row * SameGame.TILE_SIZE + margin;
        int size = SameGame.TILE_SIZE - margin * 2;

        Color brighter = baseColor.brighter();
        Color darker = baseColor.darker().darker();

        GradientPaint gradient = new GradientPaint(
                x,
                y,
                brighter,
                x + size,
                y + size,
                darker
        );

        g.setPaint(gradient);
        g.fillOval(x, y, size, size);

        g.setColor(darker);
        g.setStroke(new BasicStroke(2));
        g.drawOval(x, y, size, size);

        g.setColor(new Color(255, 255, 255, 120)); //
        g.fillOval(x + size / 4, y + size / 5, size / 5, size / 5);//
    }

    private Color getTileColor(int tile) {
        switch (tile) {
            case SameGame.TILE_RED:
                return new Color(220, 20, 20);
            case SameGame.TILE_BLUE:
                return new Color(20, 40, 220);
            case SameGame.TILE_GREEN:
                return new Color(30, 220, 30);
            case SameGame.TILE_YELLOW:
                return new Color(230, 210, 40);
            default:
                return Color.GRAY;
        }
    }

    private void drawBottomBar(Graphics2D g, SameGame sameGame, int y) {
        g.setFont(new Font("Monospaced", Font.BOLD, 18));

        g.setColor(new Color(20, 20, 20));
        g.fillRect(0, y, panel.getWidth(), BOTTOM_BAR_HEIGHT);

        g.setColor(Color.YELLOW);
        g.drawString("Score: " + sameGame.getScore(), 20, y + 25);

        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Restart: [R]", 220, y + 25);
    }

    @Override
    public void paint() {
        repaint();
    }
}