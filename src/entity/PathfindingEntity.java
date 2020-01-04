package entity;
import java.util.ArrayList;
import java.util.List;

public class PathfindingEntity extends Entity {

    /**
     * This class represents an enemy who can move but is bounded by a path
     */

    private List<Point> bounds = new ArrayList<>();

    public PathfindingEntity(String id, int x, int y) {
        super(id, x, y);
    }

    public void addPoint(Point point) {

    }

    public void addAllPoints(Point... points) {

    }
}
