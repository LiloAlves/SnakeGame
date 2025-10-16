package com.game.snake.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.game.snake.core.GameEngine;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private static final int HUD_HEIGHT = 30;
    private final int width;
    private final int height;
    private final int blockSize = 25;

    private GameEngine engine;
    private Timer timer;
    private JButton restartButton;
    private JButton pausedButton;
    private Color snakeColor;

    public GamePanel(int width, int height, Color snakeColor, Game game) {
        this.width = width;
        this.height = height;
        this.snakeColor = snakeColor;

        this.engine = new GameEngine(width, height, blockSize, snakeColor);

        setPreferredSize(new Dimension(width, height + 25));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        setLayout(new BorderLayout());

        JPanel hudPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        hudPanel.setOpaque(false);
        hudPanel.setPreferredSize(new Dimension(1, HUD_HEIGHT));
        add(hudPanel, BorderLayout.NORTH);

        pausedButton = new JButton("⏯");
        pausedButton.setToolTipText("Pause/Resume (Space)");
        pausedButton.setFocusable(false);
        pausedButton.setFont(new Font("Dialog", Font.BOLD, 16));

        pausedButton.setForeground(Color.WHITE);
        pausedButton.setBorderPainted(false);
        pausedButton.addActionListener(e -> {
            engine.togglePause();
            if (engine.isPaused())
                timer.stop();
            else
                timer.start();
            repaint();
        });
        hudPanel.add(pausedButton);

        restartButton = new JButton("Restart");
        restartButton.setFocusable(false);
        restartButton.addActionListener(e -> restartGame());
        add(restartButton, BorderLayout.SOUTH);

        timer = new Timer(engine.getDelayMs(), this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        pausedButton.setVisible(!engine.isGameOver());

        if (!engine.isGameOver()) {
            engine.render(g);

            if (engine.isPaused()) {
                drawPausedOverlay(g);
            }
        } else {
            drawGameOverScreen(g);
        }
    }

    private void drawGameOverScreen(Graphics g) {

        String gameOverMessage = "GAME OVER";

        g.setFont(new Font("Monospaced", Font.BOLD, 70));
        FontMetrics fm = g.getFontMetrics();

        int x = (width - fm.stringWidth(gameOverMessage)) / 2;
        int y = height / 2 - 30;

        g.setColor(Color.GREEN);
        g.drawString(gameOverMessage, x + 2, y + 2);

        g.setColor(Color.RED);
        g.drawString(gameOverMessage, x, y);

        g.setColor(new Color(0, 255, 0));
        g.drawString(gameOverMessage, x - 1, y - 1);

        String finalScoreMessage = "Final Score: " + engine.getScore();

        g.setFont(new Font("Monospaced", Font.PLAIN, 20));
        FontMetrics fmScore = g.getFontMetrics();

        x = (width - fmScore.stringWidth(finalScoreMessage)) / 2;

        g.setColor(Color.WHITE);
        g.drawString(finalScoreMessage, x, y + 60);
    }

    private void drawPausedOverlay(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRect(0, 0, width, height);

        String txt = "PAUSED";
        g2.setFont(new Font("Monospaced", Font.BOLD, 35));
        FontMetrics fm = g2.getFontMetrics();
        int x = (width - fm.stringWidth(txt)) / 2;
        int y = (height - fm.getHeight()) / 2 + fm.getAscent();

        g2.setColor(Color.RED);
        g2.drawString(txt, x + 3, y + 3);
        g2.setColor(Color.GREEN);
        g2.drawString(txt, x, y);

        g2.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!engine.isGameOver()) {
            engine.update();
            timer.setDelay(engine.getDelayMs());
        }
        repaint();
    }

    public void restartGame() {
        engine.restart(snakeColor);
        timer.setDelay(engine.getDelayMs());
        timer.restart();
        pausedButton.setVisible(true);
        requestFocus();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> engine.setDirection(0, -1);
            case KeyEvent.VK_DOWN -> engine.setDirection(0, 1);
            case KeyEvent.VK_LEFT -> engine.setDirection(-1, 0);
            case KeyEvent.VK_RIGHT -> engine.setDirection(1, 0);

            case KeyEvent.VK_SPACE -> {
                engine.togglePause();
                if (engine.isPaused())
                    timer.stop();
                else
                    timer.start();
                repaint();
            }
            case KeyEvent.VK_R -> {
                restartGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
