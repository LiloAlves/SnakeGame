package com.game.snake.ui;

import javax.swing.*;
import java.awt.*;
import com.game.snake.ui.Game;

public class MenuPanel extends JPanel {

    private final JFrame frame;
    private final int width;
    private final int height;
    private JButton startButton;

    public MenuPanel(JFrame frame, int width, int height) {
        this.frame = frame;
        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setLayout(null);

        startButton = new JButton("START");
        startButton.setFont(new Font("Monospaced", Font.BOLD, 25));
        startButton.setFocusable(false);

        startButton.setBounds((width - 180) / 2, (height / 2) + 10, 180, 40);

        startButton.setBackground(Color.LIGHT_GRAY);
        startButton.setForeground(Color.BLACK);

        startButton.setOpaque(true);
        startButton.setContentAreaFilled(true);
        startButton.setFocusPainted(false);

        startButton.setBorderPainted(true);
        startButton.setBorder(new javax.swing.border.LineBorder(Color.WHITE, 1, true)); // cantos arredondados

        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                startButton.setBackground(new Color(230, 230, 230)); // leve cinza
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                startButton.setBackground(Color.LIGHT_GRAY); // volta ao branco
            }
        });

        add(startButton);

        JTextArea help = new JTextArea("""
                Controls:

                • Arrow Keys: move
                • Space: pause / resume
                • R: restart
                • Eat 5 fruits: +2 TPS (speed)
                """);
        help.setEditable(false);
        help.setForeground(Color.WHITE);
        help.setBackground(new Color(0, 0, 0, 0));
        help.setFont(new Font("Monospaced", Font.PLAIN, 14));
        help.setBounds(20, height - 120, width - 40, 100);
        help.setOpaque(false);
        add(help);

        startButton.addActionListener(e -> startGameFlow());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        String title = "SNAKE GAME";
        g2.setFont(new Font("Monospaced", Font.BOLD, 70));
        FontMetrics fm = g2.getFontMetrics();

        int x = (width - fm.stringWidth(title)) / 2;
        int y = height / 2 - 60;

        g2.setColor(Color.GREEN);
        g2.drawString(title, x + 2, y + 2);

        g2.setColor(Color.RED);
        g2.drawString(title, x, y);

        g2.setColor(new Color(0, 255, 0));
        g2.drawString(title, x - 1, y - 1);

        g2.dispose();
    }

    private void startGameFlow() {

        Color snakeColor = JColorChooser.showDialog(frame, "Escolha a cor da cobra", Color.GREEN);
        if (snakeColor == null)
            snakeColor = Color.GREEN;

        Game game = new Game(width, height, snakeColor);

        frame.setContentPane(game.getGamePanel());
        frame.revalidate();
        frame.pack();
        frame.setLocationRelativeTo(null);

        game.start();
    }
}
