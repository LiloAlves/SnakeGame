package com.game.snake.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.game.snake.core.GameEngine;
import com.game.snake.core.SoundPlayer;
import com.game.snake.core.UiConstants;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int width;
    private final int height;
    private final int blockSize = 25;

    private GameEngine engine;
    private Timer timer;
    private boolean soundOn = true;
    private Color snakeColor;

    private JButton restartButton;
    private JButton pausedButton;
    private JButton soundButton;

    public GamePanel(int width, int height, Color snakeColor, Game game) {
        this.width = width;
        this.height = height;
        this.snakeColor = snakeColor;

        this.engine = new GameEngine(width, height, blockSize, snakeColor);

        setPreferredSize(new Dimension(width, height + UiConstants.HUD_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        setLayout(new BorderLayout());

        JPanel hudPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 1, 2));
        hudPanel.setOpaque(false);
        hudPanel.setPreferredSize(new Dimension(1, UiConstants.HUD_HEIGHT));
        add(hudPanel, BorderLayout.NORTH);

        soundButton = new JButton();
        soundButton.setFocusable(false);
        soundButton.setFont(new Font("Dialog", Font.BOLD, 16));
        soundButton.setForeground(Color.WHITE);
        soundButton.setOpaque(false);
        soundButton.setBorderPainted(false);
        updateSoundButtonUI();
        soundButton.addActionListener(e -> toggleSound());
        hudPanel.add(soundButton);

        pausedButton = new JButton("⏯");
        pausedButton.setToolTipText("Pause/Resume (Space)");
        pausedButton.setFocusable(false);
        pausedButton.setFont(new Font("Dialog", Font.BOLD, 16));
        pausedButton.setForeground(Color.BLACK);

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

    private void toggleSound() {
        soundOn = !soundOn;
        SoundPlayer.setMuted(!soundOn);
        updateSoundButtonUI();
        requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        pausedButton.setVisible(!engine.isGameOver());
        soundButton.setVisible(!engine.isGameOver());

        if (!engine.isGameOver()) {
            engine.render(g);

            if (engine.isPaused()) {
                drawPausedOverlay(g);
            }
        } else {
            drawGameOverScreen(g);
        }
    }

    private void updateSoundButtonUI() {
        if (soundOn) {
            soundButton.setText("🔊");
            soundButton.setToolTipText("Desligar som");
        } else {
            soundButton.setText("🔇");
            soundButton.setToolTipText("Ligar som");
        }
    }

    private void drawGameOverScreen(Graphics g) {

        String gameOverMessage = "GAME OVER";

        g.setFont(new Font("Monospaced", Font.BOLD, 70));
        FontMetrics fm = g.getFontMetrics();

        int widthX = (width - fm.stringWidth(gameOverMessage)) / 2;
        int heighty = height / 2 - 30;

        g.setColor(Color.GREEN);
        g.drawString(gameOverMessage, widthX + 2, heighty + 2);

        g.setColor(Color.RED);
        g.drawString(gameOverMessage, widthX, heighty);

        String finalScoreMessage = "Final Score: " + engine.getScore();

        g.setFont(new Font("Monospaced", Font.PLAIN, 20));
        FontMetrics fmScore = g.getFontMetrics();

        widthX = (width - fmScore.stringWidth(finalScoreMessage)) / 2;

        g.setColor(Color.WHITE);
        g.drawString(finalScoreMessage, widthX, heighty + 60);
    }

    private void drawPausedOverlay(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRect(0, 0, width, height);

        String txt = "PAUSED";
        g2.setFont(new Font("Monospaced", Font.BOLD, 35));
        FontMetrics fm = g2.getFontMetrics();
        int widthX = (width - fm.stringWidth(txt)) / 2;
        int heighty = (height - fm.getHeight()) / 2 + fm.getAscent();

        g2.setColor(Color.RED);
        g2.drawString(txt, widthX + 3, heighty + 3);
        g2.setColor(Color.GREEN);
        g2.drawString(txt, widthX, heighty);

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
        soundButton.setVisible(true);
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
