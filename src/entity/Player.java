package entity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    private String boardIcon;
    private boolean completed;
    private double velocity = 4;

    private String direction = "right";

    private PlayerTimer timer = new PlayerTimer();
    private int levelCount = 1;

    private double recentTime;

    private Map<String, Double> playerScores = new HashMap<>();

    public Player(String name, String boardIcon, double x, double y) {
        super(name, x, y);
        this.boardIcon = boardIcon;
        this.completed = false;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public double getVelocity() {
        return velocity;
    }

    public String getBoardIcon() {
        return boardIcon;
    }

    public void setBoardIcon(String boardIcon) {
        this.boardIcon = boardIcon;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        timer.stop();
        DecimalFormat df2 = new DecimalFormat(".##");
        addScore("level" + levelCount, Double.valueOf(df2.format(timer.getTime() / 60.0)));
        this.setRecentTime(Double.valueOf(df2.format(timer.getTime() / 60.0)));
        levelCount++;
    }

    public PlayerTimer getTimer() {
        return timer;
    }

    public void setTimer(PlayerTimer timer) {
        this.timer = timer;
    }

    public double getRecentTime() {
        return recentTime;
    }

    public void setRecentTime(double recentTime) {
        this.recentTime = recentTime;
    }

    public Map<String, Double> getPlayerScores() {
        return playerScores;
    }

    public void addScore(String levelName, double recentTime) {
        this.playerScores.put(levelName, recentTime);
    }

    public Double getFinalScore() {
        double sum = 0.0;
        for (double d : playerScores.values()) {
            sum += d;
        }

        DecimalFormat df2 = new DecimalFormat(".##");
        return Double.valueOf(df2.format(sum));
    }

    public String getBestLevel() {
        double best = 1000000.0;
        String levelName = "";

        for (Map.Entry<String, Double> entry : playerScores.entrySet()) {
            if (entry.getValue() < best) {
                best = entry.getValue();
                levelName = entry.getKey();
            }
        }
        return levelName;
    }

    public double getTimeForLevel(String level) {
        return playerScores.get(level);
    }
}

