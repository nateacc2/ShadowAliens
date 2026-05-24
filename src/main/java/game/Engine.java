package game;

import bagel.Image;
import java.util.Properties;

public class Engine extends Powerup {
  private int duration;
  private int durActive;
  private int originalSpeed;

  public Engine(double x, double y, Image image, int arrivalTime, Properties gameProps) {
    super(x, y, image, arrivalTime);

    movementSpeed = Integer.parseInt(gameProps.getProperty("powerup.engine.movementSpeed"));
    duration = Integer.parseInt(gameProps.getProperty("powerup.engine.duration"));
    isCollected = false;
    durActive = 0;
  }

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