package game;

import entity.Player;
import level.Level;
import level.Tile;
import level.TileType;

import java.util.ArrayList;
import java.util.List;

public abstract class MazeGame {

    private int aspectRatio = 16;

    private List<Player> players = new ArrayList<>();
    private List<Level> availableLevels = new ArrayList<>();
    private Level currentLevel = null;
    private Player currentlyPlaying = null;

    public void addAll(List<Level> levels) {
        this.availableLevels.addAll(levels);
    }

    public void addLevel(Level level) {
        this.availableLevels.add(level);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setAvailableLevels(List<Level> availableLevels) {
        this.availableLevels = availableLevels;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public List<Level> getAvailableLevels() {
        return availableLevels;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void setCurrentlyPlaying(Player currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public Player getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public boolean newPlayerMove(Player player, double distX, double distY) {
        double x = player.getX();
        double y = player.getY();

        double futureX = x + (distX);
        double futureY = y + (distY);

        double xMod = 0.0;
        double yMod = 0.0;

        if (distX < 0) {
            xMod = 8;
        }

        if (distY < 0) {
            yMod = 8;
        }

        Tile next = currentLevel.newTileAt(futureX - xMod, futureY - yMod);

        if (next != null) {
            if (next.getType() == TileType.END) {
                player.setCompleted(true);
                playerFinished(player);
            }
        }

        if (next == null) {
            System.out.println("Null tiles");
            return false;
        }

        return next.getType() != TileType.WALL;
    }

    public boolean playerCanMove(Player player, int distX, int distY) {

        int y = (int) player.getY();
        int x = (int) player.getX();

        int checkX;
        int checkY;

        checkX = x + distX;
        checkY = y + distY;

        boolean conflict = false;

        if (checkX > x || checkY > y) {
            for (int x1 = x; x1 < checkX; x1++) {
                for (int y1 = y; y1 < checkY; y1++) {
                    Tile at = currentLevel.tileAt(x1, y1);

                    if (at == null || at.getType() == TileType.WALL) {
                        conflict = true;
                    }
                }
            }
        }
        else if (checkX < x || checkY < y) {
            for (int x1 = x; x1 > checkX; x1--) {
                for (int y1 = y; y1 > checkY; y1--) {
                    Tile at = currentLevel.tileAt(x1, y1);

                    if (at == null || at.getType() == TileType.WALL) {
                        conflict = true;
                    }
                }
            }
        }
        return !conflict;
    }


    public abstract void playerFinished(Player player);

    public abstract void display();

    public abstract void start();

    public abstract void setup();
}
