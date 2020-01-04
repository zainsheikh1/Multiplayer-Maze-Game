package level;

public enum TileType {

    PLAYABLE(255,255,255),
    START(255, 0, 0),
    END(0, 255,0),
    WALL(0,0,0);

    int red, green, blue;

    TileType(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static TileType fromRGB(int red, int green, int blue) {
        TileType found = null;
        for (TileType type : values()) {
            if (type.red == red && type.green == green && type.blue == blue) {
                found = type;
            }
        }
        return found;
    }
}
