package game;

import bagel.Image;
import static game.ShadowAliens.screenHeight;

/**
 * Acts as the parent blueprint for all projectiles in the game.
 * It provides a shared setup for tracking projectile speeds, moving them, and
 * turning them off when they leave the screen.
 *
 * @author Nathan S.
 */
public abstract class Projectile extends GameObject {
  protected int speed;
  private boolean projExist;

  /**
   * Sets up a new projectile with its starting position, image, and speed.
   *
   * @param x     The horizontal center point where the projectile is fired.
   * @param y     The vertical center point where the projectile is fired.
   * @param image The visual representation used to display the projectile on screen.
   * @param speed The speed of which the projectile travels per frame.
   */
  public Projectile(double x, double y, Image image, int speed) {
    super(x, y, image);
    this.speed = speed;
    projExist = true;
  }

  /**
   * Updates the bullet's position every frame. It checks if the bullet has flown
   * past the top or bottom of the window so that missed shots can be safely
   * deleted from memory instead of traveling forever.
   */
  @Override
  public void update() {
    move();

    // check if off screen
    if (y < 0 || y > screenHeight) {
      projExist = false;
    }
  }

  public void draw() {
    if (projExist) {
      image.draw(x,y);
    }
  }
  
  public boolean isProjExist() {
    return projExist;
  }

  public void setProjExist(boolean projExist) {
    this.projExist = projExist;
  }

  public int getSpeed() {
    return speed;
  }
}
