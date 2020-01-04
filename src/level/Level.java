package level;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Level {

    private List<Tile> tiles = new ArrayList<>();
    private String name;

    private Tile start;
    private Tile end;

    public Level(String name) {
        this.name = name;
    }

    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    public String getName() {
        return name;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public Tile getStart() {
        return start;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(Tile start) {
        this.start = start;
    }

    public Tile getEnd() {
        return end;
    }

    public void setEnd(Tile end) {
        this.end = end;
    }

    public Tile newTileAt(double x, double y) {
        Rectangle2D.Double checkObj = new Rectangle2D.Double(x, y, 16, 16);
        Tile match = null;
        for (Tile tile : tiles) {
            int minX = tile.getX()*16;
            int minY = tile.getY()*16;

            Rectangle2D.Double tileBounds = new Rectangle2D.Double(minX, minY, 16, 16);
            if (tileBounds.intersects(checkObj) || tileBounds.contains(checkObj.getX(), checkObj.getY())) {
                match = tile;
            }
        }
        return match;
    }

    public Tile tileAt(double x, double y) {
        Tile match = null;
        for (Tile tile : tiles) {

            if (tile.getX() == (int) x && tile.getY() == (int) y) {
                match = tile;
            }
        }
        return match;
    }
}
