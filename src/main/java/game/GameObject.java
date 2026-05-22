package game;
import bagel.Image;

public abstract class GameObject implements Collidable {
  protected double x;
  protected double y;
  protected Image image;

  public GameObject(double x, double y, Image image) {
    this.x = x;
    this.y = y;
    this.image = image;
  }

  public void move(){
  }
  public void update(){
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  @Override
  public boolean collidesWith(GameObject other){
    // find bounding box of current Object
    double halfWidthA = image.getWidth() / 2;
    double halfHeightA = image.getHeight() / 2;

    // bounding box of other object
    double halfWidthB = other.image.getWidth() / 2;
    double halfHeightB = other.image.getHeight() / 2;

    // check if it bounding box overlaps horizontally
    if (Math.abs(x - other.x) < (halfWidthA + halfWidthB)) {
      if (Math.abs(y - other.y) < (halfHeightA + halfHeightB)) {

        // collision detected, distance between the two objects is
        // smaller than combined width and height of both objects
        return true;
      }
    }
    // no collision detected
    return false;
  }
}
