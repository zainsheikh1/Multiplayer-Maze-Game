package gui;


import entity.Player;
import game.GUIGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameGUI extends Application {

    private Label playerName;
    private TextField field;

    private static GUIGame game = new GUIGame();

    public void start(Stage primaryStage) {
        game.setup();
        System.out.println("Setting up Application");

        primaryStage.setTitle("TEAM 5_Maze Game");

        StackPane vb = new StackPane();
        vb.setAlignment(Pos.CENTER);
        Image background = new Image ("file:resources/Buttons and TitleScreen/Title Screen.png");
        ImageView bg = new ImageView(background);

        bg.setFitHeight(600);
        bg.setFitWidth(550);
        vb.getChildren().add(bg);

        playerName = new Label("Enter player to start");
        vb.getChildren().add(playerName);


        VBox vb2 = new VBox();
        vb2.setAlignment(Pos.BOTTOM_CENTER);

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        Label enterPlayer = new Label("Enter Player: ");
        enterPlayer.setTextFill(Color.web("#FFFFFF"));

        box.getChildren().add(enterPlayer);

        field = new TextField();
        field.setMaxWidth(100.0);
        field.setMinWidth(50.0);
        box.getChildren().add(field);

        vb.getChildren().add(box);

        StackPane menu = new StackPane();

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.BOTTOM_CENTER);

        Button player = new Button("");
        Button start = new Button("");
        player.setStyle("-fx-background-color: #0A0A79");
        start.setStyle("-fx-background-color: #0A0A79");
        Image addPlayerBtn = new Image ("file:resources/Buttons and TitleScreen/AddPlayer-1.png.png");
        ImageView apBtn = new ImageView(addPlayerBtn);
        player.setGraphic(apBtn);
        Image startBtn = new Image ("file:resources/Buttons and TitleScreen/Start Game.png");
        ImageView sgBtn = new ImageView(startBtn);
        start.setGraphic(sgBtn);

        player.setOnAction(event -> {
            String playerName = field.getText();

            if (playerName.equals("")) {
                System.out.println("Something wrong with player");
                return;
            }

            field.setText("");
            game.addPlayer(new Player(playerName, "", game.getCurrentLevel().getStart().getX(), game.getCurrentLevel().getStart().getY()));
        });

        start.setOnAction(event -> {
            if (game.getPlayers().size() == 0)
                return;

            game.toGame(primaryStage);
        });

        buttons.getChildren().addAll(player, start);

        box.getChildren().add(buttons);

        primaryStage.setScene(new Scene(vb, 550, 600));
        primaryStage.show();
    }
}
