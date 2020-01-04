package level;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    private File file;

    public LevelLoader(File file) {
        this.file = file;
    }

    public List<Level> load() {

        List<Level> levels = new ArrayList<>();

        if (file.isDirectory() && file.listFiles() != null) {

            for (File levelFile : file.listFiles()) {
                levels.add(createLevel(levelFile));
            }
        } else {
            levels.add(createLevel(file));
        }
        return levels;
    }

    private Level createLevel(File file) {
        Level level = new Level(file.getName().replace(".png", ""));

        try {
            System.out.println(file.getName());
            BufferedImage image = ImageIO.read(file);

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {

                    Color color = new Color(image.getRGB(x, y));
                    Tile tile = new Tile(color, x, y);

                    level.addTile(tile);

                    if (tile.getType() == TileType.START) {
                        level.setStart(tile);
                    }
                    else if (tile.getType() == TileType.END) {
                        level.setEnd(tile);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return level;
    }
}
