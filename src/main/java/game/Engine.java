package game;

import bagel.Image;
import java.util.Properties;

/**
 * Represents an Engine power-up item. When collected, it temporarily increases
 * the player's movement speed for a set duration before returning to normal
 *
 * @author Nathan S.
 */
public class Engine extends Powerup {
  private int duration;
  private int durActive;
  private int originalSpeed;

  /**
   * Constructs a new Engine power-up instance and loads its speed and duration
   * configurations from the properties file.
   *
   * @param x            The horizontal starting coordinate.
   * @param y            The vertical starting coordinate.
   * @param image        The visual representation used to draw the power-up.
   * @param arrivalTime  The game frame count when this item should spawn.
   * @param gameProps    The configuration file properties containing engine specs.
   */
  public Engine(double x, double y, Image image, int arrivalTime, Properties gameProps) {
    super(x, y, image, arrivalTime);

    movementSpeed = Integer.parseInt(gameProps.getProperty("powerup.engine.movementSpeed"));
    duration = Integer.parseInt(gameProps.getProperty("powerup.engine.duration"));
    isCollected = false;
    durActive = 0;
  }

  /**
   * Updates the item's status. If it hasn't been collected, it continues to fall down
   * the screen. If it has been collected, it increments an active frame timer until
   * the duration runs out.
   */
  @Override
  public void update() {
    if (!isCollected()) {
      super.update(); // Keep falling down screen canvas
    } else {
      durActive++;
      if (durActive >= duration) {
        setActive(false);
      }
    }
  }

  /**
   * Saves the player's current speed baseline and doubles their movement speed.
   *
   * @param player The player instance receiving the speed boost.
   */
  @Override
  public void activate(Player player) {
    originalSpeed = player.getMovementSpeed();
    // Double player speed, or scale up by a specified amount
    player.setMovementSpeed(originalSpeed * 2);
  }

  @Override
  public void deactivate(Player player) {
    player.setMovementSpeed(originalSpeed);
  }
}