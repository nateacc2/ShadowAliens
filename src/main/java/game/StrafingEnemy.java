package game;
import bagel.Image;

/**
 * Represents a type enemy ship that moves side-to-side.
 * It combines regular downward travel as well as horizontal movement.
 *
 * @author Nathan S.
 */
public class StrafingEnemy extends Enemy {

  private boolean movingLeft;
  private double screenWidth;

  /**
   * Sets up a strafing enemy ship
   *
   * @param x             The starting horizontal coordinate.
   * @param y             The starting vertical coordinate.
   * @param image         The visual representation used to draw this enemy ship.
   * @param arrivalTime   The arrival time of when this enemy should spawn counted in frames
   * @param movementSpeed The travel speed of this enemy per frame.
   * @param screenWidth  The total width of the game screen
   */
  public StrafingEnemy(double x, double y, Image image, double arrivalTime, double movementSpeed,
                       double screenWidth) {
    super(x, y, image, arrivalTime, movementSpeed);
    // evaluates to true if enemy spawn on the left side
    movingLeft = x < screenWidth / 2;
    this.screenWidth = screenWidth;
  }

  /**
   * Moves the enemy down while pushing it sideways at the same time.
   * It tracks screen boundaries so that when the ship touches a boundary edge,
   * it flips the direction boolean to bounce backward and stay inside gamescreen.
   */
  @Override
  public void move() {
    y += movementSpeed;

    if (movingLeft) {
      x -= movementSpeed;
      // check if enemy hits edge of screen
      if (x <= enemyOffset) {
        x = enemyOffset;
        // start moving right
        movingLeft = false;
      }
    } else {
      x += movementSpeed;
      if (x >= screenWidth - enemyOffset) {
        x = screenWidth - enemyOffset;
        movingLeft = true;
      }
    }
  }

  @Override
  public void update() {
    move();
  }
}