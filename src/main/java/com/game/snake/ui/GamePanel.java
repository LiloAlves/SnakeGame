package com.game.snake.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.game.snake.core.GameEngine;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int width;
    private final int height;
    private final int blockSize = 25;

    private GameEngine engine;
    private Timer timer;
    private JButton restartButton;
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

        timer = new Timer(engine.getDelayMs(), this);
        timer.start();

        restartButton = new JButton("Restart");
        restartButton.setFocusable(false);
        restartButton.addActionListener(e -> restartGame());
        setLayout(new BorderLayout());
        add(restartButton, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!engine.isGameOver()) {
            engine.render(g);
        } else {
            drawGameOverScreen(g);
        }
    }

    private void drawGameOverScreen(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String gameOverMessage = "Game Over";
        String finalScoreMessage = "Final Score: " + engine.getScore();
        FontMetrics fm = g.getFontMetrics();

        int x = (width - fm.stringWidth(gameOverMessage)) / 2;
        int y = height / 2 - 20;
        g.drawString(gameOverMessage, x, y);
        x = (width - fm.stringWidth(finalScoreMessage)) / 2;
        g.drawString(finalScoreMessage, x, y + 40);
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
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
