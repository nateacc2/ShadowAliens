package game;

import bagel.Image;
import java.util.Properties;

public class Life extends Powerup {

  public Life(double x, double y, Image image, int arrivalTime, Properties gameProps) {
    super(x, y, image, arrivalTime);
    this.movementSpeed = Integer.parseInt(gameProps.getProperty("powerup.life.movementSpeed"));
    isCollected = false;
  }

  @Override
  public void update() {
    if (!isCollected()) {
      super.update();
    }
    // Instant item logic: Once collected, it has no continuous active frame cycle
  }

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