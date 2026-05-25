package game;

import bagel.Image;
import java.util.Properties;

/**
 * Represents a type of powerup called Cooldown. When collected by a player it decreases the
 * weapon's cooldown by a factor of a third for a specific duration, reverting back to the original
 * cooldown when the duration ends.
 *
 * @author Nathan S
 */
public class Cooldown extends Powerup {
  private int duration;
  private int durActive;
  private int originalCooldown;

  /**
   * Constructs a cooldown power-up object with specific specs according to gameData properties file
   *
   * @param x           Current x value position of powerup
   * @param y           Current y value position of powerup
   * @param image       Visual representation of powerup
   * @param arrivalTime Scheduled arrivalTime of powerup in frames
   * @param gameProps   GameProperties file which containS all powerup specs
   */
  public Cooldown(double x, double y, Image image, int arrivalTime,
                  Properties gameProps) {
    super(x, y, image, arrivalTime);

    movementSpeed = Integer.parseInt(gameProps.getProperty("powerup.cooldown.movementSpeed"));
    duration = Integer.parseInt(gameProps.getProperty("powerup.cooldown.duration"));
    isCollected = false;
    durActive = 0;
  }

  /**
   * Updates state of powerup to either move or increment duration timer to control when to start
   * and end the cooldown effect
   */
  @Override
  public void update() {

    if (!isCollected) {
      // if not collected keep falling
      super.update();
      } else {
      // powerup has been collected, start incrementing timer
      durActive++;

      // Check whether effect has ran out
      if (durActive >= duration) {
        setActive(false);
      }
    }
  }

  /**
   * Lowers the projectile cooldown for player
   * @param player Player instance that is receiving the cooldown reduction
   */
  @Override
  public void activate(Player player) {
    originalCooldown = player.getProjCooldown();

    player.setProjCooldown(Math.max(1, originalCooldown / 3));
  }

  @Override
  public void deactivate(Player player) {
    player.setProjCooldown(originalCooldown);
  }
}
