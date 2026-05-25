package game;

import bagel.Image;

/**
 * Represents a projectile fired by the player's spaceship.
 * Handles upward movement mechanics across the game screen.
 *
 * @author Nathan S.
 */
public class PlayerProjectile extends Projectile {
  /**
   * Constructs a new player projectile at the ship's current coordinates.
   *
   * @param x     The horizontal coordinate of where the projectile spawns.
   * @param y     The vertical coordinate of where the projectile spawns.
   * @param image The visual representation assigned to the player's projectile.
   * @param speed The speed at which the projectile travels upward.
   */
  public PlayerProjectile(double x, double y, Image image, int speed) {
    super(x, y, image, speed);
  }
  /**
   * Updates vertical position of projectile by substracting its speed value.
   */
  @Override
  public void move() {
    // player projectile moves up screen
    y -= speed;
  }
}
