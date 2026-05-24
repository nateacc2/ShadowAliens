package game;

import bagel.Image;
import java.util.Properties;

public class Shield extends Powerup {
  private int duration;
  private int durActive;

  public Shield(double x, double y, Image image, int arrivalTime, Properties gameProps) {
    super(x, y, image, arrivalTime);
    this.movementSpeed = Integer.parseInt(gameProps.getProperty("powerup.shield.movementSpeed"));
    this.duration = Integer.parseInt(gameProps.getProperty("powerup.shield.duration"));
    isCollected = false;
    this.durActive = 0;
  }

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

  @Override
  public void activate(Player player) {
    player.setInvincible(true);
  }

  @Override
  public void deactivate(Player player) {
    player.setInvincible(false);
  }
}