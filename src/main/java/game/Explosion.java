package game;

import bagel.Image;

/**
 * Represents the explosion effect in the game.
 * Automatically tracks its own lifespan using an internal frame timer
 * and stops rendering once its active duration runs out.
 *
 * @author Nathan S.
 */
public class Explosion extends GameObject {

  private int duration;
  private int timer;
  private boolean isActive;

  /**
   * Constructs a new Explosion effect at a specific position with a designated
   * image asset and rendering duration.
   *
   * @param x        The horizontal coordinate for the explosion.
   * @param y        The vertical coordinate for the explosion.
   * @param image    The graphic image used to display the explosion.
   * @param duration The total number of game frames the explosion should remain visible.
   */
  public Explosion(double x, double y, Image image, int duration) {
    super(x, y, image);
    this.duration = duration;
    this.timer = 0;
    this.isActive = true;
  }

  /**
   * Updates the explosion's frame counter. Tracks how long an explosion should remain on
   * gamescreen before disappearing.
   */
  public void update() {

    if (isActive) {
      timer++;
      if (timer >= duration) {
        isActive = false;
      }
    }
  }
  /**
   * Renders the explosion image onto the game screen if the effect is still active
   * and has not exceeded its duration.
   */
  public void draw() {
    if (isActive) {
      image.draw(x, y);
      if (timer <= duration) {
        image.draw(x, y);
      }
    }
  }

  public boolean isActive() {
    return isActive;
  }

}
