package game;

import entity.Player;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import level.Level;
import level.LevelLoader;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class GUIGame extends MazeGame {
    
    private int aspectRatio = 16;

    private Group group;

    private AnimationTimer gameLoop;

    private Stage primary;

    private boolean gameWillContinue = true;
    private boolean isSwitchScreen = false;

    private List<Image> sprites;

    private GameScene scene;

    public GUIGame() {
        this.group = new Group();
        this.scene = new GameScene(group, 512, 512, this);
        this.sprites = Arrays.asList(new Image("file:resources/sprites/player_up.gif"),
        		new Image("file:resources/sprites/player_down.gif"),
        		new Image("file:resources/sprites/player_right.gif"),
        		new Image("file:resources/sprites/player_left.gif"));
    }

    @Override
    public void display() {
        Player player = getCurrentlyPlaying();

        Player next;

        int index = getPlayers().indexOf(player);
        if (index == getPlayers().size() - 1) {
            next = getPlayers().get(0);
        } else {
            next = getPlayers().get(index + 1);
        }

        if (isSwitchScreen) {
            gameLoop.stop();
            Image background = new Image("file:resources/Buttons and TitleScreen/Title Screen.png");
            ImageView bg = new ImageView(background);
            bg.setFitHeight(512);
            bg.setFitWidth(512);
            group.getChildren().add(bg);

            VBox box = new VBox();
            box.setPrefWidth(512);
            box.setAlignment(Pos.CENTER);
            box.setPadding(new Insets(20));

            Label label = new Label("Time taken in level: " + player.getRecentTime());
            label.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            box.getChildren().add(label);

            if (gameWillContinue) {
                Label nextPlayer = new Label(next.getId() + " are you ready to begin " + getCurrentLevel().getName() + "?");
                nextPlayer.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
                box.getChildren().add(nextPlayer);

                Button start = new Button("Start");

                start.setPrefWidth(150);
                start.setOnAction(e -> {

                    setCurrentlyPlaying(next);
                    this.isSwitchScreen = false;
                    next.getTimer().start();
                    toGame(this.primary);

                });
                box.getChildren().add(start);
            } else {
                Label endGame = new Label("Game over maaan, game over.");
                endGame.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
                box.getChildren().add(endGame);

                for (Player p : getPlayers()) {
                    Label l = new Label(p.getId() + "'s final score: " + p.getFinalScore());
                    endGame.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
                    box.getChildren().add(l);
                }
            }

            group.getChildren().add(box);
        } else {

            for (Node node : group.getChildren()) {

                if (node instanceof Group) {

                    Group group = (Group) node;

                    Node child = group.getChildren().get(0);

                    if (child instanceof ImageView) {
                        ImageView pImage = (ImageView) child;

                        if (pImage.getId() == null) {
                            continue;
                        }

                        if (!pImage.getId().equals("player")) {
                            continue;
                        }

                        pImage.setImage(getSpriteForDirection(player.getDirection()));

                        if (pImage.getTranslateX() != player.getX()) {
                            pImage.setTranslateX(player.getX());
                        }
                        if (pImage.getTranslateY() != player.getY()) {
                            pImage.setTranslateY(player.getY());
                        }
                    }
                }
            }
        }
    }

    public Image getSpriteForDirection(String direction) {
        if (direction.equals("up")) {
            return sprites.get(0);
        }
        else if (direction.equals("down")) {
            return sprites.get(1);
        }
        else if (direction.equals("right")) {
            return sprites.get(2);
        }
        else {
            return sprites.get(3);
        }
    }

    public void toGame(Stage primaryStage) {
        this.primary = primaryStage;

        scene.displayLevelTest();
        primaryStage.setScene(scene);
        primaryStage.show();
        start();
        Player currentlyPlaying = getCurrentlyPlaying();
        currentlyPlaying.getTimer().start();
        double velocity = currentlyPlaying.getVelocity();

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            switch (code) {

                case W:
                    if (!newPlayerMove(currentlyPlaying, 0, -velocity))
                        return;
                    currentlyPlaying.setDirection("up");
                    currentlyPlaying.move(0, -velocity);
                    break;
                case S:
                    if (!newPlayerMove(currentlyPlaying, 0, velocity))
                        return;
                    currentlyPlaying.setDirection("down");

                    currentlyPlaying.move(0, velocity);
                    break;
                case D:
                    if (!newPlayerMove(currentlyPlaying, velocity, 0))
                        return;
                    currentlyPlaying.setDirection("right");

                    currentlyPlaying.move(velocity, 0);
                    break;
                case A:
                    if (!newPlayerMove(currentlyPlaying, -velocity, 0))
                        return;
                    currentlyPlaying.setDirection("left");

                    currentlyPlaying.move(-velocity, 0);
                    break;
            }
        });
    }



    @Override
    public void playerFinished(Player player) {
        isSwitchScreen = true;
        player.setCompleted(true);

        boolean isAnotherLevel = true;
        Level next = null;

        if (getPlayers().size() == 1) {
            if (getAvailableLevels().size() == 1) {
                //Display end game screen!
                isAnotherLevel = false;
            } else {
                int index = getAvailableLevels().indexOf(getCurrentLevel());
                if (index == getAvailableLevels().size() - 1) {
                    isAnotherLevel = false;
                } else {
                    next = getAvailableLevels().get(index+1);
                    setCurrentLevel(next);
                    isAnotherLevel = true;
                }
            }
        } else {
            if (getPlayers().indexOf(getCurrentlyPlaying()) == getPlayers().size() - 1) {
                //This is the last player in our loop!
                if (getAvailableLevels().size() == 1) {
                    //Display end game screen.
                    isAnotherLevel = false;
                } else {
                    if (getAvailableLevels().indexOf(getCurrentLevel()) == getAvailableLevels().size() - 1) {
                        //Display end game screen last level
                        isAnotherLevel = false;
                    } else {
                        next = getAvailableLevels().get(getAvailableLevels().indexOf(getCurrentLevel()) + 1);
                        setCurrentLevel(next);
                        isAnotherLevel = true;
                    }
                }
            }
        }


        this.gameWillContinue = isAnotherLevel;
    }

    @Override
    public void start() {
        System.out.println("Start");
        if (getCurrentlyPlaying() == null) {
            setCurrentlyPlaying(getPlayers().get(0));
        }
        Player player = getCurrentlyPlaying();

        player.setX(getCurrentLevel().getStart().getX() * aspectRatio);
        player.setY(getCurrentLevel().getStart().getY() * aspectRatio);


        /*Rectangle rect = new Rectangle(0, 0, aspectRatio, aspectRatio);
        rect.setTranslateX(player.getX());
        rect.setTranslateY(player.getY());
        rect.setFill(Color.BLUE);
        group.getChildren().add(rect);*/

        Image img = new Image("file:resources/sprites/player_right.gif");
        ImageView view = new ImageView(img);
        view.setTranslateX(player.getX());
        view.setTranslateY(player.getY());
        view.setFitWidth(aspectRatio);
        view.setFitHeight(aspectRatio);
        view.setId("player");
        Group imgGroup = new Group(view);

        group.getChildren().add(imgGroup);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                display();
            }
        };
        gameLoop.start();
    }

    @Override
    public void setup() {
        LevelLoader loader = null;
        loader = new LevelLoader(new File("resources/levels"));
        addAll(loader.load());

        Level first = getAvailableLevels().get(0);
        setCurrentLevel(first);
    }
}
