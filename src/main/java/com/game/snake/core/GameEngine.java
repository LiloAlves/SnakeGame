package com.game.snake.core;

import java.awt.*;
import com.game.snake.entities.Food;
import com.game.snake.entities.Snake;

public class GameEngine {
    private static final int HUD_HEIGHT = 30;

    private final int width;
    private final int height;
    private final int blockSize;
    private final SpeedController speed;
    private Snake snake;
    private Food food;
    private boolean gameOver;
    private boolean paused = false;

    public GameEngine(int width, int height, int blockSize, Color snakeColor) {
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        this.speed = new SpeedController(8, 2, 24, 5);
        reset(snakeColor);
    }

    private int playableHeight() {
        return height - HUD_HEIGHT;
    }

    public void update() {
        if (gameOver || paused)
            return;

        if (snakeStarted()) {
            snake.move();

            if (snake.checkCollision(food.getPosition())) {
                snake.grow();
                speed.onFruitEaten();
                SoundPlayer.play("eat.wav");
                food.generateNewPosition(width, playableHeight(), blockSize);

            }

            if (snakeOutOfBounds() || checkSelfCollision()) {
                SoundPlayer.play("gameover.wav");
                gameOver = true;
            }
        }
    }

    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        drawHud(g2);
        g2.translate(0, HUD_HEIGHT);
        food.draw(g2, blockSize);
        snake.draw(g2, blockSize);

        g2.dispose();
    }

    private void drawHud(Graphics2D g) {

        g.setColor(new Color(255, 255, 255, 50));
        g.fillRect(0, 0, width, HUD_HEIGHT);

        g.setFont(new Font("DialogInput", Font.BOLD, 13));
        g.setColor(Color.WHITE);

        g.drawString("Score: " + getScore() + "    TPS: " + speed.getTps(), 10, 20);
        g.getFontMetrics();

    }

    public void reset(Color snakeColor) {
        this.snake = new Snake(5, 5, snakeColor);
        this.food = new Food(width, playableHeight(), blockSize);
        this.gameOver = false;
        this.paused = false;
        speed.reset();
    }

    private boolean checkSelfCollision() {
        Point head = snake.getHead();
        for (int i = 1; i < snake.getBody().size(); i++) {
            if (head.equals(snake.getBody().get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean snakeOutOfBounds() {
        return snake.outOfBounds(width, playableHeight(), blockSize);
    }

    private boolean snakeStarted() {
        return snake.getVelocityX() != 0 || snake.getVelocityY() != 0;
    }

    public int getScore() {
        return snake.getBody().size() - 1;
    }

    public int getDelayMs() {
        return speed.delayMs();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isPaused() {
        return paused;
    }

    public void togglePause() {
        if (!gameOver) {
            paused = !paused;
        }
    }

    public void restart(Color snakeColor) {
        reset(snakeColor);
        paused = false;
    }

    public void setDirection(int dx, int dy) {
        snake.setDirection(dx, dy);
    }
}
