package com.game.snake.core;

import java.awt.*;
import com.game.snake.entities.Food;
import com.game.snake.entities.Snake;

public class GameEngine {
    private final int width;
    private final int height;
    private final int blockSize;
    private final SpeedController speed;
    private Snake snake;
    private Food food;
    private boolean gameOver;

    public GameEngine(int width, int height, int blockSize, Color snakeColor) {
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        this.speed = new SpeedController(8, 2, 24, 5);
        reset(snakeColor);
    }

    public void update() {
        if (gameOver)
            return;

        if (snakeStarted()) {
            snake.move();

            if (snake.checkCollision(food.getPosition())) {
                snake.grow();
                food.generateNewPosition(width, height, blockSize);
                speed.onFruitEaten();
            }

            if (snakeOutOfBounds() || checkSelfCollision()) {
                gameOver = true;
            }
        }
    }

    public void render(Graphics g) {
        food.draw(g, blockSize);
        snake.draw(g, blockSize);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + getScore() + "      TPS: " + speed.getTps(), 10, 20);
    }

    public void reset(Color snakeColor) {
        this.snake = new Snake(5, 5, snakeColor);
        this.food = new Food(width, height, blockSize);
        this.gameOver = false;
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
        return snake.outOfBounds(width, height, blockSize);
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

    public void restart(Color snakeColor) {
        reset(snakeColor);
    }

    public void setDirection(int dx, int dy) {
        snake.setDirection(dx, dy);
    }
}
