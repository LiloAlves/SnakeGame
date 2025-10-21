package com.game.snake.app;

import javax.swing.*;
import com.game.snake.ui.MenuPanel;

public class App {
    public static void main(String[] args) {
        int largura = 700;
        int altura = largura;

        JFrame window = new JFrame("Snake Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        MenuPanel menu = new MenuPanel(window, largura, altura);
        window.setContentPane(menu);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
