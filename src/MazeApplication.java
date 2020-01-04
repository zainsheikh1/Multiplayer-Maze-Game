import game.ConsoleGame;
import gui.GameGUI;
import javafx.application.Application;

import java.util.Scanner;

public class MazeApplication {

    public static void main(String[] args) {
        MazeApplication app = new MazeApplication();
        app.setupGame();
    }

    public void setupGame() {
        System.out.println("Would you like to play the Console or GUI Version: ");
        Scanner sc = new Scanner(System.in);
        String line;
        while ((line = sc.nextLine()) != null && !line.equalsIgnoreCase("console") && !line.equalsIgnoreCase("gui")) {
            System.out.println("I could not find that game mode, try console or gui.");
        }

        if (line.equalsIgnoreCase("console")) {
            initConsoleGame();
        }
        else {
            initGUIGame();
        }
    }

    private void initConsoleGame() {
        ConsoleGame game = new ConsoleGame();
        game.setup();
        game.start();
    }

    private void initGUIGame() {
        Application.launch(GameGUI.class);
    }
}
