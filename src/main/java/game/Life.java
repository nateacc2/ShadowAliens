package game;

import bagel.Image;
import java.util.Properties;

/**
 * Represents an instant health power-up item. Unlike timed power-ups, this item
 * modifies the player's health count, giving them an extra life if they don't currently have
 * max health.
 *
 * @author Nathan S.
 */
public class Life extends Powerup {
  /**
   * Constructs a new Life object and extracts its specific downward falling speed
   * from the properties file.
   *
   * @param x            The initial horizontal coordinate.
   * @param y            The initial vertical coordinate.
   * @param image        The visual representation for this powerup.
   * @param arrivalTime  The game frame count when this power-up should spawn.
   * @param gameProps    The configuration data file containing powerup specs.
   */
  public Life(double x, double y, Image image, int arrivalTime, Properties gameProps) {
    super(x, y, image, arrivalTime);
    this.movementSpeed = Integer.parseInt(gameProps.getProperty("powerup.life.movementSpeed"));
    isCollected = false;
  }

  /**
   * Implements the falling movement logic if the item is still airborne
   */
  @Override
  public void update() {
    if (!isCollected()) {
      super.update();
    }
    // Instant item logic: Once collected, player stat is modified immediately
  }

  /**
   * Instantly increments the player's life count and immediately
   * marks itself as inactive so the game engine's active buff status can be clear.
   *
   * @param player The player instance receiving the extra health.
   */
  @Override
  public void activate(Player player) {
    // Increment life counter instantly
    player.setLivesCount(player.getLivesCount() + 1);

    setActive(false);
  }

  @Override
  public void deactivate(Player player) {
    // Nothing required here since the effect is permanent
  }
}