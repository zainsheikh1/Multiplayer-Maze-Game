package game;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import level.Level;
import level.Tile;
import level.TileType;

public class GameScene extends Scene {

    public MazeGame game;
    private Group group;
    
    private int aspectRatio = 16;

    public GameScene(Group root, int width, int height, MazeGame game) {
        super(root, width, height);
        this.game = game;
        this.group = root;
    }

    public void displayLevelTest() {
        Level level = game.getCurrentLevel();
        for (Tile tile : level.getTiles()) {
            ImageView img = new ImageView();
            if (tile.getType() == TileType.WALL) {
                img.setImage(new Image("file:resources/textures/stone12-1.png.png"));
            } else if (tile.getType() == TileType.PLAYABLE) {
                img.setImage(new Image("file:resources/textures/plank-1.png.png"));
            } else {
                Rectangle rect = new Rectangle(tile.getX() * aspectRatio, tile.getY() * aspectRatio, aspectRatio, aspectRatio);
                rect.setFill(Color.rgb(tile.getColor().getRed(), tile.getColor().getGreen(), tile.getColor().getBlue()));
                group.getChildren().add(rect);
            }
            img.setFitWidth(aspectRatio);
            img.setFitHeight(aspectRatio);
            img.setX(tile.getX() * aspectRatio);
            img.setY(tile.getY() * aspectRatio);
            group.getChildren().add(img);
        }
    }

    public void displayLevel() {
        Level level = game.getCurrentLevel();

        System.out.println(level.getTiles().size());
        for (Tile tile : level.getTiles()) {
            Rectangle rect = new Rectangle(tile.getX() * aspectRatio, tile.getY() * aspectRatio, aspectRatio, aspectRatio);
            rect.setFill(Color.rgb(tile.getColor().getRed(), tile.getColor().getGreen(), tile.getColor().getBlue()));
            group.getChildren().add(rect);
        }
    }
}
