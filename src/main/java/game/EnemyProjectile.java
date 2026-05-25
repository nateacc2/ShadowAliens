package game;

import bagel.Image;

/**
 * Represents a projectile fired by an enemy ship.
 * Handles downward movement across the game screen.
 *
 * @author Nathan S.
 */

public class EnemyProjectile extends Projectile {

  /**
   * Constructs a new enemy projectile at a specific position with an assigned
   * image and speed value.
   *
   * @param x     The horizontal starting coordinate.
   * @param y     The vertical starting coordinate.
   * @param image The visual representation used to draw the projectile.
   * @param speed The speed at which projectile travels down the screen.
   */
  public EnemyProjectile(double x, double y, Image image, int speed) {
    super(x, y, image, speed);
  }

  /**
   * Updates vertical position to move downwards at its "speed" value
   */
  @Override
  public void move() {
    // enemy projectile moves down screen
    y += speed;
  }
}
