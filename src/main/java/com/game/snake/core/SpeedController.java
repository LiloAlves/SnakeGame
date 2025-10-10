package com.game.snake.core;

public class SpeedController {
    private final int baseTps;
    private final int stepTps;
    private final int maxTps;
    private final int fruitsPerStep;
    private int currentTps;
    private int fruitsEaten;

    public SpeedController(int baseTps, int stepTps, int maxTps, int fruitsPerStep) {
        this.baseTps = baseTps;
        this.stepTps = stepTps;
        this.maxTps = maxTps;
        this.fruitsPerStep = fruitsPerStep;
        this.currentTps = baseTps;
        this.fruitsEaten = 0;
    }

    public void onFruitEaten() {
        fruitsEaten++;
        if (fruitsEaten % fruitsPerStep == 0) {
            currentTps = Math.min(maxTps, currentTps + stepTps);
        }
    }

    public int delayMs() {
        return Math.max(1, 1000 / Math.max(1, currentTps));
    }

    public int getTps() {
        return currentTps;
    }

    public void reset() {
        currentTps = baseTps;
        fruitsEaten = 0;
    }
}
