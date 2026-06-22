import greenfoot.*;
import java.util.List;

public class GameManager extends Actor {
    public static boolean isPaused = false;

    private int score = 0;
    private int timeLeft = 5400;
    private boolean levelEnded = false;

    public GameManager() {
        getImage().clear();
        isPaused = false;
    }

    protected void addedToWorld(World world) {
 
        if (world.getObjects(SpawnClient.class).isEmpty()) {
            world.addObject(new SpawnClient(), world.getWidth() / 2, 0);
        }
    }

    public void act() {
        if (isPaused || levelEnded) {
            return;
        }
        countDownTime();
        updateUI();
    }

    private void countDownTime() {
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            endLevel();
        }
    }

    public void addScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    private void updateUI() {
        int seconds = timeLeft / 60;
        getWorld().showText("SCORE: " + score, 120, 25);
        getWorld().showText("TIME: " + seconds + "s", 120, 55);
    }

    private void endLevel() {
        levelEnded = true;
        getWorld().showText("LEVEL COMPLETED!", getWorld().getWidth() / 2, getWorld().getHeight() / 2);
        getWorld().showText("FINAL SCORE: " + score, getWorld().getWidth() / 2, (getWorld().getHeight() / 2) + 40);
        Greenfoot.stop();
    }
}