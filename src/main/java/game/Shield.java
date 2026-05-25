package game;

import bagel.Image;
import java.util.Properties;

/**
 * Represents a Shield power-up item.
 * When picked up, it temporarily shields the player from all incoming damage
 * for a duration before deactivating .
 *
 * @author Nathan S.
 */
public class Shield extends Powerup {
  private int duration;
  private int durActive;

  /**
   * Sets up a new Shield item by pulling its specific falling speed and active
   * duration settings out of the main configuration file.
   *
   * @param x            The horizontal center point of the shield powerup.
   * @param y            The vertical center point of the shield powerup.
   * @param image        The visual representation of the shield powerup
   * @param arrivalTime  The arrival time of when the shield powerup should appear in frames.
   * @param gameProps    The configuration file containing the shield's speed and duration.
   */
  public Shield(double x, double y, Image image, int arrivalTime, Properties gameProps) {
    super(x, y, image, arrivalTime);
    this.movementSpeed = Integer.parseInt(gameProps.getProperty("powerup.shield.movementSpeed"));
    this.duration = Integer.parseInt(gameProps.getProperty("powerup.shield.duration"));
    isCollected = false;
    this.durActive = 0;
  }

  /**
   * Manages the life cycle of the shield. If it hasn't been collected, it keeps
   * falling down the screen. Once collected, it switches to a frame counter to
   * track how long it has been on, turning itself off once the time limit is reached.
   */
  @Override
  public void update() {
    
    if (!isCollected()) {
      super.update();
    } else {
      durActive++;
      if (durActive >= duration) {
        setActive(false);
      }
    }
  }

  /**
   * Flip the player ship's invincibility status to true so that health points
   * are not affected when player gets hit.
   *
   * @param player The player ship receiving the protection status.
   */
  @Override
  public void activate(Player player) {
    player.setInvincible(true);
  }

  /**
   * Reverts the player ship's invincibility back to false so that enemies can
   * damage the player normally again after the power-up expires.
   *
   * @param player The player ship losing the protection status.
   */
  @Override
  public void deactivate(Player player) {
    player.setInvincible(false);
  }
}