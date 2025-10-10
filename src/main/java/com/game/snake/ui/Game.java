package com.game.snake.ui;

import java.awt.*;
import javax.swing.*;

public class Game {
    private GamePanel gamePanel;

    public Game(int width, int height, Color snakeColor) {
        this.gamePanel = new GamePanel(width, height, snakeColor, this);
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public void start() {
        gamePanel.requestFocus();
    }
}
