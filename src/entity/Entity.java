package entity;
/**
*Entity class to organize different entities within the GUI game
*/
public class Entity {
    /**
    *Private variables for the Entity class, keeping track of position (x,y) and identity
    */
    private String id;
    private double x, y;
    private double velocity;
    
    /**
    *Entity constructor 
    *@param id: String, x: int, y: int
    */
    public Entity(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
    *Changes the x and y coordinates of the entity based on the input values of move
    *@param distX: int, distY: int
    */
    public void move(double distX, double distY) {
        setX(getX() + distX);
        setY(getY() + distY);
    }
    
    //Getters and Setters:
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
