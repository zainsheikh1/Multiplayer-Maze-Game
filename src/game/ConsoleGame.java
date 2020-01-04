package game;

import entity.Player;
import level.Level;
import level.LevelLoader;
import level.Tile;
import level.TileType;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class ConsoleGame extends MazeGame {

    private String wallIndicator = "##";
    private String finishIndicator = "$$";
    private String startIndicator = "**";
    private Scanner scanner;

    /*
        Player movement icons ^ > < v

        Each player gets X amount of seconds to finish the level if they do not, they are removed from the game
        next player then begins

        Commands:

        <direction> <amount>

     */

    public String getWallIndicator() {
        return wallIndicator;
    }

    public String getFinishIndicator() {
        return finishIndicator;
    }

    public String getStartIndicator() {
        return startIndicator;
    }

    @Override
    public void display() {

        int lastY = 0;
        int x = (int) getCurrentlyPlaying().getX();
        int py = (int) getCurrentlyPlaying().getY();

        for (Tile tile : getCurrentLevel().getTiles()) {
            int y = tile.getY();
            String toPrint;
            TileType type = tile.getType();

            if (type == TileType.END) {
                toPrint = getFinishIndicator();
            } else if (type == TileType.START) {
                toPrint = getStartIndicator();
            } else if (type == TileType.WALL) {
                toPrint = getWallIndicator();
            } else {
                toPrint = "  ";
            }

            if (getCurrentlyPlaying() != null) {
                if (x == tile.getX() && py == tile.getY()) {
                    toPrint = getCurrentlyPlaying().getBoardIcon();
                }
            }

            if (tile.getX() == 31) {
                toPrint = "\n" + toPrint;
            }

            System.out.print(toPrint);
        }
    }

    @Override
    public void start() {
    	File levelDir = new File("resources/console/level1.png");
        System.out.println(levelDir.getAbsolutePath());
        LevelLoader loader = new LevelLoader(levelDir);
        List<Level> levelsLoaded = loader.load();

        addAll(levelsLoaded);
        Level current = levelsLoaded.get(0);

        setCurrentLevel(current);

        System.out.println("Welcome to MazeGame Console Edition! Enter number of players to begin: ");
        this.scanner = new Scanner(System.in);

        int players = scanner.nextInt();

        int startX = getCurrentLevel().getStart().getX();
        int startY = getCurrentLevel().getStart().getY();

        for (int i = 0; i < players; i++) {
            System.out.println("Okay player " + (i + 1) + " enter your user name:");
            String name = scanner.next();

            Player player = new Player(name, "> ", startX, startY);
            addPlayer(player);

            System.out.println(player.getId() + " has been added to the game!");
        }

        setCurrentlyPlaying(getPlayers().get(0));
        System.out.println(getCurrentlyPlaying().getId() + " Is starting!");
        scanner.nextLine();

        for (Player player : getPlayers()) {
            gameLoop(player, current);
        }
    }

    @Override
    public void playerFinished(Player player) {
        System.out.println("Player completed the level!");
    }

    @Override
    public void setup() {

    }

    public void gameLoop(Player playing, Level current) {
        setCurrentlyPlaying(playing);
        setCurrentLevel(current);

        System.out.println(playing.getX() + " " + playing.getY());


        display();

        System.out.println("\n" + playing.getId() + " it is your turn! To move, please type a direction followed by the amount of spaces.");
        System.out.println("\nEnter your first move:");
        String move = scanner.nextLine();
        String direction = move.split(" ")[0];
        int amount = Integer.parseInt(move.split(" ")[1]);

        String finishingMessage = "Time is up!";

        while (!move.equals(" ") && !playing.isCompleted()) {

            int x = 0;
            int y = 0;

            if (direction.equalsIgnoreCase("right") || direction.equalsIgnoreCase("r")) {
                x = amount;
            } else if (direction.equalsIgnoreCase("left") || direction.equalsIgnoreCase("l")) {
                x = -amount;
            } else if (direction.equalsIgnoreCase("up") || direction.equalsIgnoreCase("u")) {
                y = -amount;
            } else if (direction.equalsIgnoreCase("down") || direction.equalsIgnoreCase("d")) {
                y = amount;
            }

            if (!playerCanMove(playing, x, y)) {
                System.out.println("There seems to be a wall " + amount + " spaces " + direction);
                System.out.println("Try again: ");
            } else {
                System.out.println("Moving to: " + x + " and " + y);
                getCurrentlyPlaying().move(x, y);
                display();

                if (current.tileAt(playing.getX(), playing.getY()).getType() == TileType.END) {
                    finishingMessage = "Congrats! You have made it through " + current.getName() + "!!!";
                    playing.setCompleted(true);
                } else {
                    System.out.println("\nEnter next move: ");
                }
            }

            move = scanner.nextLine();
            direction = move.split(" ")[0];
            amount = Integer.parseInt(move.split(" ")[1]);
        }

        System.out.println(finishingMessage);
    }
}
