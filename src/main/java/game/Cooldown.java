package game;

import bagel.Image;
import java.util.Properties;



public class Cooldown extends Powerup {
  private int duration;
  private int durActive;
  private int originalCooldown;

  public Cooldown(double x, double y, Image image, int arrivalTime,
                  Properties gameProps) {
    super(x, y, image, arrivalTime);

    movementSpeed = Integer.parseInt(gameProps.getProperty("powerup.cooldown.movementSpeed"));
    duration = Integer.parseInt(gameProps.getProperty("powerup.cooldown.duration"));
    isCollected = false;
    durActive = 0;
  }

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

  @Override
  public void activate(Player player) {
    originalCooldown = player.getProjCooldown();

    player.setProjCooldown(Math.max(1, originalCooldown / 3));
  }

  @Override
  public void deactivate(Player player) {
    player.setProjCooldown(originalCooldown);
  }

  public boolean isEffectActive() {
    // checks whether is picked up and is currently on the wave
    return isCollected && isActive();
  }
}
