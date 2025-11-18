package com.game.snake.core;

import java.awt.*;
import java.util.*;

import com.game.snake.entities.Food;
import com.game.snake.entities.Snake;
import com.game.snake.entities.Obstacle;

public class GameEngine {
    private final int width;
    private final int height;
    private final int blockSize;
    private final SpeedController speed;

    private Snake snake;
    private Food food;
    private boolean gameOver;
    private boolean paused = false;

    private final Random random = new Random();
    private final java.util.List<Obstacle> obstacles = new ArrayList<>();
    private int fruitsEatenCounter = 0;

    public GameEngine(int width, int height, int blockSize, Color snakeColor) {
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        this.speed = new SpeedController(8, 2, 24, 5);
        reset(snakeColor);
    }

    private int playableHeight() {
        return height - UiConstants.HUD_HEIGHT;
    }

    public void update() {
        if (gameOver || paused)
            return;

        if (snakeStarted()) {
            snake.move();

            if (snake.checkCollision(food.getPosition())) {
                snake.grow();
                speed.onFruitEaten();
                fruitsEatenCounter++;
                SoundPlayer.play("eat.wav");

                if (fruitsEatenCounter % 5 == 0) {
                    addRandomObstacle();
                }

                food.generateNewPosition(width, playableHeight(), blockSize);

            }

            if (snakeOutOfBounds() || checkSelfCollision() || hitObstacle()) {
                SoundPlayer.play("gameover.wav");
                gameOver = true;
            }
        }
    }

    private boolean hitObstacle() {
        Point head = snake.getHead();
        for (Obstacle o : obstacles) {
            if (head.equals(o.getPosition()))
                return true;
        }
        return false;
    }

    private void addRandomObstacle() {

        for (int attempt = 0; attempt < 200; attempt++) {
            int maxX = width / blockSize;
            int maxY = playableHeight() / blockSize;
            int pointX = random.nextInt(Math.max(1, maxX));
            int pointY = random.nextInt(Math.max(1, maxY));
            Point points = new Point(pointX, pointY);

            if (isFreeCell(points)) {
                obstacles.add(new Obstacle(points));
                return;
            }
        }
    }

    private boolean isFreeCell(Point points) {
        if (points.equals(food.getPosition()))
            return false;

        for (Point part : snake.getBody()) {
            if (points.equals(part))
                return false;
        }

        for (Obstacle o : obstacles) {
            if (points.equals(o.getPosition()))
                return false;
        }
        return true;
    }

    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        drawHud(g2);
        g2.translate(0, UiConstants.HUD_HEIGHT);

        for (Obstacle o : obstacles) {
            o.draw(g2, blockSize);
        }

        food.draw(g2, blockSize);
        snake.draw(g2, blockSize);

        g2.dispose();
    }

    private void drawHud(Graphics2D g) {
        g.setColor(new Color(255, 255, 255, 50));
        g.fillRect(0, 0, width, UiConstants.HUD_HEIGHT);

        g.setFont(new Font("DialogInput", Font.BOLD, 13));
        g.setColor(Color.WHITE);

        g.drawString("Score: " + getScore() + "    TPS: " + speed.getTps(), 10, 20);

    }

    public void reset(Color snakeColor) {
        this.snake = new Snake(5, 5, snakeColor);
        this.food = new Food(width, playableHeight(), blockSize);
        this.gameOver = false;
        this.paused = false;
        this.obstacles.clear();
        this.fruitsEatenCounter = 0;
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

    public void setDirection(int directionX, int directionY) {
        snake.setDirection(directionX, directionY);
    }
}
