package entity;
import javafx.animation.AnimationTimer;

public class PlayerTimer extends AnimationTimer {

    private long time = 0L;

    public long getTime() {
        return time;
    }

    @Override
    public void start() {
        super.start();
        time = 0;
    }

    @Override
    public void handle(long now) {
        time++;
    }
}
