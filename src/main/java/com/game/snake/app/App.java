package com.game.snake.app;

import javax.swing.*;
import com.game.snake.ui.MenuPanel;

public class App {
    public static void main(String[] args) {
        int width = 700;
        int height = width;

        JFrame window = new JFrame("Snake Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        MenuPanel menu = new MenuPanel(window, width, height);
        window.setContentPane(menu);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
