package com.game.snake.app;

import java.awt.Color;
import javax.swing.*;

import com.game.snake.ui.Game;

public class App {
    public static void main(String[] args) {
        int width = 700;
        int height = width;

        Color snakeColor = JColorChooser
                .showDialog(null, "Escolha a cor da cobra", Color.GREEN);
        if (snakeColor == null) {
            snakeColor = Color.GREEN;
        }

        JFrame window = new JFrame("Snake Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        Game game = new Game(width, height, snakeColor);
        window.add(game.getGamePanel());

        window.pack();
        window.setVisible(true);
        game.start();
    }
}
