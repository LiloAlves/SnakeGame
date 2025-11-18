package com.game.snake.ui;

import java.awt.*;

import lombok.Getter;

@Getter
public class Game {
    private GamePanel gamePanel;

    public Game(int width, int height, Color snakeColor) {
        this.gamePanel = new GamePanel(width, height, snakeColor, this);
    }

    public void start() {
        gamePanel.requestFocus();
    }
}
