package game;
import bagel.Image;

/**
 * Represents a standard enemy ship type
 * It implements a simple enemy movement of  flying straight down the screen at a specified speed.
 *
 * @author Nathan S.
 */
public class RegularEnemy extends Enemy {

  /**
   * Constructs a standard regular enemy instance. Passes its starting position,
   * graphic asset, wave arrival time, and speed values directly up to the parent
   * enemy configuration blueprint.
   *
   * @param x             The horizontal coordinate on the enemy spaceship.
   * @param y             The vertical coordinate on the enemy spaceship.
   * @param image         The visual representation used to draw the ship.
   * @param arrivalTime   The time an enemy should arrive counted in frames.
   * @param movementSpeed The travel speed of this enemy per frame.
   */
  public RegularEnemy(double x, double y, Image image, double arrivalTime, double movementSpeed) {
    super(x, y, image, arrivalTime, movementSpeed);
  }

  @Override
  public void move() {
    y += movementSpeed;
  }

  @Override
  public void update() {
    move();
  }
}
