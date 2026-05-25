package game;
import bagel.Image;

/**
 * Serves as the parent blueprint for all visible elements of all game objects in
 * Shadow Aliens. It enforces a coordinate layout and establishes a single image to represent
 * what each game objects should look like in the user's display.
 *
 * @author Nathan S.
 */
public abstract class GameObject implements Collidable {
  protected double x;
  protected double y;
  protected Image image;

  /**
   * Sets up the starting position and image for a game object.
   * This allows the game loop to track where the object is on the screen.
   *
   * @param x     The horizontal center point of the object.
   * @param y     The vertical center point of the object.
   * @param image The visual representation of the object.
   */
  public GameObject(double x, double y, Image image) {
    this.x = x;
    this.y = y;
    this.image = image;
  }

  /**
   * Created as an empty placeholder so that individual child classes
   * can write their own custom movement rules without breaking the main loop.
   */
  public void move(){
  }

  /**
   * Created as an empty placeholder so that child classes can update
   * their own unique timers or behaviors on every single frame.
   */
  public void update(){
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }


  /**
   * Checks if this object touches another object using bounding boxes.
   * It measures the distance from the true center points because the Bagel
   * engine draws all assets from ther center.
   * This prevents collision mistakes when objects have different sizes.
   *
   * @param other The other game object we are testing for a hit.
   * @return true if the objects overlap horizontally and vertically; false otherwise.
   */
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
