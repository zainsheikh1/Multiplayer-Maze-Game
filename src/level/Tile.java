package level;

import java.awt.*;

public class Tile {

    private Color color;
    private TileType type;

    private int x,y;

    public Tile(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.type = TileType.fromRGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
